package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramPost;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInstagramPostService {
    List<InstagramPost> getAllPosts();
    Optional<InstagramPost> getPostById(UUID id);
    List<InstagramPost> getPostsByPageId(UUID pageId);
    InstagramPost createPost(InstagramPost post);
    InstagramPost updatePost(UUID id, InstagramPost post);
    void deletePost(UUID id);
}
