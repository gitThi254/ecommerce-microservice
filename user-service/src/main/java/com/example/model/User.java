package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Table(name="user_site")
@SuperBuilder
@Entity
public  class User extends BaseEntity {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    public String fullName() {
        return firstName + " " + lastName;
    }

}
