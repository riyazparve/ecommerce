package com.riyaz.ecom.jpademo.repository;

import com.riyaz.ecom.jpademo.models.InstagramPage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstagramPageRepo extends JpaRepository<InstagramPage, UUID> {
    List<InstagramPage> findByCreatorId(UUID creatorId);
}
