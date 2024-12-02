package com.example.mapper;


import com.example.dto.InventoryDto;
import com.example.dto.InventoryReq;
import com.example.model.Inventory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping
public class InventoryMapper {
    public Inventory mapTo(InventoryReq req) {
        return Inventory.builder().skuCode(req.getSkuCode())
                .quantity(req.getQtyInStock())
                .build();
    }
    public InventoryDto mapToDto(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
