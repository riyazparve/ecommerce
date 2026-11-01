package com.riyaz.ecom.userauthservice.services;

import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.Role;
import com.riyaz.ecom.userauthservice.models.Status;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.models.UserSession;
import com.riyaz.ecom.userauthservice.repositories.RoleRepository;
import com.riyaz.ecom.userauthservice.repositories.UserRepository;
import com.riyaz.ecom.userauthservice.repositories.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserSessionRepository userSessionRepository;
    private final SecretKey secretKey;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
                       UserSessionRepository userSessionRepository, SecretKey secretKey,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userSessionRepository = userSessionRepository;
        this.secretKey = secretKey;
        this.passwordEncoder = passwordEncoder;
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
        user.setPassword(passwordEncoder.encode(password));
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

    public Pair<User, String> login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Please sign up first"));
        if (!user.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("User is not active");
        }
        if (password == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credential");
        }

        // delete existing session and generate a new one
        userSessionRepository.findByUserAndStatus(user, Status.ACTIVE).ifPresent(userSessionRepository::delete);

        // log in was successful now generate claims, token, and user session
        Map<String, Object> claims = generateClaims(user);
        String token = generateToken(claims);

        // generate user session and save it
        generateAndSaveUserSession(user, token, claims);
        return Pair.of(user, token);
    }

    private void generateAndSaveUserSession(User user, String token, Map<String, Object> claims) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setToken(token);
        userSession.setStatus(Status.ACTIVE);
        userSession.setExpiration((Date) claims.get(Claims.EXPIRATION));
        logger.info("User session generated for user: {} token length: {}", user.getEmail(), token.length());
        userSessionRepository.save(userSession);
    }

    private Map<String, Object> generateClaims(User user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(Claims.ISSUED_AT, new Date());
        claims.put(Claims.EXPIRATION, new Date(System.currentTimeMillis() + EXPIRY_TIME));
        claims.put(Claims.ISSUER, SCALER);

        claims.put("email", user.getEmail());
        claims.put("user_id", user.getId().toString());
        claims.put(AUTHENTICATION_TYPE, AUTH);
        claims.put("access", user.getRole().stream().map(Role::getName).toList());
        return claims;
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder().claims(claims)
                .signWith(secretKey)
                .compact();
    }

    public Boolean validateToken(String token) {
        return validateUserSession(token);
    }

    @Override
    public void logout(String token) {
        Optional<UserSession> userSession = getUserSession(token);
        if (userSession.isEmpty()) {
            return;
        }
        userSession.get().setStatus(Status.INACTIVE);
        userSessionRepository.save(userSession.get());
    }

    public Optional<UserSession> getUserSession(String token) {
        return userSessionRepository.findByToken(token);
    }

    public Boolean validateUserSession(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            if (!SCALER.equals(claims.getIssuer())
                    || !AUTH.equals(claims.get(AUTHENTICATION_TYPE))
                    || claims.getExpiration() == null) {
                return false;
            }

            Optional<UserSession> userSession = getUserSession(token);
            if (userSession.isEmpty()) {
                return false;
            }

            UserSession session = userSession.get();
            Date now = new Date();
            return session.getStatus() == Status.ACTIVE
                    && session.getExpiration() != null
                    && session.getExpiration().after(now)
                    && session.getUser() != null
                    && session.getUser().getStatus() == Status.ACTIVE;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

}
