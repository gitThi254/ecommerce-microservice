package com.example.repository;

import com.example.model.ShippingMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingMethodRep extends JpaRepository<ShippingMethod, String>{
}
