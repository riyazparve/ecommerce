package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dtos.InstagramLikeDto;
import com.riyaz.ecom.jpademo.models.InstagramLike;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstagramLikeMapper {
//    InstagramLikeMapper INSTANCE = Mappers.getMapper(InstagramLikeMapper.class);

    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    InstagramLike toEntity(InstagramLikeDto dto);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "user.id", target = "userId")
    InstagramLikeDto toDto(InstagramLike entity);
}
