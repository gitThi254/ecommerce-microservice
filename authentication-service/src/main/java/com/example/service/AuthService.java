package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UserReq;
import com.example.exception.DuplicateKeyException;
import com.example.exception.NotFoundException;
import com.example.model.*;
import lombok.AllArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest request) {
        try {
            UserVO registeredUser = restTemplate.postForObject("http://user-service/api/v1/user/login", request, UserVO.class);
            assert registeredUser != null;
            String accessToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole() ,"ACCESS");
            String refreshToken = jwtUtil.generate(registeredUser.getId(), registeredUser.getRole(), "REFRESH");
            return new AuthResponse(accessToken, refreshToken);
        } catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    public UserDto register(UserReq req) {
        try {
            UserDto userDto = restTemplate.postForObject("http://user-service/api/v1/user/register", req, UserDto.class);
            if (userDto == null || userDto.getId() == null) {
                throw new DuplicateKeyException("Failed to register user: Response is null or missing user ID");
            }
            CartRequest cartRequest = new CartRequest(userDto.getId());
            System.out.println(cartRequest.toString());
            CartVO cart = restTemplate.postForObject("http://order-service/api/v1/shop-cart", cartRequest, CartVO.class);
            if (cart == null || cart.getId() == null) {
                throw new DuplicateKeyException("Failed to create cart: Response is null or missing cart ID");
            }
            userDto.setCartId(cart.getId());
            return userDto;
        } catch (Exception e) {
            throw new DuplicateKeyException(e.getMessage());
        }
    }
}