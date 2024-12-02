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
@Table(name="status")
@SuperBuilder
@Entity
public class Status extends OtherEntity {
    private String name;
    @OneToMany(mappedBy = "status")
    private List<Payment> paymentList;
    public Status(String id, String name) {
        setName(name);
        setId(id);
    }
}
