package com.example.service;


import com.example.dto.CategoryDto;
import com.example.dto.PageRequestDto;
import com.example.req.CategoryReq;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface ICategoryService {
    void save(CategoryReq req);
    Page<CategoryDto> getAllCategories(PageRequestDto dto, String keyword);
    CategoryDto getCategoryById(String id);
    void deleteCategoryById(String id);
    void updateCategory(String id, CategoryReq categoryReq);
}