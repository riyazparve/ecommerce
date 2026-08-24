package com.riyaz.ecom.productcatalog.dto;

import lombok.Data;

@Data
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String description;
    private String category;
    private String image;
}
