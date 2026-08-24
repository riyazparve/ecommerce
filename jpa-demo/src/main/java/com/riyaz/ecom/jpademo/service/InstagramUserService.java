package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramUser;
import com.riyaz.ecom.jpademo.repository.InstagramUserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InstagramUserService implements IInstagramUserService {

    private final InstagramUserRepo userRepo;

    public InstagramUserService(InstagramUserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<InstagramUser> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public InstagramUser getUserById(UUID id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public InstagramUser findByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public InstagramUser findByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Override
    public InstagramUser save(InstagramUser user) {
        return userRepo.save(user);
    }

    @Override
    public void delete(InstagramUser user) {
        userRepo.delete(user);
    }
}
