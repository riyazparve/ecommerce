package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
public class FakeStoreClient {
    private final String BASE_URL = "https://fakestoreapi.com";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public List<FakeStoreProductDto> getAllFakeStoreProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        // 1. Define the specific target FakeStore URL to get all products
        String url = BASE_URL + "/products";

        // 2. Define the ParameterizedTypeReference for List<FakeStoreProductDto>
        ParameterizedTypeReference<List<FakeStoreProductDto>> responseType = new ParameterizedTypeReference<List<FakeStoreProductDto>>() {
        };

        // 3. Set up the authorization headers
        HttpHeaders headers = new HttpHeaders();

        // 4. HttpEntity (GET request, so body is null)
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        // 6. Call your generic exchange method, response is always not null
        ResponseEntity<List<FakeStoreProductDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

        // 7. Return the body if present, or an empty list to prevent NullPointerExceptions
        return response.getBody() != null ? response.getBody() : Collections.emptyList();
    }


    public FakeStoreProductDto getFakeStoreProductsById(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // 1. Define the specific target FakeStore URL to get all products
        String url = BASE_URL + "/products/" + id;

        // 2. Set up the authorization headers
        HttpHeaders headers = new HttpHeaders();

        // 3. HttpEntity (GET request, so body is null)
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        // 6. Call your generic exchange method, response is always not null
        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, FakeStoreProductDto.class);

        // 7. Return the body if present, or an empty list to prevent NullPointerExceptions
        return response.getBody();
    }
}
