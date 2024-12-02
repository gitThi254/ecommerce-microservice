package com.example;

import com.example.model.OrderStatus;
import com.example.model.ShippingMethod;
import com.example.repository.OrderStatusRep;
import com.example.repository.ShippingMethodRep;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(OrderStatusRep orderStatusRep, ShippingMethodRep shippingMethodRep) {
        return  args -> {
             if(orderStatusRep.findById("1").isPresent()) {
                 return;
             } else {
                orderStatusRep.save(new OrderStatus("1", "PENDING"));
                orderStatusRep.save(new OrderStatus("2", "SUCCESS"));
                orderStatusRep.save(new OrderStatus("3", "CANCEL"));
                shippingMethodRep.save(new ShippingMethod("1", "Giao hàng nhanh", BigDecimal.valueOf(10)));
                 shippingMethodRep.save(new ShippingMethod("2", "Giao hàng tiết kiệm",BigDecimal.valueOf(5)));
                 shippingMethodRep.save(new ShippingMethod("3", "Giao hàng hỏa tốc", BigDecimal.valueOf(20)));


             }
        };
    }
}