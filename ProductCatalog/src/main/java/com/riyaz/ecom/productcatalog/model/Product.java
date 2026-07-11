package com.riyaz.ecom.productcatalog.model;

import lombok.Data;

@Data
public class Product extends BaseModel {
    private String name;
    private String description;
    private Category category;
    private Double price;
    private String imageUrl;

    private Boolean isPrimeProduct; // this data should not be exposed to external entities
}
