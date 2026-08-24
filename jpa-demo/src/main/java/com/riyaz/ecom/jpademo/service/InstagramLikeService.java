package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramLike;
import com.riyaz.ecom.jpademo.repository.InstagramLikeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstagramLikeService implements IInstagramLikeService {

    private final InstagramLikeRepo likeRepo;

    public InstagramLikeService(InstagramLikeRepo likeRepo) {
        this.likeRepo = likeRepo;
    }

    @Override
    public List<InstagramLike> getAllLikes() {
        return likeRepo.findAll();
    }

    @Override
    public Optional<InstagramLike> getLikeById(UUID id) {
        return likeRepo.findById(id);
    }

    @Override
    public List<InstagramLike> getLikesByPostId(UUID postId) {
        return likeRepo.findByPostId(postId);
    }

    @Override
    public List<InstagramLike> getLikesByUserId(UUID userId) {
        return likeRepo.findByUserId(userId);
    }

    @Override
    public InstagramLike createLike(InstagramLike like) {
        return likeRepo.save(like);
    }

    @Override
    public InstagramLike updateLike(UUID id, InstagramLike like) {
        return likeRepo.findById(id).map(existing -> {
            existing.setPost(like.getPost());
            existing.setUser(like.getUser());
            return likeRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Like not found"));
    }

    @Override
    public void deleteLike(UUID id) {
        likeRepo.deleteById(id);
    }
}
