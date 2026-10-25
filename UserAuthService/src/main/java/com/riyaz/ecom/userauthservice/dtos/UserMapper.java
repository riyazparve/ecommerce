package com.riyaz.ecom.userauthservice.dtos;

import com.riyaz.ecom.userauthservice.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}
