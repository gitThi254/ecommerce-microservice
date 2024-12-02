package com.example.mapper;


import com.example.dto.InventoryDto;
import com.example.dto.ProductDto;
import com.example.exception.NotFoundException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRep;
import com.example.req.ProductReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryRep categoryRep;
    public Product mapTo(ProductReq req) {
        Category category = categoryRep.findById(req.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
        return Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .category(category)
                .build();
    }
    public ProductDto mapToDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .photoUrl(product.getPhotoUrl())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getCategoryName())
                .createdAt(product.getCreatedAtUTC())
                .updatedAt(product.getUpdatedAtUTC())
                .build();
    }
}
