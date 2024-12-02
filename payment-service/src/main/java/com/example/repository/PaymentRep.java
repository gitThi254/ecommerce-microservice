package com.example.repository;

import com.example.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRep extends JpaRepository<Payment, String> {
}
