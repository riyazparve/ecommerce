package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.exception.InvalidProductIdException;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerAdvisorTest {

    private final ControllerAdvisor advisor = new ControllerAdvisor();

    @Test
    void invalidProductIdException_returnsBadRequest() {
        ResponseEntity<String> response = advisor.handleInvalidProductId(new InvalidProductIdException("Product ID must be greater than 0"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Product ID must be greater than 0", response.getBody());
    }

    @Test
    void productNotFoundException_returnsNotFound() {
        ResponseEntity<String> response = advisor.handleProductNotFound(new ProductNotFoundException(42L));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found with ID: 42", response.getBody());
    }
}
