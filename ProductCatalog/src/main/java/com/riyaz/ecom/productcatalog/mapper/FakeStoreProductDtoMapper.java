package com.riyaz.ecom.productcatalog.mapper;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.model.Product;

public class FakeStoreProductDtoMapper {
    public static Product toEntity(FakeStoreProductDto dto) {
        if (dto == null) {return null;}
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImage());
        return product;
    }
}
