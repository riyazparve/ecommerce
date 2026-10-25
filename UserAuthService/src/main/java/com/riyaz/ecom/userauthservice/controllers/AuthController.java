package com.riyaz.ecom.userauthservice.controllers;

import com.riyaz.ecom.userauthservice.dtos.*;
import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.User;
import com.riyaz.ecom.userauthservice.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        User loggedInUser;
        try {
            loggedInUser = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(userMapper.toDto(loggedInUser));
    }
}
