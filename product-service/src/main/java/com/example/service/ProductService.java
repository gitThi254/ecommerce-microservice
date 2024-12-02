package com.example.service;


import com.example.dto.PageRequestDto;
import com.example.dto.ProductDto;
import com.example.exception.ForeignKeyException;
import com.example.exception.NotFoundException;
import com.example.mapper.ProductMapper;
import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRep;
import com.example.repository.ProductRep;
import com.example.req.ProductReq;
import com.example.utils.PageAuto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRep productRep;
    private final ProductMapper productMapper;
    private final PageAuto pageAuto;
    private final CategoryRep categoryRep;

    @Override
    public String save(ProductReq req) {
        boolean checkCategoryName = productRep.existsByName(req.getName());
        if (checkCategoryName) {
            throw new DuplicateKeyException("Product Name exist");
        } else {
            Product product = productMapper.mapTo(req);
            return productRep.save(product).getId();
        }
    }

    @Override
    public Page<ProductDto> getAll(PageRequestDto dto, String keyword, String categoryId, BigDecimal min, BigDecimal max) {
        List<ProductDto> listDto =  productRep.filter(keyword, categoryId).stream()
                .map(productMapper::mapToDto).collect(Collectors.toList());
        return pageAuto.Page(dto, listDto);
    }

    @Override
    public ProductDto get(String id) {
        return productRep.findById(id).map(productMapper::mapToDto).orElseThrow(() -> new NotFoundException("Product not found"));

    }

    @Override
    public void delete(String id) {
        productRep.findById(id).ifPresentOrElse(category -> {
            try {
                productRep.delete(category);
            } catch (DataIntegrityViolationException e) {
                throw new ForeignKeyException(String.format("Cannot delete product with id %s due to foreign key constraint", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Product with id %s not found", id));
        });
    }

    @Override
    public void update(String id, ProductReq req) {
        productRep.findById(id)
                .map(oldCategory -> {
                    Category category = categoryRep.findById(req.getCategoryId()).orElseThrow(() -> new NotFoundException("Category not found"));
                    oldCategory.setName(req.getName());
                    oldCategory.setDescription(req.getDescription());
                    oldCategory.setCategory(category);
                    return productRep.save(oldCategory);
                })
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %s not found", id)));
    }

}
