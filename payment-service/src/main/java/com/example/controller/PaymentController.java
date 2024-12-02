package com.example.controller;

import com.example.service.PaymentService;
import com.example.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("${api.prefix}/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final JwtUtils jwtUtils;
    private final PaymentService service;
    @PostMapping
    public void save(@RequestParam("paymentId") String paymentId,
                     @RequestParam("amount") BigDecimal amount,
                     @RequestParam("orderId") String orderId,
                     @RequestParam("userId") String userId
                      ) {
        service.savePayment(amount, orderId, paymentId, userId);
    }
}
