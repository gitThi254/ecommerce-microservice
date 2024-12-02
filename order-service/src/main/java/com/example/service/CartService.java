package com.example.service;

import com.example.dto.CartVO;
import com.example.model.ShoppingCart;
import com.example.repository.CartRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRep cartRep;

    @Override
    public CartVO SaveCart(String userId) {
        ShoppingCart cart = ShoppingCart.builder().userId(userId).build();
        return new CartVO(cartRep.save(cart).getId());
    }
}
