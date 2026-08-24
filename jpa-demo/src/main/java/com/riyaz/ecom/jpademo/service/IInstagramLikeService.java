package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramLike;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInstagramLikeService {
    List<InstagramLike> getAllLikes();
    Optional<InstagramLike> getLikeById(UUID id);
    List<InstagramLike> getLikesByPostId(UUID postId);
    List<InstagramLike> getLikesByUserId(UUID userId);
    InstagramLike createLike(InstagramLike like);
    InstagramLike updateLike(UUID id, InstagramLike like);
    void deleteLike(UUID id);
}
