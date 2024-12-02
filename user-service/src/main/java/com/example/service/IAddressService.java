package com.example.service;


import com.example.dto.user.AddressDto;
import com.example.dto.user.PageRequestDto;
import com.example.req.AddressReq;
import org.springframework.data.domain.Page;


public interface IAddressService {
    AddressDto save(AddressReq t, String username);
    AddressDto findById(String id, String username);
    void delete(String id, String username);
    AddressDto update(AddressReq t, String id, String username);
    Page<AddressDto> filter(PageRequestDto dto, String keyword, String username );
}
