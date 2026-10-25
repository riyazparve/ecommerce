package com.riyaz.ecom.userauthservice.services;

import com.riyaz.ecom.userauthservice.exceptions.UserAlreadyExistsException;
import com.riyaz.ecom.userauthservice.models.User;

public interface IAuthService {
    User signup(String name, String email, String phoneNumber, String password) throws UserAlreadyExistsException;

    User login(String email, String password) throws RuntimeException;
}
