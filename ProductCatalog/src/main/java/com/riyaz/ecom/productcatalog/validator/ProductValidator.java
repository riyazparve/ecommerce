package com.riyaz.ecom.productcatalog.validator;

import com.riyaz.ecom.productcatalog.exception.InvalidProductIdException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    
    public void validateProductId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidProductIdException("Product ID must be greater than 0");
        }
    }
}
