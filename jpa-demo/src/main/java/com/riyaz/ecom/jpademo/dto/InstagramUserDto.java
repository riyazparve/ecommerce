package com.riyaz.ecom.jpademo.dto;

import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
public class InstagramUserDto {
    private String name;
    private String email;
    private String username;
}
