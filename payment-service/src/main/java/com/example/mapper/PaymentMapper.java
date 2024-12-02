package com.example.mapper;


import com.example.dto.PaymentDto;
import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.Status;
import com.example.req.PaymentReq;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class PaymentMapper {
    public Payment mapTo(String userId, String orderId, PaymentMethod paymentMethod, BigDecimal amount,
                         Status status
                         ) {
        String random = UUID.randomUUID().toString();
        return Payment.builder().transaction_id(random).orderId(orderId).userId(userId).paymentMethod(paymentMethod).status(status).amount(amount).build();
    }
    public PaymentDto mapToDto(Payment payment) {
        return PaymentDto.builder()
                .id(payment.getId())
                .paymentMethod(payment.getPaymentMethod().getMethodName())
                .amount(payment.getAmount())
                .transaction_id(payment.getTransaction_id())
                .status(payment.getStatus().getName())
                .createdAt(payment.getCreatedAtUTC())
                .updatedAt(payment.getUpdatedAtUTC())
                .build();
    }
}
