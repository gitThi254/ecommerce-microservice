package com.example.controller;

import com.example.dto.user.LoginDto;
import com.example.dto.user.LoginReq;
import com.example.dto.user.UserDto;
import com.example.dto.user.UserReq;
import com.example.service.AuthenticationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService service;
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserReq req) {
        return ResponseEntity.ok(service.register(req));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginReq req) {
        return ResponseEntity.ok(service.login(req.getUsername(), req.getPassword()));
    }
}
