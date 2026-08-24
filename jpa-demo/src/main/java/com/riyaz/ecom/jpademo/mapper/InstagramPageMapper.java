package com.riyaz.ecom.jpademo.mapper;

import com.riyaz.ecom.jpademo.dto.InstagramPageDto;
import com.riyaz.ecom.jpademo.models.InstagramPage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface InstagramPageMapper {
    InstagramPageMapper INSTANCE = Mappers.getMapper(InstagramPageMapper.class);

    InstagramPage toEntity(InstagramPageDto dto);

    InstagramPageDto toDto(InstagramPage entity);
}
