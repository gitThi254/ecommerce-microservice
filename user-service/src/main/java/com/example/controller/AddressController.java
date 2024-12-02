package com.example.controller;

import com.example.dto.user.AddressDto;
import com.example.dto.user.PageRequestDto;
import com.example.req.AddressReq;
import com.example.service.AddressService;
import com.example.utils.JwtUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/address")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService service;
    private final JwtUtils jwtUtil;
    @PostMapping
    public ResponseEntity<AddressDto> create(
            @Valid @RequestBody AddressReq t,
            @RequestHeader("Authorization") String token
    ) {
        String userId =  jwtUtil.getClaims(token).get("id").toString();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(t, userId));
    }
    @GetMapping
    public ResponseEntity<Page<AddressDto>> getAll(
            @RequestHeader("Authorization") String token,
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "pageIndex", required = false, defaultValue = "0") Integer pageIndex,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "createdAt") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "DESC") Sort.Direction order
    ) {
        String userId =  jwtUtil.getClaims(token).get("id").toString();
        PageRequestDto dto = new PageRequestDto(pageIndex, pageSize, order, sort);
        return ResponseEntity.ok(service.filter(dto, keyword.toLowerCase(), userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> findById(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String userId =  jwtUtil.getClaims(token).get("id").toString();
        return ResponseEntity.ok(service.findById(id, userId));
    }


    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> update(@RequestHeader("Authorization") String token, @PathVariable String id, @Valid @RequestBody AddressReq req) {
        String userId =  jwtUtil.getClaims(token).get("id").toString();


        return ResponseEntity.ok(service.update(req, id,userId ));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable String id) {
        String userId =  jwtUtil.getClaims(token).get("id").toString();
        service.delete(id, userId);
        return ResponseEntity.ok(String.format("Address with id %s deleted", id));
    }
}
