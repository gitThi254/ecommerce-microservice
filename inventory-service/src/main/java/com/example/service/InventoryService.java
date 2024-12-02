package com.example.service;

import com.example.dto.InventoryDto;
import com.example.dto.InventoryReq;
import com.example.dto.InventoryResponse;
import com.example.exception.NotFoundException;
import com.example.mapper.InventoryMapper;
import com.example.model.Inventory;
import com.example.repository.InventoryRep;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryMapper inventoryMapper;
    private final InventoryRep inventoryRep;
    private final RestTemplate restTemplate;
    @Transactional
    @SneakyThrows
    public InventoryDto getInventory(String sku) {
        return inventoryMapper.mapToDto(inventoryRep.findBySkuCodeEquals(sku).orElseThrow(() -> new NotFoundException("Sku not found")));
    }
    @Transactional
    @SneakyThrows
    public InventoryDto createInventory(InventoryReq req) {
        Inventory inventory = inventoryMapper.mapTo(req);
        return inventoryMapper.mapToDto(inventoryRep.save(inventory));
    }
    @Transactional
    @SneakyThrows
    public InventoryDto updateInventory(String sku,int quantity) {
        Inventory inventory = inventoryRep.findBySkuCodeEquals(sku).orElseThrow(() -> new NotFoundException("Sku code not found") );
        inventory.setQuantity(quantity);
        return inventoryMapper.mapToDto(inventoryRep.save(inventory));
    }

    public void deleteInventory(String sku) {
        Inventory inventory = inventoryRep.findBySkuCodeEquals(sku).orElseThrow(() -> new NotFoundException("Sku code not found") );
        inventoryRep.delete(inventory);
    }
    @Transactional
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> orderItemList) {
        Map<String, Integer> map = orderItemList.stream().map(item -> item.split("_"))
                .collect(Collectors.toMap(item -> item[0], item -> Integer.parseInt(item[1])));
        List<String> keys = new ArrayList<>(map.keySet());
        return inventoryRep.findBySkuCodeIn(keys).stream()
                .map(inventory ->
                        InventoryResponse.builder()

                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > map.get(inventory.getSkuCode()))
                                .build()
                ).toList();
    }
    @Transactional
    @SneakyThrows
    public void isUpdateSellInStock(List<String> orderItemList) {

        Map<String, Integer> map = orderItemList.stream().map(item -> item.split("_"))
                .collect(Collectors.toMap(item -> item[0], item -> Integer.parseInt(item[1])));
        List<String> keys = new ArrayList<>(map.keySet());
        for (String key : map.keySet()) {
            System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
        List<Inventory> inventories = inventoryRep.findBySkuCodeIn(keys);

        inventories.forEach(inventory -> {
            Integer quantityToReduce = map.get(inventory.getSkuCode());
            if (quantityToReduce != null) {
                inventory.setQuantity(inventory.getQuantity() - quantityToReduce);
            }
        });

        inventoryRep.saveAll(inventories);
    }
    @Transactional
    @SneakyThrows
    public void isUpdateRefundInStock(List<String> orderItemList) {

        Map<String, Integer> map = orderItemList.stream().map(item -> item.split("_"))
                .collect(Collectors.toMap(item -> item[0], item -> Integer.parseInt(item[1])));
        List<String> keys = new ArrayList<>(map.keySet());
        for (String key : map.keySet()) {
            System.out.println("Key: " + key + ", Value: " + map.get(key));
        }
        List<Inventory> inventories = inventoryRep.findBySkuCodeIn(keys);

        inventories.forEach(inventory -> {
            Integer quantityToReduce = map.get(inventory.getSkuCode());
            if (quantityToReduce != null) {
                inventory.setQuantity(inventory.getQuantity() + quantityToReduce);
            }
        });

        inventoryRep.saveAll(inventories);
    }
    @Transactional
    @SneakyThrows
    public Boolean isCartInStock(String skuCode, int qty) {
        return inventoryRep.findBySkuCodeEquals(skuCode).stream().anyMatch(item -> item.getQuantity() >= qty);
    }
}
