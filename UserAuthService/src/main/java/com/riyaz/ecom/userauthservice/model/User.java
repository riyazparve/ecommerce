package com.riyaz.ecom.userauthservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity(name = "users")
public class User extends BaseModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private Status status;

    @ManyToMany
    private List<Role> role;
}
