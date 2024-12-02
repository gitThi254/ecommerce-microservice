package com.example.controller;

import com.example.dto.CategoryDto;
import com.example.dto.PageRequestDto;
import com.example.req.CategoryReq;
import com.example.response.ApiResponse;
import com.example.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
@RestController
@RequestMapping("${api.prefix}/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> findAll(
            @RequestParam(value = "keyword",  defaultValue = "") String keyword,
            @RequestParam(value = "pageIndex",  defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize",  defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", defaultValue = "DESC") Sort.Direction order
    ) {
        PageRequestDto dto = new PageRequestDto(pageIndex, pageSize, order, sort);
        return ResponseEntity.ok(service.getAllCategories(dto, keyword));
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> find(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(service.getCategoryById(id));
    }
    @PostMapping
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CategoryReq req) {
        try {
            service.save(req);
            return ResponseEntity.created(URI.create("/api/v1/admin/category/categoryID")).body(new ApiResponse("Create Category success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable String id,
            @Valid @RequestBody CategoryReq req) {
        try {
            service.updateCategory(id, req);
            return ResponseEntity.ok(new ApiResponse("Update Category success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(
            @PathVariable String id
    ) {
        try {
            service.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Delete Category success"));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(new ApiResponse(e.getMessage()));
        }
    }
}