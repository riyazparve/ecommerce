package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import com.riyaz.ecom.productcatalog.validator.ProductValidator;
import org.junit.jupiter.api.Assertions;
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
    void getAllProducts_returnsMappedDTOs() {
        String laptop = "Laptop";

        Product product = new Product();
        product.setName(laptop);
        ProductDto dto = new ProductDto();
        dto.setName(laptop);

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        ResponseEntity<List<ProductDto>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(laptop, response.getBody().get(0).getName());
        verify(productService).getAllProducts(); //verify getAllProducts was called only once
    }

    @Test
    void getProductById_validatesAndReturnsDTO() {
        String phone = "Phone";
        long id = 7L;

        Product product = new Product();
        product.setName(phone);
        ProductDto dto = new ProductDto();
        dto.setName(phone);

        when(productService.getProductById(id)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        ResponseEntity<ProductDto> response = productController.getProductById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(phone, response.getBody().getName());
        verify(productValidator).validateProductId(id);
        verify(productService).getProductById(id);
    }

    @Test
    void getProductById_whenServiceReturnsNull_throwsNotFound() {
        long id = 9L;
        when(productService.getProductById(id)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> productController.getProductById(id));
        verify(productValidator).validateProductId(id);
    }

    @Test
    void createProduct_returnsCreatedDTO() {
        String tablet = "Tablet";

        Product product = new Product();
        product.setName(tablet);
        ProductDto requestDto = new ProductDto();
        requestDto.setName(tablet);
        ProductDto responseDto = new ProductDto();
        responseDto.setName(tablet);

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.createProduct(product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.createProduct(requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(tablet, response.getBody().getName());
    }

    @Test
    void createProducts_returnsListOfDTOs() {
        String a = "A";
        String b = "B";

        ProductDto first = new ProductDto();
        first.setName(a);
        ProductDto second = new ProductDto();
        second.setName(b);
        Product productA = new Product();
        productA.setName(a);
        Product productB = new Product();
        productB.setName(b);

        when(productDtoMapper.toEntity(first)).thenReturn(productA);
        when(productDtoMapper.toEntity(second)).thenReturn(productB);
        when(productService.createProduct(productA)).thenReturn(productA);
        when(productService.createProduct(productB)).thenReturn(productB);
        when(productDtoMapper.toDto(productA)).thenReturn(first);
        when(productDtoMapper.toDto(productB)).thenReturn(second);

        ResponseEntity<List<ProductDto>> response = productController.createProduct(List.of(first, second));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals(a, response.getBody().get(0).getName());
        assertEquals(b, response.getBody().get(1).getName());
    }

    @Test
    void replaceProduct_returnsUpdatedDTO() {
        String updated = "Updated";
        long id = 3L;

        ProductDto requestDto = new ProductDto();
        requestDto.setName(updated);
        Product product = new Product();
        product.setName(updated);
        ProductDto responseDto = new ProductDto();
        responseDto.setName(updated);

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.replaceProduct(id, product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.replaceProduct(id, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(updated, response.getBody().getName());
    }

    @Test
    void updateProduct_returnsUpdatedDto() {
        String patched = "Patched";
        long id = 4L;

        ProductDto requestDto = new ProductDto();
        requestDto.setName(patched);
        Product product = new Product();
        product.setName(patched);
        ProductDto responseDto = new ProductDto();
        responseDto.setName(patched);

        when(productDtoMapper.toEntity(requestDto)).thenReturn(product);
        when(productService.updateProduct(id, product)).thenReturn(product);
        when(productDtoMapper.toDto(product)).thenReturn(responseDto);

        ResponseEntity<ProductDto> response = productController.updateProduct(id, requestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        assertEquals(patched, response.getBody().getName());
    }

    @Test
    void deleteProduct_returnsNoContent() {
        long id = 5L;

        ResponseEntity<ProductDto> response = productController.deleteProduct(id);
        
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteProduct(id);
    }
}
