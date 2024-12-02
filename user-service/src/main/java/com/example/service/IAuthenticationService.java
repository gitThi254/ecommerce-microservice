package com.example.service;



import com.example.dto.user.LoginDto;
import com.example.dto.user.UserDto;
import com.example.dto.user.UserReq;

public interface IAuthenticationService {
    UserDto register(UserReq userDto);
    LoginDto login(String username, String password);
}
