package com.example.repository;

import com.example.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRep extends JpaRepository<Inventory, String> {
    Optional<Inventory> findBySkuCodeEquals(String sku);
    List<Inventory> findBySkuCodeIn(List<String> skuCode);
}
