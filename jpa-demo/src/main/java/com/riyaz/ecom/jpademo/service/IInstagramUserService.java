package com.riyaz.ecom.jpademo.service;

import com.riyaz.ecom.jpademo.models.InstagramUser;

import java.util.List;
import java.util.UUID;

public interface IInstagramUserService {
    List<InstagramUser> getAllUsers();
    InstagramUser getUserById(UUID id);
    InstagramUser findByUsername(String username);
    InstagramUser findByEmail(String email);
    InstagramUser save(InstagramUser user);
    void delete(InstagramUser user);
}
