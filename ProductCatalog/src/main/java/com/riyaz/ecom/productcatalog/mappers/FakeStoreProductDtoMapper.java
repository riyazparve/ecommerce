package com.riyaz.ecom.productcatalog.mappers;

import com.riyaz.ecom.productcatalog.dtos.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FakeStoreProductDtoMapper {
    
    @Mapping(source = "title", target = "name")
    @Mapping(source = "image", target = "imageUrl")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isPrimeProduct", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    Product toEntity(FakeStoreProductDto dto);

    @Mapping(source = "name", target = "title")
    @Mapping(source = "imageUrl", target = "image")
    @Mapping(target = "category", ignore = true)
    FakeStoreProductDto toDto(Product product);
}
