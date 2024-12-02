package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="shopping_carts")
@SuperBuilder
@Entity
public class ShoppingCart extends BaseEntity {
    private String userId;
    @OneToMany(mappedBy = "shoppingCart")
    private List<ShoppingCartItem> cartItems;
}
