package com.riyaz.ecom.productcatalog.mapper;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FakeStoreProductDtoMapper {
    
    @Mapping(source = "title", target = "name")
    @Mapping(source = "image", target = "imageUrl")
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "isPrimeProduct", ignore = true)
    Product toEntity(FakeStoreProductDto dto);

    @Mapping(source = "name", target = "title")
    @Mapping(source = "imageUrl", target = "image")
    @Mapping(target = "category", ignore = true)
    FakeStoreProductDto toDto(Product product);
}
