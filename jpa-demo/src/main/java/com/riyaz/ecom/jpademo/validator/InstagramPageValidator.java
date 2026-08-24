package com.riyaz.ecom.jpademo.validator;

import com.riyaz.ecom.jpademo.dto.InstagramPageDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InstagramPageValidator implements Validator<InstagramPageDto> {

    @Override
    public List<String> validate(InstagramPageDto pageDto) {
        List<String> errors = new ArrayList<>();

        if (pageDto == null) {
            errors.add("Page DTO cannot be null");
            return errors;
        }

        if (pageDto.getCreatorId() == null) {
            errors.add("Creator ID is required");
        }

        return errors;
    }

    @Override
    public boolean isValid(InstagramPageDto pageDto) {
        return validate(pageDto).isEmpty();
    }
}
