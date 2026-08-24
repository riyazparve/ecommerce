package com.riyaz.ecom.jpademo.validator;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T entity);
    boolean isValid(T entity);
}
