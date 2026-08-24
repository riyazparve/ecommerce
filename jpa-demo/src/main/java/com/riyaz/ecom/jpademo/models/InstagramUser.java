package com.riyaz.ecom.jpademo.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Entity
@Data
public class InstagramUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String email;
    private String username;

    @OneToMany(mappedBy = "creator")
    private Set<InstagramPage> pages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<InstagramLike> instagramLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<InstagramComment> instagramComments = new ArrayList<>();
}
