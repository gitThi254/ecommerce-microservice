package com.example.service;


import com.example.exception.NotFoundException;
import com.example.mapper.PaymentMapper;
import com.example.model.Payment;
import com.example.model.PaymentMethod;
import com.example.model.Status;
import com.example.repository.PaymentMethodRep;
import com.example.repository.PaymentRep;
import com.example.repository.StatusRep;
import com.example.req.PaymentReq;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentMapper paymentMapper;
    private final PaymentMethodRep paymentMethodRep;
    private final StatusRep statusRep;
    private final PaymentRep paymentRep;
    @SneakyThrows
    @Transactional
    public void savePayment(BigDecimal amount, String orderId, String methodId , String userId) {
        PaymentMethod paymentMethod = paymentMethodRep.findById(methodId).orElseThrow(() -> new NotFoundException("Payment not found"));
        Status status = statusRep.findById("1").orElseThrow(() -> new NotFoundException("Status not found"));
        Payment payment = paymentMapper.mapTo(userId, orderId, paymentMethod, amount, status);
        paymentRep.save(payment);
    }
}
