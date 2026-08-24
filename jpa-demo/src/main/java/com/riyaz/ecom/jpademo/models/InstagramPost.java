package com.riyaz.ecom.jpademo.models;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstagramPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "instagram_page_id")
    private InstagramPage instagramPage;

    @OneToMany(mappedBy = "post")
    private List<InstagramLike> instagramLikes;

    @OneToMany(mappedBy = "post")
    private List<InstagramComment> instagramComments;

    private String content;
}
