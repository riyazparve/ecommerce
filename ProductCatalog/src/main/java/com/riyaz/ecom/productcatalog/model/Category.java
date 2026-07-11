package com.riyaz.ecom.productcatalog.model;

import lombok.Data;

import java.util.List;

@Data
public class Category extends BaseModel {
    private String name;
    private String description;
    private List<Product> products;
}
