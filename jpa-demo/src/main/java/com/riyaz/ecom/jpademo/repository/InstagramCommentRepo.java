package com.riyaz.ecom.jpademo.repository;

import com.riyaz.ecom.jpademo.models.InstagramComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstagramCommentRepo extends JpaRepository<InstagramComment, UUID> {
    List<InstagramComment> findByPostId(UUID postId);
    List<InstagramComment> findByUserId(UUID userId);
}
