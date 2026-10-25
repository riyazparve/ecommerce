package com.riyaz.ecom.userauthservice.dtos;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String id;
    private String name;
    private String email;

    private List<RoleDto> role;
}
