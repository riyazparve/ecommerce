package com.riyaz.ecom.productservice.model;

import lombok.Data;

@Data
public class Product extends BaseModel {
    private String name;
    private String description;
    private Category category;
    private Double price;
    private String imageUrl;
}
