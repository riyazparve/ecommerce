package com.riyaz.ecom.jpademo.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstagramPage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "instagramPage")
    private Set<InstagramPost> posts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private InstagramUser creator;
}

