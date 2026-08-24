package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramComment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IInstagramCommentService {
    List<InstagramComment> getAllComments();
    Optional<InstagramComment> getCommentById(UUID id);
    List<InstagramComment> getCommentsByPostId(UUID postId);
    List<InstagramComment> getCommentsByUserId(UUID userId);
    InstagramComment createComment(InstagramComment comment);
    InstagramComment updateComment(UUID id, InstagramComment comment);
    void deleteComment(UUID id);
}
