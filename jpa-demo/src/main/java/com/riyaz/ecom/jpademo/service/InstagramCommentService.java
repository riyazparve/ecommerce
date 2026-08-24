package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramComment;
import com.riyaz.ecom.jpademo.repository.InstagramCommentRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InstagramCommentService implements IInstagramCommentService {

    private final InstagramCommentRepo commentRepo;

    public InstagramCommentService(InstagramCommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @Override
    public List<InstagramComment> getAllComments() {
        return commentRepo.findAll();
    }

    @Override
    public Optional<InstagramComment> getCommentById(UUID id) {
        return commentRepo.findById(id);
    }

    @Override
    public List<InstagramComment> getCommentsByPostId(UUID postId) {
        return commentRepo.findByPostId(postId);
    }

    @Override
    public List<InstagramComment> getCommentsByUserId(UUID userId) {
        return commentRepo.findByUserId(userId);
    }

    @Override
    public InstagramComment createComment(InstagramComment comment) {
        return commentRepo.save(comment);
    }

    @Override
    public InstagramComment updateComment(UUID id, InstagramComment comment) {
        return commentRepo.findById(id).map(existing -> {
            existing.setText(comment.getText());
            existing.setPost(comment.getPost());
            existing.setUser(comment.getUser());
            return commentRepo.save(existing);
        }).orElseThrow(() -> new RuntimeException("Comment not found"));
    }

    @Override
    public void deleteComment(UUID id) {
        commentRepo.deleteById(id);
    }
}
