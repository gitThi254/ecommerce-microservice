package com.example.controller;

import com.example.dto.PageRequestDto;
import com.example.dto.ProductDto;
import com.example.req.ProductReq;
import com.example.response.ApiResponse;
import com.example.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("${api.prefix}/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    @GetMapping
    public ResponseEntity<Page<ProductDto>> findAll(
            @RequestParam(value = "keyword",  defaultValue = "") String keyword,
            @RequestParam(value = "categoryId",  defaultValue = "") String categoryId,
            @RequestParam(value = "pageIndex",  defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize",  defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", defaultValue = "DESC") Sort.Direction order,
            @RequestParam(value = "min", required = false, defaultValue = "0") BigDecimal min,
            @RequestParam(value = "max", required = false, defaultValue = "0") BigDecimal max
    ) {
        PageRequestDto dto = new PageRequestDto(pageIndex, pageSize, order, sort);
        return ResponseEntity.ok(service.getAll(dto, keyword,categoryId, min, max));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody ProductReq req) {
        try {
            service.save(req);
            return ResponseEntity.created(URI.create("/api/v1/product/categoryID")).body(new ApiResponse("Create product successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));

        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable String id,
            @Valid @RequestBody ProductReq req) {
        try {
            service.update(id, req);
            return ResponseEntity.ok(new ApiResponse("Update Product success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));

        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable String id
    ) {
        try {
            service.delete(id);
            return ResponseEntity.ok(new ApiResponse("Delete Product success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> find(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(service.get(id));
    }
}
