package com.riyaz.ecom.userauthservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "users")
public class User extends BaseModel {
    private String name;
    private String email;
    private String phoneNumber;
    private String password;

    @ManyToMany
    private List<Role> role;
}
