package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dto.InstagramPostDto;
import com.riyaz.ecom.jpademo.models.InstagramPost;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstagramPostMapper {
    InstagramPostMapper INSTANCE = Mappers.getMapper(InstagramPostMapper.class);

    InstagramPost toEntity(InstagramPostDto dto);

    InstagramPostDto toDto(InstagramPost entity);
}
