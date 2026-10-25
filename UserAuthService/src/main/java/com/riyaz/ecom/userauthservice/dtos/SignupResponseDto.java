package com.riyaz.ecom.userauthservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupResponseDto {
    private String message;
    private UserDto user;
}
