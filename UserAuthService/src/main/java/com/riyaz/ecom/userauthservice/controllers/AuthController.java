package com.riyaz.ecom.userauthservice.controllers;

import com.riyaz.ecom.userauthservice.dtos.*;
import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserMapper userMapper;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, UserMapper userMapper) {
        this.authService = authService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto;
        try {
            User user = authService.signup(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPhoneNumber(), signupRequestDto.getPassword());
            signupResponseDto = new SignupResponseDto("Successful", userMapper.toDto(user));
        } catch (UserAlreadyExistsException e) {
            logger.error("User signup failed", e);
            signupResponseDto = new SignupResponseDto(e.getMessage(), null);
            return ResponseEntity.badRequest().body(signupResponseDto);
        }
        return ResponseEntity.ok(signupResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, String> loggedInUser;
        try {
            loggedInUser = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + loggedInUser.getSecond());
            return new ResponseEntity<>(userMapper.toDto(loggedInUser.getFirst()), headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Boolean> validateToken(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        return ResponseEntity.ok(authService.validateToken(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        String token = extractBearerToken(authorizationHeader);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    private String extractBearerToken(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return null;
        }
        String token = authorizationHeader.substring(7).trim();
        return token.isEmpty() || token.chars().anyMatch(Character::isWhitespace) ? null : token;
    }
}
