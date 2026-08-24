package com.riyaz.ecom.jpademo.repository;

import com.riyaz.ecom.jpademo.models.InstagramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InstagramUserRepo extends JpaRepository<InstagramUser, UUID> {
    List<InstagramUser> findByName(String name);

    Optional<InstagramUser> findByUsername(String username);

    Optional<InstagramUser> findByEmail(String email);
}
