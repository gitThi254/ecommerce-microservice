package com.example.repository;


import com.example.model.Address;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRep extends JpaRepository<Address, String> {
    Optional<Address> findByIdAndUser_Id(String id, String userId);
    List<Address> findAllByUser_Id(String userId);
}
