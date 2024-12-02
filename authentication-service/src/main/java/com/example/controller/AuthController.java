package com.example.controller;

import com.example.dto.UserDto;
import com.example.dto.UserReq;
import com.example.model.AuthRequest;
import com.example.model.AuthResponse;
import com.example.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping(value = "/register")
    public ResponseEntity<UserDto> register(@RequestBody UserReq req) {
        return ResponseEntity.ok(authService.register(req));
    }
}
