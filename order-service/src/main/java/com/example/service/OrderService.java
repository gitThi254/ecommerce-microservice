package com.example.service;


import com.example.dto.InventoryResponse;
import com.example.dto.OrderReq;
import com.example.dto.OrderUpdateReq;
import com.example.dto.ProductItemDto;
import com.example.event.OrderPlacedEvent;
import com.example.exception.DuplicateKeyException;
import com.example.exception.NotFoundException;
import com.example.mapper.InventoryMapper;
import com.example.model.*;
import com.example.repository.CartItemRep;
import com.example.repository.OrderRepository;
import com.example.repository.OrderStatusRep;
import com.example.repository.ShippingMethodRep;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final InventoryMapper inventoryMapper;
    private final CartItemRep cartItemRep;
    private final OrderStatusRep orderStatusRep;
    private final ShippingMethodRep shippingMethodRep;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void update(OrderUpdateReq req, String id, String userId) {
           Order order = orderRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("Order not found"));
           OrderStatus orderStatus = orderStatusRep.findById(req.getStatusId()).orElseThrow(() -> new NotFoundException("Order status not found"));
           if(Objects.equals(orderStatus.getStatus(), "CANCEL") && !Objects.equals(order.getOrderStatus().getStatus(), "CANCEL")) {
               List<String> skuCodes = order.getOrderLineItems().stream()
                       .map(inventoryMapper::mapToReq)
                       .toList();
               webClientBuilder.build().put()
                       .uri("http://inventory-service/api/v1/inventory/checkout/refund",
                               uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                       .build())
                       .retrieve()
                       .bodyToMono(void.class)
                       .block();
           } else if(!Objects.equals(orderStatus.getStatus(), "CANCEL") && Objects.equals(order.getOrderStatus().getStatus(), "CANCEL")) {
               List<String> skuCodes = order.getOrderLineItems().stream()
                       .map(inventoryMapper::mapToReq)
                       .toList();
               webClientBuilder.build().put()
                       .uri("http://inventory-service/api/v1/inventory/checkout/sell",
                               uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                       .build())
                       .retrieve()
                       .bodyToMono(void.class)
                       .block();
           }
           order.setOrderStatus(orderStatus);
           orderRepository.save(order);


    }
    public void placeOrder(OrderReq orderRequest, String userId) {
        OrderStatus orderStatus = orderStatusRep.findById("1").orElseThrow(() -> new NotFoundException("Order status not found"));
        ShippingMethod shippingMethod = shippingMethodRep.findById(orderRequest.getShippingMethodId()).orElseThrow(() -> new NotFoundException("Shipping method not found"));
        Order order = new Order();
        order.setOrderStatus(orderStatus);
        order.setShippingMethod(shippingMethod);
        order.setUserId(userId);
        List<ShoppingCartItem> cartList = cartItemRep.findByIdIn(orderRequest.getCartLists());
        List<OrderLineItem> orderLineItems = cartList
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItems(orderLineItems);
        List<String> skuCodes = order.getOrderLineItems().stream()
                .map(inventoryMapper::mapToReq)
                .toList();
        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory/checkout",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        assert inventoryResponseArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                .allMatch(InventoryResponse::isInStock);
        if(allProductsInStock && Arrays.stream(inventoryResponseArray).findAny().isPresent()){
            webClientBuilder.build().put()
                    .uri("http://inventory-service/api/v1/inventory/checkout/sell",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                    .build())
                    .retrieve()
                    .bodyToMono(void.class)
                    .block();
            Order newOrder = orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getId()));
            webClientBuilder.build().post()
                    .uri("http://payment-service/api/v1/payment",
                            uriBuilder -> uriBuilder
                                    .queryParam("orderId", newOrder.getId())
                                    .queryParam("amount", 1000)
                                    .queryParam("paymentId", "1")
                                    .queryParam("userId", userId)
                                    .build())
                    .retrieve()
                    .bodyToMono(void.class)
                    .block();

        } else {
            throw new DuplicateKeyException("Product is not in stock, please try again later");
        }
    }


    private OrderLineItem mapToDto(ShoppingCartItem cartItem) {
        ProductItemDto productItemDto =  webClientBuilder.build().get()
                .uri("http://product-service/api/v1/product/sku",
                        uriBuilder -> uriBuilder.queryParam("sku", cartItem.getSkuCode())
                                .build())
                .retrieve()
                .bodyToMono(ProductItemDto.class)
                .block();
        assert productItemDto != null;
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setQuantity(cartItem.getQty());
        orderLineItem.setUnitPrice(productItemDto.getPrice());
        orderLineItem.setSkuCode(cartItem.getSkuCode());
        return orderLineItem;
    }
}