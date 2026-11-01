package com.riyaz.ecom.userauthservice.repositories;

import com.riyaz.ecom.userauthservice.models.Status;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.models.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {
    Optional<UserSession> findByToken(String token);

    Optional<UserSession> findByUserAndStatus(User user, Status status);
}
