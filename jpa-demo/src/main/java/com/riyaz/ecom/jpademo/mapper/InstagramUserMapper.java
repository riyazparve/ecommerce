package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dtos.InstagramUserDto;
import com.riyaz.ecom.jpademo.models.InstagramUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstagramUserMapper {
//    InstagramUserMapper INSTANCE = Mappers.getMapper(InstagramUserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "pages", ignore = true)
    @Mapping(target = "instagramLikes", ignore = true)
    @Mapping(target = "instagramComments", ignore = true)
    InstagramUser toEntity(InstagramUserDto dto);

    InstagramUserDto toDto(InstagramUser entity);
}
