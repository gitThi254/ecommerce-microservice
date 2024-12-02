package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItemDto {
    private String id;
    private String SKU;
    private int qtyInStock;
    private BigDecimal price;
    private String photoUrl;
    private String size;
    private String color;
    private String productId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
