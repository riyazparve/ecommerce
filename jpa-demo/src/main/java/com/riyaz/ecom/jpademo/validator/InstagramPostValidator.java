package com.riyaz.ecom.jpademo.validator;

import com.riyaz.ecom.jpademo.dto.InstagramPostDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramPostValidator implements Validator<InstagramPostDto> {

    @Override
    public List<String> validate(InstagramPostDto postDto) {
        List<String> errors = new ArrayList<>();

        if (postDto == null) {
            errors.add("Post DTO cannot be null");
            return errors;
        }

        if (postDto.getContent() == null || postDto.getContent().trim().isEmpty()) {
            errors.add("Post content is required");
        }

        if (postDto.getContent() != null && postDto.getContent().length() > 2000) {
            errors.add("Post content must not exceed 2000 characters");
        }

        if (postDto.getPageId() == null) {
            errors.add("Page ID is required");
        }

        return errors;
    }

    @Override
    public boolean isValid(InstagramPostDto postDto) {
        return validate(postDto).isEmpty();
    }
}
