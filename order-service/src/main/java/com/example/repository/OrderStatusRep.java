package com.example.repository;

import com.example.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusRep extends JpaRepository<OrderStatus, String>{
}
