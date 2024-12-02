package com.example.req;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductItemReq {
    private String SKU;
    @NotNull(message = "quantity  is required")
    @Min(value = 1, message = "Quantity > 1")
    private int qtyInStock;
    @NotNull(message = "Price  is required")
    private BigDecimal price;
    @NotNull(message = "Size is required")
    private String size;
    @NotNull(message = "Color is required")
    private String color;
    private String productId;
}
