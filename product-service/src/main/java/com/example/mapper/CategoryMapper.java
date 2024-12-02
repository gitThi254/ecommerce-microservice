package com.example.mapper;


import com.example.dto.CategoryDto;
import com.example.model.Category;
import com.example.req.CategoryReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMapper {
    public Category mapTo(CategoryReq req) {
        return Category.builder()
                .categoryName(req.getCategoryName())
                .build();
    }
    public CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .createdAt(category.getCreatedAtUTC())
                .updatedAt(category.getUpdatedAtUTC())
                .build();
    }
}
