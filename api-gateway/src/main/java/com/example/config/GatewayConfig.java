package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Autowired
    private AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/api/v1/product/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://product-service"))
                .route("user-service", r -> r.path("/api/v1/address/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://user-service"))
                .route("order-service", r -> r.path("/api/v1/cart/**", "/api/v1/order/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://order-service"))
                .build();
    }
}
