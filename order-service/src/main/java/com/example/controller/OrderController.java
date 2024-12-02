package com.example.controller;

import com.example.dto.OrderReq;
import com.example.dto.OrderUpdateReq;
import com.example.exception.DuplicateKeyException;
import com.example.service.OrderService;
import com.example.utils.JwtUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final JwtUtils jwtUtils;

    @PostMapping
    @CircuitBreaker(name = "order", fallbackMethod = "fallbackMethod")
    public CompletableFuture<ResponseEntity<String>> placeOrder(@RequestBody OrderReq orderReq,
                             @RequestHeader("Authorization") String token
                             ) {
            String userId =  jwtUtils.getClaims(token).get("id").toString();
            orderService.placeOrder(orderReq, userId);
        return  CompletableFuture.supplyAsync(()->
                ResponseEntity.status(HttpStatus.CREATED).body("Create order success")
                );
    }
    @PutMapping("/{id}")
    @CircuitBreaker(name = "update", fallbackMethod = "fallbackMethod")
    public CompletableFuture<String> update(
            @PathVariable("id") String id,
            @RequestBody OrderUpdateReq req,
            @RequestHeader("Authorization") String token
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();
        orderService.update(req, id, userId);
        return  CompletableFuture.supplyAsync(()-> "Update Order Successfully");

    }
    public CompletableFuture<ResponseEntity<String>> fallbackMethod(RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(runtimeException.getMessage()));
    }

}
