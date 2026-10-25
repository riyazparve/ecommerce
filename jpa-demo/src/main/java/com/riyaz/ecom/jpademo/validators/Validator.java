package com.riyaz.ecom.jpademo.validators;

import java.util.List;

public interface Validator<T> {
    List<String> validate(T entity);
}
