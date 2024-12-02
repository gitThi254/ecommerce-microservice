package com.example.service;

import com.example.dto.user.AddressDto;
import com.example.dto.user.PageRequestDto;
import com.example.exception.NotFoundException;
import com.example.mapper.AddressMapper;
import com.example.model.Address;
import com.example.model.User;
import com.example.repository.AddressRep;
import com.example.repository.UserRep;
import com.example.req.AddressReq;
import com.example.utils.PageAuto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService implements IAddressService {
    private final UserRep userRep;
    private final AddressRep addressRep;
    private final AddressMapper addressMapper;
    private final PageAuto pageAuto;

    @Override
    public AddressDto save(AddressReq t, String id) {
        User user = userRep.findById(id).orElseThrow(() -> new NotFoundException(String.format("user with id %s not found", id)));
        Address address = addressMapper.mapTo(t, user);
        return addressMapper.mapToDto(addressRep.save(address));
    }

    @Override
    public AddressDto findById(String id, String userId) {
        return addressRep.findByIdAndUser_Id(id, userId).map(addressMapper::mapToDto).orElseThrow(() -> new NotFoundException(String.format("Address with id %s not found", id)));
    }

    @Override
    public void delete(String id, String userId) {
        addressRep.findByIdAndUser_Id(id, userId).ifPresentOrElse(addressRep::delete, () -> {
            throw new NotFoundException(String.format("Address with id %s not found", id));
        } );
    }

    @Override
    public AddressDto update(AddressReq t, String id, String userId) {
        Address address = addressRep.findByIdAndUser_Id(id, userId)
                .map(addressExisting -> updateExistingAddress(addressExisting, t))
                .map(addressRep::save)
                .orElseThrow(() -> new NotFoundException(String.format("Address with id %s not found", id)));
        return addressMapper.mapToDto(addressRep.save(address));
    }
    private Address updateExistingAddress(Address addressExisting, AddressReq t) {
        addressExisting.setDiaChi(t.getDiaChi());
        addressExisting.setXa(t.getXa());
        addressExisting.setQuan(t.getQuan());
        addressExisting.setTinh(t.getTinh());
        return addressExisting;
    }

    @Override
    public Page<AddressDto> filter(PageRequestDto dto, String keyword, String userId) {
        List<AddressDto> listCategoryDto =  addressRep.findAllByUser_Id(userId)
                .stream()
                .map(addressMapper::mapToDto)
                .collect(Collectors.toList());
        return pageAuto.Page(dto, listCategoryDto);
    }
}
