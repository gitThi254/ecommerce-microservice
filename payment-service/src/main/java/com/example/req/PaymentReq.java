package com.example.req;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentReq {
    private String paymentMethodId;
    private BigDecimal amount;
}
