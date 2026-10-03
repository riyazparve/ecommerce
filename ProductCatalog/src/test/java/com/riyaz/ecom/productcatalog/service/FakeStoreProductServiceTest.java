package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FakeStoreProductServiceTest {

    @Mock
    private FakeStoreClient fakeStoreClient;

    @Mock
    private FakeStoreProductDtoMapper fakeStoreProductDtoMapper;

    @InjectMocks
    private FakeStoreProductService fakeStoreProductService;

    @Test
    void getAllProducts_mapsDtosToEntities() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Keyboard");
        Product product = new Product();
        product.setName("Keyboard");

        when(fakeStoreClient.getAllFakeStoreProducts()).thenReturn(List.of(dto));
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        List<Product> result = fakeStoreProductService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Keyboard", result.get(0).getName());
    }

    @Test
    void getProductById_mapsSingleDtoToEntity() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Mouse");
        Product product = new Product();
        product.setName("Mouse");

        when(fakeStoreClient.getFakeStoreProductsById(14L)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.getProductById(14L);

        assertEquals("Mouse", result.getName());
    }

    @Test
    void createProduct_mapsAndPersistsCreatedProduct() {
        Product product = new Product();
        product.setName("Monitor");
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Monitor");

        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.createFakeStoreProduct(dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.createProduct(product);

        assertEquals("Monitor", result.getName());
    }

    @Test
    void replaceProduct_mapsAndReturnsReplacement() {
        Product product = new Product();
        product.setName("Router");
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Router");

        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.replaceFakeStoreProduct(21L, dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.replaceProduct(21L, product);

        assertEquals("Router", result.getName());
    }

    @Test
    void updateProduct_whenExistingProductMissing_throwsProductNotFound() {
        Product product = new Product();
        product.setName("Cable");

        when(fakeStoreClient.getFakeStoreProductsById(99L)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> fakeStoreProductService.updateProduct(99L, product));
    }

    @Test
    void updateProduct_whenExistingProductPresent_returnsUpdatedEntity() {
        Product product = new Product();
        product.setName("Speaker");
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle("Speaker");

        when(fakeStoreClient.getFakeStoreProductsById(17L)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.updateFakeStoreProduct(17L, dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.updateProduct(17L, product);

        assertEquals("Speaker", result.getName());
    }

    @Test
    void deleteProduct_delegatesToClient() {
        fakeStoreProductService.deleteProduct(12L);

        verify(fakeStoreClient).deleteFakeStoreProduct(12L);
    }
}
