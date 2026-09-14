package com.riyaz.ecom.productcatalog.dto;

import lombok.Data;

@Data
public class ProductDto {
//    private Long id;
    private String name;
    private String description;
    private CategoryDto category;
    private Double price;
    private String imageUrl;
}
