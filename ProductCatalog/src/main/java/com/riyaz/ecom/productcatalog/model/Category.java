package com.riyaz.ecom.productcatalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Category extends BaseModel {
    private String description;
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
