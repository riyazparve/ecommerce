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
import org.springframework.web.client.RestClientException;
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
        String url = BASE_URL + "/products/{id}";

        // If we do not need response status we can directly use restTemplate.getForObject which is similar to restTemplate.getForEntity
//        return restTemplate.getForObject(url, FakeStoreProductDto.class, id);

        // If we have complex request like we need to add auth headers, etc we use restTemplate.exchange method
        // 1. Define the specific target FakeStore URL to get all products
//        String url = BASE_URL + "/products/" + id;

        // 2. Set up the authorization headers
//        HttpHeaders headers = new HttpHeaders();

        // 3. HttpEntity (GET request, so body is null)
//        HttpEntity<Object> entity = new HttpEntity<>(headers);

        // 6. Call your generic exchange method, response is always not null
//        ResponseEntity<FakeStoreProductDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, FakeStoreProductDto.class);

        // 7. Return the body if present, or an empty list to prevent NullPointerExceptions
//        return response.getBody();


        try {
            // Getting response entity to check the http response was success or failure
            ResponseEntity<FakeStoreProductDto> responseEntity = restTemplate.getForEntity(url, FakeStoreProductDto.class, id);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.hasBody()) {
                return responseEntity.getBody();
            }
        } catch (RestClientException e) {
            // We can throw new exception here but for now we will just return null
            throw new RuntimeException(e);
//            return null;
        }

        return null;
    }

    public FakeStoreProductDto createFakeStoreProduct(FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = BASE_URL + "/products";
        try {
            ResponseEntity<FakeStoreProductDto> createFakeStoreProductEntity = restTemplate.postForEntity(url, fakeStoreProductDto, FakeStoreProductDto.class);
            if (createFakeStoreProductEntity.getStatusCode().is2xxSuccessful()) {
                return createFakeStoreProductEntity.getBody();
            }
            throw new RuntimeException("Unable to create due to HttpStatus: " + createFakeStoreProductEntity.getStatusCode().toString());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public FakeStoreProductDto replaceFakeStoreProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        return updateFakeStoreProduct(id, fakeStoreProductDto);
    }

    public FakeStoreProductDto updateFakeStoreProduct(Long id, FakeStoreProductDto fakeStoreProductDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        // 1. Define the specific target FakeStore URL to update product with given id
        String url = BASE_URL + "/products/{id}";
        try {

            // 2. Set up the authorization headers
        HttpHeaders headers = new HttpHeaders();

            // 3. HttpEntity (GET request, so body is null)
        HttpEntity<Object> entity = new HttpEntity<>(headers);

            // 6. Call your generic exchange method, response is always not null
//            restTemplate.put(url,fakeStoreProductDto, id); not using the void return type version
        ResponseEntity<FakeStoreProductDto> updateFakeStoreProductEntity = restTemplate.exchange(url, HttpMethod.PUT, entity, FakeStoreProductDto.class, id);

            // 7. Return the body if present, or an empty list to prevent NullPointerExceptions
//        return response.getBody();
            if (updateFakeStoreProductEntity.getStatusCode().is2xxSuccessful()) {
                return updateFakeStoreProductEntity.getBody();
            }
            throw new RuntimeException("Unable to update due to HttpStatus: " + updateFakeStoreProductEntity.getStatusCode().toString());
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFakeStoreProduct(Long id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        String url = BASE_URL + "/products/{id}";
        try {
            restTemplate.delete(url, id);
        } catch (RestClientException e) {
            throw new RuntimeException(e);
        }
    }
}
