package com.riyaz.ecom.productcatalog.validator;

import com.riyaz.ecom.productcatalog.exception.InvalidProductIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidatorTest {

    private final ProductValidator validator = new ProductValidator();

    @Test
    void validateProductId_nullOrNonPositive_throwsException() {
        assertThrows(InvalidProductIdException.class, () -> validator.validateProductId(null));
        assertThrows(InvalidProductIdException.class, () -> validator.validateProductId(0L));
        assertThrows(InvalidProductIdException.class, () -> validator.validateProductId(-2L));
    }

    @Test
    void validateProductId_positiveValue_isAllowed() {
        assertDoesNotThrow(() -> validator.validateProductId(1L));
        assertDoesNotThrow(() -> validator.validateProductId(25L));
    }
}
