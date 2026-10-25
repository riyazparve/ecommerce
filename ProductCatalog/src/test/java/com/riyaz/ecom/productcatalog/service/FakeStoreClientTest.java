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

import static org.junit.jupiter.api.Assertions.*;
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
    void setUp() throws Exception {
        try (AutoCloseable autoCloseable = MockitoAnnotations.openMocks(this)) {
            // this is a try-with-resources block to ensure that the mocks are closed after use
            assertNotNull(autoCloseable);
        }
        fakeStoreClient = new FakeStoreClient();
        ReflectionTestUtils.setField(fakeStoreClient, "restTemplateBuilder", restTemplateBuilder);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    void getAllFakeStoreProducts_returnsListFromExchange() {
        String watch = "Watch";

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(watch);
        ResponseEntity<List<FakeStoreProductDto>> response = new ResponseEntity<>(List.of(dto), HttpStatus.OK);

        when(restTemplate.exchange(eq("https://fakestoreapi.com/products"), eq(HttpMethod.GET), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(response);

        List<FakeStoreProductDto> result = fakeStoreClient.getAllFakeStoreProducts();

        assertEquals(1, result.size());
        assertEquals(watch, result.get(0).getTitle());
    }

    @Test
    void getFakeStoreProductsById_returnsBodyWhenSuccessful() {
        String headphones = "Headphones";

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(headphones);
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.getForEntity(anyString(), eq(FakeStoreProductDto.class), anyLong())).thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.getFakeStoreProductsById(3L);

        assertNotNull(result);
        assertEquals(headphones, result.getTitle());
    }

    @Test
    void createFakeStoreProduct_returnsCreatedBody() {
        String lamp = "Lamp";

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(lamp);
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.CREATED);

        when(restTemplate.postForEntity("https://fakestoreapi.com/products", dto, FakeStoreProductDto.class)).thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.createFakeStoreProduct(dto);

        assertEquals(lamp, result.getTitle());
    }

    @Test
    void updateFakeStoreProduct_returnsUpdatedBody() {
        String speaker = "Speaker";

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(speaker);
        ResponseEntity<FakeStoreProductDto> response = new ResponseEntity<>(dto, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), eq(HttpMethod.PUT), any(HttpEntity.class), eq(FakeStoreProductDto.class), anyLong()))
                .thenReturn(response);

        FakeStoreProductDto result = fakeStoreClient.updateFakeStoreProduct(10L, dto);

        assertEquals(speaker, result.getTitle());
    }

    @Test
    void deleteFakeStoreProduct_invokesDeleteRequest() {
        // invoke delete and assert it does not throw an exception
        assertDoesNotThrow(() -> fakeStoreClient.deleteFakeStoreProduct(8L));
    }

    @Test
    void deleteFakeStoreProduct_throwsException() {
        // invoke delete and assert it throws an exception
        assertThrows(IllegalArgumentException.class, () -> fakeStoreClient.deleteFakeStoreProduct(null));
    }
}
