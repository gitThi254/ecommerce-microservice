package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ObjectError {
    private String message;
    private Date timestamp;
    private int statusCode;
}
