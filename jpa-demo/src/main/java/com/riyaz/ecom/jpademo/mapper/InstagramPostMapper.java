package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dtos.InstagramPostDto;
import com.riyaz.ecom.jpademo.models.InstagramPost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstagramPostMapper {
//    InstagramPostMapper INSTANCE = Mappers.getMapper(InstagramPostMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "instagramPage", ignore = true)
    @Mapping(target = "instagramLikes", ignore = true)
    @Mapping(target = "instagramComments", ignore = true)
    InstagramPost toEntity(InstagramPostDto dto);

    @Mapping(source = "instagramPage.id", target = "pageId")
    InstagramPostDto toDto(InstagramPost entity);
}
