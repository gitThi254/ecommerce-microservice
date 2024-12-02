package com.example.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentDto {
    private String id;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private String transaction_id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
