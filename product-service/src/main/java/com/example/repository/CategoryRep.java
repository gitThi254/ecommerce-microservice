package com.example.repository;

import com.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRep extends JpaRepository<Category, String> {
    boolean existsByCategoryName(String categoryName);
    @Query("SELECT p FROM Category p WHERE (CONCAT(p.id, '',LOWER(p.categoryName), '') LIKE %?1%)")
    public List<Category> filter(String keyword);
}
