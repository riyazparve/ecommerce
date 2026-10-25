package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dtos.InstagramPageDto;
import com.riyaz.ecom.jpademo.models.InstagramPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InstagramPageMapper {
//    InstagramPageMapper INSTANCE = Mappers.getMapper(InstagramPageMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "creator", ignore = true)
    InstagramPage toEntity(InstagramPageDto dto);

    @Mapping(source = "creator.id", target = "creatorId")
    InstagramPageDto toDto(InstagramPage entity);
}
