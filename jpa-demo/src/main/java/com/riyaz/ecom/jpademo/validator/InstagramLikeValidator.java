package com.riyaz.ecom.jpademo.validator;

import com.riyaz.ecom.jpademo.dto.InstagramLikeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramLikeValidator implements Validator<InstagramLikeDto> {

    @Override
    public List<String> validate(InstagramLikeDto likeDto) {
        List<String> errors = new ArrayList<>();

        if (likeDto == null) {
            errors.add("Like DTO cannot be null");
            return errors;
        }

        if (likeDto.getPostId() == null) {
            errors.add("Post ID is required");
        }

        if (likeDto.getUserId() == null) {
            errors.add("User ID is required");
        }

        return errors;
    }

    @Override
    public boolean isValid(InstagramLikeDto likeDto) {
        return validate(likeDto).isEmpty();
    }
}
