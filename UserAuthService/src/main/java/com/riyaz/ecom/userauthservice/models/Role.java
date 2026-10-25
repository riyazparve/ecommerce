package com.riyaz.ecom.userauthservice.models;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "roles")
public class Role extends BaseModel {
    private String name;
}
