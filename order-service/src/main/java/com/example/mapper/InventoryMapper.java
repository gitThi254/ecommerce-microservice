package com.example.mapper;

import com.example.dto.InventoryReq;
import com.example.model.OrderLineItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMapper {
    public String mapToReq(OrderLineItem orderLineItem) {
        return orderLineItem.getSkuCode() + "_" + orderLineItem.getQuantity();
    }
}
