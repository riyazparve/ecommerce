package com.riyaz.ecom.productcatalog.mapper;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.model.Product;

public class ProductDtoMapper {
    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        if (product == null) { return productDto; }
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        return productDto;
    }
}
