package com.riyaz.ecom.userauthservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class UserSession extends BaseModel {
    @Column(length = 500)
    private String token;
    private Date expiration;

    @ManyToOne
    private User user;
}
