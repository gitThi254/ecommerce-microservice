package com.example.config;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationFilter {
    public boolean admin(String path, String method) {
        Map<String, List<String>> apiMap = new HashMap<>();
        apiMap.put("GET", List.of("/api/admin"));
        apiMap.put("POST", List.of("/api/v1/product"));
        apiMap.put("PUT", List.of("/api/v1/product"));
        apiMap.put("DELETE", List.of("/api/v1/product"));
        return apiMap.get(method).stream().anyMatch(path::startsWith);
    }
}
