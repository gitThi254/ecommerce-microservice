package com.example.repository;

import com.example.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRep extends JpaRepository<ShoppingCart,String> {
    Optional<ShoppingCart> findByUserId(String userId);
}
