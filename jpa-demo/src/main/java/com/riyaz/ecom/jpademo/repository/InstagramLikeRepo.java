package com.riyaz.ecom.jpademo.repository;

import com.riyaz.ecom.jpademo.models.InstagramLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstagramLikeRepo extends JpaRepository<InstagramLike, UUID> {
    List<InstagramLike> findByPostId(UUID postId);
    List<InstagramLike> findByUserId(UUID userId);
}
