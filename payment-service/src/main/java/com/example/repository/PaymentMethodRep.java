package com.example.repository;

import com.example.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRep extends JpaRepository<PaymentMethod, String> {
}
