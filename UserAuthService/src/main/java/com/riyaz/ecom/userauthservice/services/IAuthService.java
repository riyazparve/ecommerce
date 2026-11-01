package com.riyaz.ecom.userauthservice.services;

import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.User;
import org.springframework.data.util.Pair;

public interface IAuthService {
    User signup(String name, String email, String phoneNumber, String password) throws UserAlreadyExistsException;

    Pair<User, String> login(String email, String password) throws RuntimeException;

    Boolean validateToken(String token);

    void logout(String token);


    public static final String NON_ADMIN = "NON_ADMIN";
    public static final int EXPIRY_TIME = 1000 * 60 * 60; // 1 hour
    public static final String SCALER = "scaler";
    public static final String AUTH = "auth";
    public static final String AUTHENTICATION_TYPE = "type";

}
