package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import com.riyaz.ecom.productcatalog.validator.ProductValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private IProductService productService;

    @Mock
    private ProductValidator productValidator;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts_returnsMappedDtos() {
        Product product = new Product();
        product.setName("Laptop");
        ProductDto dto = new ProductDto();
        dto.setName("Laptop");

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Laptop", response.getBody().get(0).getName());
        verify(productService).getAllProducts();
    }

    @Test
    void getProductById_validatesAndReturnsDto() {
        Product product = new Product();
        product.setName("Phone");
        ProductDto dto = new ProductDto();
        dto.setName("Phone");

        when(productService.getProductById(7L)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        ResponseEntity<ProductDto> response = productController.getProductById(7L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Phone", response.getBody().getName());
        verify(productValidator).validateProductId(7L);
        verify(productService).getProductById(7L);
    }

    @Test
    void getProductById_whenServiceReturnsNull_throwsNotFound() {
        when(productService.getProductById(9L)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(9L));
        verify(productValidator).validateProductId(9L);
    }

    @Test
    void createProduct_returnsCreatedDto() {
        Product product = new Product();
        product.setName("Tablet");
        ProductDto requestDto = new ProductDto();
        requestDto.setName("Tablet");
        ProductDto responseDto = new ProductDto();
        responseDto.setName("Tablet");

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.createProduct(product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.createProduct(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Tablet", response.getBody().getName());
    }

    @Test
    void createProducts_returnsListOfDtos() {
        ProductDto first = new ProductDto();
        first.setName("A");
        ProductDto second = new ProductDto();
        second.setName("B");
        Product productA = new Product();
        productA.setName("A");
        Product productB = new Product();
        productB.setName("B");

        when(productDtoMapper.toEntity(first)).thenReturn(productA);
        when(productDtoMapper.toEntity(second)).thenReturn(productB);
        when(productService.createProduct(productA)).thenReturn(productA);
        when(productService.createProduct(productB)).thenReturn(productB);
        when(productDtoMapper.toDto(productA)).thenReturn(first);
        when(productDtoMapper.toDto(productB)).thenReturn(second);

        ResponseEntity<List<ProductDto>> response = productController.createProduct(List.of(first, second));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("A", response.getBody().get(0).getName());
        assertEquals("B", response.getBody().get(1).getName());
    }

    @Test
    void replaceProduct_returnsUpdatedDto() {
        ProductDto requestDto = new ProductDto();
        requestDto.setName("Updated");
        Product product = new Product();
        product.setName("Updated");
        ProductDto responseDto = new ProductDto();
        responseDto.setName("Updated");

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.replaceProduct(3L, product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.replaceProduct(3L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getName());
    }

    @Test
    void updateProduct_returnsUpdatedDto() {
        ProductDto requestDto = new ProductDto();
        requestDto.setName("Patched");
        Product product = new Product();
        product.setName("Patched");
        ProductDto responseDto = new ProductDto();
        responseDto.setName("Patched");

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.updateProduct(4L, product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.updateProduct(4L, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Patched", response.getBody().getName());
    }

    @Test
    void deleteProduct_returnsNoContent() {
        ResponseEntity<ProductDto> response = productController.deleteProduct(5L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteProduct(5L);
    }
}
