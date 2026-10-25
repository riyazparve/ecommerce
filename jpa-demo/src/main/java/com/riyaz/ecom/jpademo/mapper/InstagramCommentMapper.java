package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dtos.InstagramCommentDto;
import com.riyaz.ecom.jpademo.models.InstagramComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstagramCommentMapper {
//    InstagramCommentMapper INSTANCE = Mappers.getMapper(InstagramCommentMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    InstagramComment toEntity(InstagramCommentDto dto);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    InstagramCommentDto toDto(InstagramComment entity);
}
