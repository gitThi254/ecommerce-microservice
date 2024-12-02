package com.example.repository;

import com.example.model.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRep extends JpaRepository<ProductItem, String> {
    @Query("SELECT p FROM ProductItem p WHERE (CONCAT(p.id, '',LOWER(p.SKU), '') LIKE %?1%)")
    public List<ProductItem> filter(String keyword);
    boolean existsBySizeAndColor(String size, String color);
    public List<ProductItem> findAllByProductId(String productId);
    Optional<ProductItem> findByIdAndProduct_Id(String id, String productId);

    Optional<ProductItem> findBySKU(String sku);
}
