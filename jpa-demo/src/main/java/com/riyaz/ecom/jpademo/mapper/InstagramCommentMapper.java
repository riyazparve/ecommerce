package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dto.InstagramCommentDto;
import com.riyaz.ecom.jpademo.models.InstagramComment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstagramCommentMapper {
    InstagramCommentMapper INSTANCE = Mappers.getMapper(InstagramCommentMapper.class);

    InstagramComment toEntity(InstagramCommentDto dto);

    InstagramCommentDto toDto(InstagramComment entity);
}
