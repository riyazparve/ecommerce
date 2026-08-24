package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramPost;
import com.riyaz.ecom.jpademo.repository.InstagramPostRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstagramPostService implements IInstagramPostService {

    private final InstagramPostRepo postRepo;

    public InstagramPostService(InstagramPostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public List<InstagramPost> getAllPosts() {
        return postRepo.findAll();
    }

    @Override
    public Optional<InstagramPost> getPostById(UUID id) {
        return postRepo.findById(id);
    }

    @Override
    public List<InstagramPost> getPostsByPageId(UUID pageId) {
        return postRepo.findByInstagramPageId(pageId);
    }

    @Override
    public InstagramPost createPost(InstagramPost post) {
        return postRepo.save(post);
    }

    @Override
    public InstagramPost updatePost(UUID id, InstagramPost post) {
        return postRepo.findById(id).map(existing -> {
            existing.setContent(post.getContent());
            existing.setInstagramPage(post.getInstagramPage());
            return postRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public void deletePost(UUID id) {
        postRepo.deleteById(id);
    }
}
