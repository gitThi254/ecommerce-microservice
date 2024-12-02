package com.example.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductReq {
    @NotNull(message = "Name name is required")
    private String name;
    @NotNull(message = "Description name is required")
    private String description;
    @NotNull(message = "Category name is required")
    private String categoryId;
}
