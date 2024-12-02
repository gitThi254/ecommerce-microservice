package com.example.mapper;


import com.example.dto.InventoryDto;
import com.example.dto.ProductItemDto;
import com.example.exception.NotFoundException;
import com.example.model.Product;
import com.example.model.ProductItem;
import com.example.repository.ProductRep;
import com.example.req.ProductItemReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ProductItemMapper {
    private final ProductRep productRep;
    private final WebClient.Builder webClientBuilder;

    public ProductItem mapTo(ProductItemReq req) {
        Product product = productRep.findById(req.getProductId()).orElseThrow(() -> new NotFoundException("Product not found"));
        return ProductItem.builder()
                .SKU(req.getSKU())
                .color(req.getColor())
                .size(req.getSize())
                .price(req.getPrice())
                .product(product)
                .build();
    }
    public ProductItemDto mapToDto(ProductItem item) {
        InventoryDto inventory = webClientBuilder.build().get()
                .uri("http://inventory-service/api/v1/inventory",
                        uriBuilder -> uriBuilder
                                .queryParam("skuCode", item.getSKU())
                                .build())
                .retrieve()
                .bodyToMono(InventoryDto.class)
                .block();
        assert inventory != null;
        return ProductItemDto.builder()
                .id(item.getId())
                .photoUrl(item.getPhotoUrl())
                .qtyInStock(inventory.getQuantity())
                .SKU(item.getSKU())
                .color(item.getColor())
                .size(item.getSize())
                .price(item.getPrice())
                .productId(item.getProduct().getId())
                .createdAt(item.getCreatedAtUTC())
                .updatedAt(item.getUpdatedAtUTC())
                .build();
    }
}
