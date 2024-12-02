package com.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    private String userId;
    @ManyToOne
    @JoinColumn(name="shipping_method_id")
    private ShippingMethod shippingMethod;
    @ManyToOne
    @JoinColumn(name="order_status_id")
    private OrderStatus orderStatus;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems;
    public BigDecimal totalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        return total;
    }
}