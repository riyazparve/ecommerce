package com.riyaz.ecom.jpademo.repository;

import com.riyaz.ecom.jpademo.models.InstagramPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstagramPostRepo extends JpaRepository<InstagramPost, UUID> {
    List<InstagramPost> findByInstagramPageId(UUID pageId);
}
