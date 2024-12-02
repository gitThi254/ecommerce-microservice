package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shipping_method")
@SuperBuilder
@Entity
public class ShippingMethod extends StatusAndShippingEntity{
    private String name;
    private BigDecimal price;
    @OneToMany(mappedBy = "shippingMethod")
    private List<Order> shopOrderList;

    public ShippingMethod(String id, String name, BigDecimal price) {
        this.setId(id);
        this.setName(name);
        this.setPrice(price);
    }
}
