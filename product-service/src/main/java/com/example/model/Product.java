package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
@SuperBuilder
@Entity
public class Product extends BaseEntity {
    private String name;
    private String description;
    private String photoUrl;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="category")
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductItem> productItems;
}
