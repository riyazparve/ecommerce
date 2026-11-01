package com.riyaz.ecom.userauthservice.controllers;

import com.riyaz.ecom.userauthservice.dtos.LoginRequestDto;
import com.riyaz.ecom.userauthservice.dtos.SignupRequestDto;
import com.riyaz.ecom.userauthservice.dtos.SignupResponseDto;
import com.riyaz.ecom.userauthservice.dtos.UserDto;
import com.riyaz.ecom.userauthservice.dtos.UserMapper;
import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.services.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthController authController;

    @Test
    void signupReturnsMappedUserOnSuccess() throws UserAlreadyExistsException {
        SignupRequestDto request = new SignupRequestDto();
        request.setName("New User");
        request.setEmail("new@example.com");
        request.setPhoneNumber("555-0100");
        request.setPassword("secret");
        User user = new User();
        UserDto userDto = new UserDto();
        when(authService.signup("New User", "new@example.com", "555-0100", "secret")).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<SignupResponseDto> response = authController.signup(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        SignupResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals("Successful", body.getMessage());
        assertSame(userDto, body.getUser());
    }

    @Test
    void signupReturnsBadRequestWhenEmailAlreadyExists() throws UserAlreadyExistsException {
        SignupRequestDto request = new SignupRequestDto();
        request.setEmail("existing@example.com");
        when(authService.signup(null, "existing@example.com", null, null))
                .thenThrow(new UserAlreadyExistsException("User already exists"));

        ResponseEntity<SignupResponseDto> response = authController.signup(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        SignupResponseDto body = response.getBody();
        assertNotNull(body);
        assertEquals("User already exists", body.getMessage());
        assertNull(body.getUser());
    }

    @Test
    void loginReturnsMappedUserAndBearerTokenOnSuccess() {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("user@example.com");
        request.setPassword("secret");
        User user = new User();
        UserDto userDto = new UserDto();
        when(authService.login("user@example.com", "secret")).thenReturn(Pair.of(user, "jwt-token"));
        when(userMapper.toDto(user)).thenReturn(userDto);

        ResponseEntity<UserDto> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(userDto, response.getBody());
        assertEquals("Bearer jwt-token", response.getHeaders().getFirst(HttpHeaders.AUTHORIZATION));
    }

    @Test
    void loginReturnsUnauthorizedWhenAuthenticationFails() {
        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("user@example.com");
        request.setPassword("wrong");
        when(authService.login("user@example.com", "wrong")).thenThrow(new RuntimeException("Invalid credential"));

        ResponseEntity<UserDto> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void validateTokenRejectsMalformedAuthorizationHeader() {
        ResponseEntity<Boolean> response = authController.validateToken("Basic abc");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(Boolean.FALSE, response.getBody());
        verify(authService, never()).validateToken(anyString());
    }

    @Test
    void validateTokenPassesBearerTokenToService() {
        when(authService.validateToken("jwt-token")).thenReturn(true);

        ResponseEntity<Boolean> response = authController.validateToken("Bearer jwt-token");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Boolean.TRUE, response.getBody());
    }
}
