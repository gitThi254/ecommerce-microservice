package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="address")
@SuperBuilder
@Entity
public class Address extends BaseEntity {
    private String DiaChi;
    private String Xa;
    private String Quan;
    private String Tinh;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    public String AddressOrder() {
        return this.DiaChi + " - " + this.Xa + " - " + this.Quan + " - " + this.Tinh;
    }
}
