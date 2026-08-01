package com.riyaz.ecom.productcatalog.mapper;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;

public class ProductDtoMapper {
    public static ProductDto toDto(Product product) {
        if (product == null) { return null; }
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        
        // Map category to DTO
        Category category = product.getCategory();
        if (category != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setId(category.getId());
            categoryDto.setName(category.getName());
            categoryDto.setDescription(category.getDescription());
            productDto.setCategory(categoryDto);
        }
        
        return productDto;
    }

    public static Product toEntity(ProductDto productDto) {
        if (productDto == null) { return null; }
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());

        Category category = new Category();
        CategoryDto categoryDto = productDto.getCategory();
        if (categoryDto != null) {
            category.setId(categoryDto.getId());
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            product.setCategory(category);
        }

        return product;
    }
}
