package com.example.service;


import com.example.dto.CartItemDto;
import com.example.req.CartItemReq;
import com.example.utils.PageRequestDto;
import org.springframework.data.domain.Page;

public interface ICartItemService {
    CartItemDto save(CartItemReq t, String username);
    CartItemDto findById(String id, String username);
    void delete(String id, String username);
    CartItemDto update(CartItemReq t, String id, String username);
    Page<CartItemDto> filter(PageRequestDto dto, String keyword, String username);
}
