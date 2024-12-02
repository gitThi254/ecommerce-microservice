package com.example;

import com.example.model.PaymentMethod;
import com.example.model.Status;
import com.example.repository.PaymentMethodRep;
import com.example.repository.StatusRep;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MethodServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MethodServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner(StatusRep statusRep, PaymentMethodRep paymentMethodRep) {
        return args -> {
            if(statusRep.findById("1").isPresent()) {
                return;
            } else {
                statusRep.save(new Status("1", "Pending"));
                statusRep.save(new Status("2", "Completed"));
                statusRep.save(new Status("3", "Failed"));
                paymentMethodRep.save(new PaymentMethod("1", "Credit Card"));
                paymentMethodRep.save(new PaymentMethod("2", "E-Wallets"));
                paymentMethodRep.save(new PaymentMethod("3", "Bank Transfers"));
            }
        };

    }
}