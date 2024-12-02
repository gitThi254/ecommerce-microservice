package com.example.controller;

import com.example.dto.InventoryDto;
import com.example.dto.InventoryReq;
import com.example.dto.InventoryResponse;
import com.example.req.CartItemReq;
import com.example.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService service;
    @PostMapping
    public InventoryDto createInventory(@RequestParam String skuCode, @RequestParam int quantity) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        InventoryReq req = InventoryReq.builder()
                .skuCode(skuCode)
                .qtyInStock(quantity)
                .build();
        return service.createInventory(req);
    }

    @GetMapping
    public InventoryDto getInventory(@RequestParam String skuCode) {
        return service.getInventory(skuCode);
    }

    @GetMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        log.info("Received inventory check request for skuCode: {}", skuCode);
        return service.isInStock(skuCode);
    }
    @PutMapping("/checkout/sell")
    @ResponseStatus(HttpStatus.OK)
    public void isUpdateSellInStock(@RequestParam List<String> skuCode) {
         service.isUpdateSellInStock(skuCode);
    }
    @PutMapping("/checkout/refund")
    @ResponseStatus(HttpStatus.OK)
    public void isUpdateRefundGoodInStock(@RequestParam List<String> skuCode) {
        service.isUpdateRefundInStock(skuCode);
    }
    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public Boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("qty") int quantity) {
        return service.isCartInStock(skuCode, quantity);
    }

    @PutMapping("/{skuCode}")
    public InventoryDto updateInventory(@PathVariable("skuCode") String skuCode, @RequestParam int quantity) {
        return service.updateInventory(skuCode, quantity);
    }
    @DeleteMapping("/{skuCode}")
    public void DeleteInventory(@PathVariable("skuCode") String skuCode) {
        service.deleteInventory(skuCode);
    }
}
