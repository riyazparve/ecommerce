package com.riyaz.ecom.userauthservice.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "roles")
public class Role extends BaseModel {
    private String name;
}
