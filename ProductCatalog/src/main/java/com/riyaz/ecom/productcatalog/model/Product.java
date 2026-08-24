package com.riyaz.ecom.productcatalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product extends BaseModel {
    private String name;
    private String description;
    @ManyToOne
    private Category category;
    private Double price;
    private String imageUrl;

    private Boolean isPrimeProduct; // this data should not be exposed to external entities
}
