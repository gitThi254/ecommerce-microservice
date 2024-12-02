package com.example.controller;

import com.example.dto.ProductItemDto;
import com.example.service.ProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/product/sku")
@RequiredArgsConstructor
public class OtherController {
    private final ProductItemService service;
    @GetMapping
    public ResponseEntity<ProductItemDto> findBySku(
            @RequestParam String sku
    ) {
        return ResponseEntity.ok(service.getSku(sku));
    }
}
