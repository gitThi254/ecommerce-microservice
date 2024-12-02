package com.example.req;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressReq {
    @NotNull(message = "Dia chi bat buoc")
    private String DiaChi;
    @NotNull(message = "Xa bat buoc")
    private String Xa;
    @NotNull(message = "Quan bat buoc")
    private String Quan;
    @NotNull(message = "Tinh bat buoc")
    private String Tinh;
}
