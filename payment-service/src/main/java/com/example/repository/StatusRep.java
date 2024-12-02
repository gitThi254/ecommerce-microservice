package com.example.repository;

import com.example.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRep extends JpaRepository<Status, String> {
}
