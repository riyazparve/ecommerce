package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dto.InstagramLikeDto;
import com.riyaz.ecom.jpademo.models.InstagramLike;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstagramLikeMapper {
    InstagramLikeMapper INSTANCE = Mappers.getMapper(InstagramLikeMapper.class);

    InstagramLike toEntity(InstagramLikeDto dto);

    InstagramLikeDto toDto(InstagramLike entity);
}
