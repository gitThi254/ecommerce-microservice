package com.example.repository;


import com.example.model.ShoppingCart;
import com.example.model.ShoppingCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRep extends JpaRepository<ShoppingCartItem, String> {
    Optional<ShoppingCartItem> findByIdAndShoppingCart(String id, ShoppingCart shoppingCart);
    List<ShoppingCartItem> findByIdIn(List<String> ids);
    List<ShoppingCartItem> findByShoppingCart(ShoppingCart shoppingCart);
    Optional<ShoppingCartItem> findBySkuCodeAndShoppingCart(String sku,ShoppingCart shoppingCart);

}
