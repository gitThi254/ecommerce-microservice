package com.example.service;

import com.example.dto.CartVO;
import com.example.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartService  {
    CartVO SaveCart(String userId);
}
