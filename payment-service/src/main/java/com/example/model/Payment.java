package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name="payment")
@SuperBuilder
@Entity
public class Payment extends BaseEntity{
    private String userId;
    private String orderId;
    private BigDecimal amount;
    private String transaction_id;
    @ManyToOne
    @JoinColumn(name="payment_method_id")
    private PaymentMethod paymentMethod;
    @ManyToOne
    @JoinColumn(name="status_id")
    private Status status;
}
