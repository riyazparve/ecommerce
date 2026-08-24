package com.riyaz.ecom.jpademo.models;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class InstagramLike {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private InstagramPost post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private InstagramUser user;
}
