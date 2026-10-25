package com.riyaz.ecom.userauthservice.services;

import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.Role;
import com.riyaz.ecom.userauthservice.models.Status;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.repositories.RoleRepository;
import com.riyaz.ecom.userauthservice.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {

    public static final String NON_ADMIN = "NON_ADMIN";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User signup(String name, String email, String phoneNumber, String password) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with email: " + email);
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setStatus(Status.ACTIVE);

        Optional<Role> roleOptional = roleRepository.findByName(NON_ADMIN);
        Role role;
        if (roleOptional.isEmpty()) {
            role = new Role();
            role.setName(NON_ADMIN);
            role = roleRepository.save(role);
        } else {
            role = roleOptional.get();
        }
        user.setRole(List.of(role));

        return userRepository.save(user);
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Please sign up first"));
        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("User is not active");
        }
        if (!password.equals(user.getPassword())) {
            throw new RuntimeException("Invalid credential");
        }
        return user;
    }
}
