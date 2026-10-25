package com.riyaz.ecom.productcatalog.validators;

import com.riyaz.ecom.productcatalog.exceptions.InvalidProductIdException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {
    
    public void validateProductId(Long id) {
        if (id == null || id <= 0) {
            throw new InvalidProductIdException("Product ID must be greater than 0");
        }
    }
}
