package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dto.InstagramUserDto;
import com.riyaz.ecom.jpademo.models.InstagramUser;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstagramUserMapper {
    InstagramUserMapper INSTANCE = Mappers.getMapper(InstagramUserMapper.class);

    InstagramUser toEntity(InstagramUserDto dto);

    InstagramUserDto toDto(InstagramUser entity);
}
