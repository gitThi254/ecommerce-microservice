package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRep extends JpaRepository<User, String> {
    @Query("SELECT p FROM User p WHERE CONCAT(p.id,'', LOWER(p.firstName),'', LOWER(p.lastName), '', LOWER(p.phone), '', LOWER(p.email), '', LOWER(p.username), '') LIKE %?1%  OR CONCAT(p.id, '') LIKE ?2" )
    List<User> filter(String keyword, String  role);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhone(String phone);
    Optional<User> findByUsername(String username);
}