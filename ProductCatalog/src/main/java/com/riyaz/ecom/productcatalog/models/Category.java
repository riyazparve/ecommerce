package com.riyaz.ecom.productcatalog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Category extends BaseModel {
    private String description;
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
//    @BatchSize(size = 2)
    private List<Product> products;
}
