package com.example.service;


import com.example.dto.PageRequestDto;
import com.example.dto.ProductItemDto;
import com.example.req.ProductItemReq;
import org.springframework.data.domain.Page;

public interface IProductItemService {
    void save(ProductItemReq req, String id);
    Page<ProductItemDto> getAll(PageRequestDto dto, String keyword, String id);
    ProductItemDto get(String productId, String id);
    ProductItemDto getSku(String sku);
    void delete(String productId, String id);
    void update(String productId, String id, com.example.req.ProductItemReq req);
}
