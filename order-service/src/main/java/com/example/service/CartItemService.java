package com.example.service;
import com.example.dto.CartItemDto;
import com.example.dto.ProductItemDto;
import com.example.exception.DuplicateKeyException;
import com.example.exception.NotFoundException;
import com.example.mapper.ShoppingCartItemMapper;
import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import com.example.repository.CartItemRep;
import com.example.repository.CartRep;
import com.example.req.CartItemReq;
import com.example.utils.PageAuto;
import com.example.utils.PageRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final ShoppingCartItemMapper shoppingCartItemMapper;
    private final CartItemRep shoppingCartItemRep;
    private final WebClient.Builder webClientBuilder;
    private final CartRep  cartRep;
    private final CartItemRep cartItemRep;
    private final PageAuto pageAuto;

    @Override
    public CartItemDto save(CartItemReq t, String userId) {
        ShoppingCart cart = cartRep.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("cart with user %s not found", userId)));
        ShoppingCartItem checkCartItem = cartItemRep.findBySkuCodeAndShoppingCart(t.getSkuCode(), cart).orElse(null);
        int final_qty = checkCartItem == null ? t.getQty() :  t.getQty() +  checkCartItem.getQty();
        Boolean checkCart = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory/cart",
                        uriBuilder -> uriBuilder.queryParam("skuCode", t.getSkuCode())
                                .queryParam("qty", final_qty)
                                .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
      if(Boolean.TRUE.equals(checkCart)) {
          ProductItemDto productItemDto = webClientBuilder.build().get()
                  .uri("http://product-service/api/v1/product/sku",
                          uriBuilder -> uriBuilder.queryParam("sku", t.getSkuCode())
                                  .build())
                  .retrieve()
                  .bodyToMono(ProductItemDto.class)
                  .block();
          if (checkCartItem == null) {
              ShoppingCartItem shoppingCartItem = shoppingCartItemMapper.mapTo(t, cart);
              ShoppingCartItem savedItem = shoppingCartItemRep.save(shoppingCartItem);
              CartItemDto dto = shoppingCartItemMapper.mapToDto(savedItem);
              if (productItemDto != null) {
                  dto.setPrice(productItemDto.getPrice());
              }
              return dto;
          } else {
              checkCartItem.setQty(final_qty);
              CartItemDto dto = shoppingCartItemMapper.mapToDto(shoppingCartItemRep.save(checkCartItem));
              if (productItemDto != null) {
                  dto.setPrice(productItemDto.getPrice());
              }

              return dto;
          }
      } else {
          throw new DuplicateKeyException("The quantity of goods in stock is not enough");
      }
    }

    @Override
    public CartItemDto findById(String id, String userId) {
        ShoppingCart cart = cartRep.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("cart with user %s not found", userId)));
             return cartItemRep.findByIdAndShoppingCart(id, cart).map(shoppingCartItemMapper::mapToDto).orElseThrow(() -> new NotFoundException(String.format("cart item with %s not found", id)));
    }

    @Override
    public void delete(String id,  String userId) {
        ShoppingCart cart = cartRep.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("cart with user %s not found", userId)));
        ShoppingCartItem cartItem =  cartItemRep.findByIdAndShoppingCart(id, cart).orElseThrow(() -> new NotFoundException(String.format("cart item with %s not found", id)));
       cartItemRep.delete(cartItem);
    }

    @Override
    public CartItemDto update(CartItemReq t, String id, String userId) {
        Boolean checkCart = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory/cart",
                        uriBuilder -> uriBuilder.queryParam("skuCode", t.getSkuCode())
                                .queryParam("qty", t.getQty())
                                .build())
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
        if(Boolean.TRUE.equals(checkCart)) {
            ShoppingCart cart = cartRep.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("cart with user %s not found", userId)));
            ShoppingCartItem cartItem =  cartItemRep.findByIdAndShoppingCart(id, cart).orElseThrow(() -> new NotFoundException(String.format("cart item with %s not found", id)));
            cartItem.setQty(t.getQty());
            return shoppingCartItemMapper.mapToDto(shoppingCartItemRep.save(cartItem));
        } else {
            throw new DuplicateKeyException("The quantity of goods in stock is not enough");
        }
    }

    @Override
    public Page<CartItemDto> filter(PageRequestDto dto, String keyword, String userId) {
        ShoppingCart cart = cartRep.findByUserId(userId).orElseThrow(() -> new NotFoundException(String.format("cart with user %s not found", userId)));
        List<CartItemDto> listCategoryDto =  cartItemRep.findByShoppingCart(cart)
                .stream()
                .map(shoppingCartItemMapper::mapToDto)
                .collect(Collectors.toList());
        return pageAuto.Page(dto, listCategoryDto);
    }
}
