package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class FakeStoreClientTest {

    private FakeStoreClient fakeStoreClient;

    @Mock
    private RestTemplateBuilder restTemplateBuilder;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        fakeStoreClient = new FakeStoreClient();
        ReflectionTestUtils.setField(fakeStoreClient, "restTemplateBuilder", restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    void getAllFakeStoreProducts_returnsListFromExchange() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Watch");
        ResponseEntity<List<FakeStoreProductDto>> response = new ResponseEntity<>(List.of(dto), HttpStatus.OK);

        when(restTemplate.exchange(eq("https://fakestoreapi.com/products"), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(response);

        List<FakeStoreProductDto> result = fakeStoreClient.getAllFakeStoreProducts();

        assertEquals(1, result.size());
        assertEquals("Watch", result.get(0).getTitle());
    }

    @Test
    void getFakeStoreProductsById_returnsBodyWhenSuccessful() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Headphones");
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(FakeStoreProductDto.class), anyLong())).thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.getFakeStoreProductsById(3L);

        assertNotNull(result);
        assertEquals("Headphones", result.getTitle());
    }

    @Test
    void createFakeStoreProduct_returnsCreatedBody() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Lamp");
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.CREATED);

        when(restTemplate.postForEntity("https://fakestoreapi.com/products", dto, FakeStoreProductDto.class)).thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.createFakeStoreProduct(dto);

        assertEquals("Lamp", result.getTitle());
    }

    @Test
    void updateFakeStoreProduct_returnsUpdatedBody() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Speaker");
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(FakeStoreProductDto.class), anyLong()))
                .thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.updateFakeStoreProduct(10L, dto);

        assertEquals("Speaker", result.getTitle());
    }

    @Test
    void deleteFakeStoreProduct_invokesDeleteRequest() {
        fakeStoreClient.deleteFakeStoreProduct(8L);
    }
}
