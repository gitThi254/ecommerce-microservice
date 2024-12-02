package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="paymentMethod")
@SuperBuilder
@Entity
public class PaymentMethod extends OtherEntity {
    private String methodName;
    @OneToMany(mappedBy = "paymentMethod")
    private List<Payment> paymentList;
    public PaymentMethod(String id, String name) {
        setMethodName(name);
        setId(id);
    }
}
