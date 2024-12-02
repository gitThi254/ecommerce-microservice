package com.example.controller;

import com.example.dto.CartItemDto;
import com.example.dto.CartVO;
import com.example.exception.DuplicateKeyException;
import com.example.exception.NotFoundException;
import com.example.req.CartItemReq;
import com.example.req.CartRequest;
import com.example.service.CartItemService;
import com.example.service.CartService;
import com.example.utils.JwtUtils;
import com.example.utils.PageRequestDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;
    private final JwtUtils jwtUtils;
    private final CartItemService cartItemService;
    @PostMapping("/shop-cart")

    public ResponseEntity<CartVO> save(
            @RequestBody CartRequest cartRequest
    ) {
        return ResponseEntity.ok(cartService.SaveCart(cartRequest.getUserId()));
    }
    @PostMapping("/cart")
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "cart", fallbackMethod = "fallbackMethod")
    public CompletableFuture<CartItemDto> addItem(@RequestBody CartItemReq cartItemReq,
                                                  @RequestHeader("Authorization") String token
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();
        return  CompletableFuture.supplyAsync(()->cartItemService.save(cartItemReq, userId));
    }

    @GetMapping("/cart")
    public ResponseEntity<Page<CartItemDto>> getAllItem(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") Sort.Direction order
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();
        PageRequestDto dto = new PageRequestDto(pageIndex, pageSize, order, sort);
        return ResponseEntity.ok(cartItemService.filter(dto, keyword.toLowerCase(), userId));
    }
    @GetMapping("/cart/{id}")
    public ResponseEntity<CartItemDto> getCartItem(
                                               @RequestHeader("Authorization") String token,
                                               @PathVariable("id") String id
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();

        return ResponseEntity.ok(cartItemService.findById(id, userId)) ;
    }
    @PutMapping("/cart/{id}")
    @CircuitBreaker(name = "cartUpdate", fallbackMethod = "fallbackMethod")
    public CompletableFuture<CartItemDto> updateCartItem(@RequestBody CartItemReq cartItemReq,
                                                      @PathVariable("id") String id,
                                               @RequestHeader("Authorization") String token
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();
        return  CompletableFuture.supplyAsync(()->cartItemService.update(cartItemReq,id, userId));
    }
    @DeleteMapping("/cart/{id}")
    public ResponseEntity<String> deleteCartItem(
                                                      @PathVariable("id") String id,
                                                      @RequestHeader("Authorization") String token
    ) {
        String userId =  jwtUtils.getClaims(token).get("id").toString();
        cartItemService.delete(id, userId);
        return ResponseEntity.ok("Delete cart item success") ;
    }
    public CompletableFuture<ResponseEntity<String>> fallbackMethod(RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> ResponseEntity.status(HttpStatus.BAD_GATEWAY).body( "Oops! Something went wrong, please try again later!"));
    }
}
