package com.riyaz.ecom.jpademo.validator;

import com.riyaz.ecom.jpademo.dto.InstagramUserDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramUserValidator implements Validator<InstagramUserDto> {

    @Override
    public List<String> validate(InstagramUserDto userDto) {
        List<String> errors = new ArrayList<>();

        if (userDto == null) {
            errors.add("User DTO cannot be null");
            return errors;
        }

        if (userDto.getName() == null || userDto.getName().trim().isEmpty()) {
            errors.add("Name is required");
        }

        if (userDto.getName() != null && userDto.getName().length() > 255) {
            errors.add("User name must not exceed 255 characters");
        }

        return errors;
    }

    @Override
    public boolean isValid(InstagramUserDto userDto) {
        return validate(userDto).isEmpty();
    }
}
