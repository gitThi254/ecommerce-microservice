package com.example.controller;

import com.example.dto.PageRequestDto;
import com.example.dto.ProductItemDto;
import com.example.req.ProductItemReq;
import com.example.response.ApiResponse;
import com.example.service.ProductItemService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("${api.prefix}/product/{productId}/item")
@RequiredArgsConstructor
public class ProductItemController {
    private final ProductItemService service;
    @GetMapping
    public ResponseEntity<Page<ProductItemDto>> findAll(
            @RequestParam(value = "keyword",  defaultValue = "") String keyword,
            @PathVariable String productId,
            @RequestParam(value = "pageIndex",  defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize",  defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", defaultValue = "DESC") Sort.Direction order
    ) {
        PageRequestDto dto = new PageRequestDto(pageIndex, pageSize, order, sort);
        return ResponseEntity.ok(service.getAll(dto, keyword, productId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductItemDto> find(
            @PathVariable String id,
            @PathVariable String productId
    ) {
        return ResponseEntity.ok(service.get(productId, id));
    }
    @PostMapping
    @CircuitBreaker(name = "createProductItem", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<ApiResponse>> create(@Valid @RequestBody ProductItemReq req,
                                              @PathVariable String productId
    ) {
            service.save(req, productId);
        return  CompletableFuture.supplyAsync(()-> ResponseEntity.status(201).body(new ApiResponse("Create Product item success")));
    }
    @PutMapping("/{id}")
    @CircuitBreaker(name = "updateProductItem", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<ApiResponse>> update(
            @PathVariable String id,
            @PathVariable String productId,
            @Valid @RequestBody ProductItemReq req) {
            service.update(productId ,id , req);
        return  CompletableFuture.supplyAsync(()-> ResponseEntity.ok(new ApiResponse("Update Product item success")));

    }
    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "deleteProductItem", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<ApiResponse>> delete(
            @PathVariable String id,
            @PathVariable String productId
    ) {
        service.delete(productId , id);
        return  CompletableFuture.supplyAsync(()-> ResponseEntity.ok(new ApiResponse("Delete Product item success")));

    }
    public CompletableFuture<ResponseEntity<String>> fallbackMethod(RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body( "Oops! Something went wrong, please try again later!"));
    }
}
