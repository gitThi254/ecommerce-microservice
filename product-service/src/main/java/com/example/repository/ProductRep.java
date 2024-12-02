package com.example.repository;

import com.example.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ProductRep extends JpaRepository<Product, String> {
    boolean existsByName(String name);
    @Query("SELECT p FROM Product p WHERE (CONCAT(p.id, '',LOWER(p.name), '') LIKE %?1%) AND CONCAT(p.category.id, '') LIKE %?2%")
    public List<Product> filter(String keyword, String categoryId);
}
