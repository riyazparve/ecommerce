package com.riyaz.ecom.productcatalog.mapper;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductDtoMapper {
    
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);

    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto categoryDto);
}
