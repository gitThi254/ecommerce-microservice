package com.example.service;


import com.example.dto.PageRequestDto;
import com.example.dto.ProductDto;
import com.example.req.ProductReq;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface IProductService {
    String save(ProductReq req);
    Page<ProductDto> getAll(PageRequestDto dto, String keyword, String categoryId, BigDecimal min, BigDecimal max);
    ProductDto get(String id);
    void delete(String id);
    void update(String id, ProductReq ProductReq);
}
