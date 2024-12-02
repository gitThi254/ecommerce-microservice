package com.example.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemReq {
    @NotNull(message = "skuCode id is required")
    private String skuCode;
    @NotNull(message = "qty is required")
    private int qty;
}
