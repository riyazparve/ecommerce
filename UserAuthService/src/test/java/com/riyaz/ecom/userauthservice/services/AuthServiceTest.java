package com.riyaz.ecom.userauthservice.services;

import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.Role;
import com.riyaz.ecom.userauthservice.models.Status;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.models.UserSession;
import com.riyaz.ecom.userauthservice.repositories.RoleRepository;
import com.riyaz.ecom.userauthservice.repositories.UserRepository;
import com.riyaz.ecom.userauthservice.repositories.UserSessionRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserSessionRepository userSessionRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private AuthService authService;
    private final AtomicReference<UserSession> savedSession = new AtomicReference<>();

    @BeforeEach
    void setUp() {
        SecretKey secretKey = Jwts.SIG.HS256.key().build();
        authService = new AuthService(userRepository, roleRepository, userSessionRepository, secretKey, passwordEncoder);
        lenient().when(userSessionRepository.save(any(UserSession.class))).thenAnswer(invocation -> {
            UserSession session = invocation.getArgument(0);
            savedSession.set(session);
            return session;
        });
        lenient().when(userSessionRepository.findByToken(anyString()))
                .thenAnswer(invocation -> Optional.ofNullable(savedSession.get()));
    }

    @Test
    void signupCreatesNonAdminRoleAndHashesPassword() throws UserAlreadyExistsException {
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(AuthService.NON_ADMIN)).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = authService.signup("New User", "new@example.com", "555-0100", "secret");

        assertEquals("New User", savedUser.getName());
        assertEquals("new@example.com", savedUser.getEmail());
        assertEquals("555-0100", savedUser.getPhoneNumber());
        assertNotEquals("secret", savedUser.getPassword());
        assertTrue(passwordEncoder.matches("secret", savedUser.getPassword()));
        assertEquals(Status.ACTIVE, savedUser.getStatus());
        assertEquals(AuthService.NON_ADMIN, savedUser.getRole().get(0).getName());
        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void signupReusesExistingNonAdminRole() throws UserAlreadyExistsException {
        Role existingRole = new Role();
        existingRole.setName(AuthService.NON_ADMIN);
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName(AuthService.NON_ADMIN)).thenReturn(Optional.of(existingRole));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User savedUser = authService.signup("New User", "new@example.com", "555-0100", "secret");

        assertSame(existingRole, savedUser.getRole().get(0));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void signupRejectsAnEmailThatAlreadyExists() {
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> authService.signup("Existing User", "existing@example.com", "555-0100", "secret")
        );

        assertEquals("User already exists with email: existing@example.com", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
        verify(roleRepository, never()).findByName(anyString());
    }

    @Test
    void loginRejectsUnknownEmail() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login("unknown@example.com", "secret"));
        verify(userSessionRepository, never()).save(any(UserSession.class));
    }

    @Test
    void loginRejectsInactiveUser() {
        User inactiveUser = activeUser();
        inactiveUser.setStatus(Status.INACTIVE);
        when(userRepository.findByEmail(inactiveUser.getEmail())).thenReturn(Optional.of(inactiveUser));

        assertThrows(RuntimeException.class, () -> authService.login(inactiveUser.getEmail(), "correct-secret"));
        verify(userSessionRepository, never()).save(any(UserSession.class));
    }

    @Test
    void loginRejectsIncorrectPassword() {
        User user = activeUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        assertThrows(RuntimeException.class, () -> authService.login(user.getEmail(), "incorrect-secret"));
        verify(userSessionRepository, never()).save(any(UserSession.class));
    }

    @Test
    void loginCreatesOneSessionAndReturnsAValidToken() {
        User user = activeUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Pair<User, String> loginResult = authService.login(user.getEmail(), "correct-secret");

        assertSame(user, loginResult.getFirst());
        assertNotNull(loginResult.getSecond());
        assertFalse(loginResult.getSecond().isBlank());
        verify(userSessionRepository, times(1)).save(any(UserSession.class));
        assertTrue(authService.validateToken(loginResult.getSecond()));
    }

    @Test
    void validateTokenRejectsMalformedAndLoggedOutTokens() {
        assertFalse(authService.validateToken("not-a-jwt"));

        User user = activeUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        String token = authService.login(user.getEmail(), "correct-secret").getSecond();

        authService.logout(token);

        assertFalse(authService.validateToken(token));
    }

    @Test
    void validateTokenRejectsTokensWithoutAnActiveSession() {
        User user = activeUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        String token = authService.login(user.getEmail(), "correct-secret").getSecond();
        savedSession.set(null);

        assertFalse(authService.validateToken(token));
    }

    private User activeUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        user.setPassword(passwordEncoder.encode("correct-secret"));
        user.setStatus(Status.ACTIVE);
        user.setRole(List.of());
        return user;
    }
}
