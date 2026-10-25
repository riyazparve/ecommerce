package com.riyaz.ecom.productcatalog.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Product extends BaseModel {
    private String name;
    @Column(length = 1000)
    private String description;
    @ManyToOne
    private Category category;
    private Double price;
    private String imageUrl;

    private Boolean isPrimeProduct; // this data should not be exposed to external entities
}
