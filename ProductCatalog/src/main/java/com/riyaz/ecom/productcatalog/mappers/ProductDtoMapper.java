package com.riyaz.ecom.productcatalog.mappers;

import com.riyaz.ecom.productcatalog.dtos.CategoryDto;
import com.riyaz.ecom.productcatalog.dtos.ProductDto;
import com.riyaz.ecom.productcatalog.models.Category;
import com.riyaz.ecom.productcatalog.models.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    
    ProductDto toDto(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "isPrimeProduct", ignore = true)
    Product toEntity(ProductDto productDto);

    CategoryDto toCategoryDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modificationDate", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category toCategory(CategoryDto categoryDto);
}
