package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public class BaseEntity {
    @Id
    @UuidGenerator
    @Column(name="id", unique=true, updatable=false)
    private String id;
    @JsonIgnore
    @Column(updatable = false, name="create_at_utc")
    @CreationTimestamp
    private LocalDateTime createdAtUTC;
    @JsonIgnore
    @Column(name="update_at_utc")
    @UpdateTimestamp
    private LocalDateTime updatedAtUTC;
}
