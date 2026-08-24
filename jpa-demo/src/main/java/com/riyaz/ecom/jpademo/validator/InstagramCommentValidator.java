package com.riyaz.ecom.jpademo.validator;

import com.riyaz.ecom.jpademo.dto.InstagramCommentDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramCommentValidator implements Validator<InstagramCommentDto> {

    @Override
    public List<String> validate(InstagramCommentDto commentDto) {
        List<String> errors = new ArrayList<>();

        if (commentDto == null) {
            errors.add("Comment DTO cannot be null");
            return errors;
        }

        if (commentDto.getText() == null || commentDto.getText().trim().isEmpty()) {
            errors.add("Comment text is required");
        }

        if (commentDto.getText() != null && commentDto.getText().length() > 1000) {
            errors.add("Comment text must not exceed 1000 characters");
        }

        if (commentDto.getPostId() == null) {
            errors.add("Post ID is required");
        }

        if (commentDto.getUserId() == null) {
            errors.add("User ID is required");
        }

        return errors;
    }

    @Override
    public boolean isValid(InstagramCommentDto commentDto) {
        return validate(commentDto).isEmpty();
    }
}
