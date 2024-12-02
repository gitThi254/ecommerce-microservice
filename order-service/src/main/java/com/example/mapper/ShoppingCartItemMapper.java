package com.example.mapper;



import com.example.dto.CartItemDto;
import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import com.example.req.CartItemReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ShoppingCartItemMapper {
    public ShoppingCartItem mapTo(CartItemReq t, ShoppingCart cart) {
        return ShoppingCartItem.builder().shoppingCart(cart).qty(t.getQty()).skuCode(t.getSkuCode()).build();
    }

    public CartItemDto mapToDto(ShoppingCartItem shoppingCartItem) {
        return CartItemDto.builder()
                .id(shoppingCartItem.getId())
                .cartId(shoppingCartItem.getShoppingCart().getId())
                .qty(shoppingCartItem.getQty())
                .sku(shoppingCartItem.getSkuCode())
                .createdAt(shoppingCartItem.getCreatedAtUTC())
                .updatedAt(shoppingCartItem.getUpdatedAtUTC())
                .build();
    }
}
