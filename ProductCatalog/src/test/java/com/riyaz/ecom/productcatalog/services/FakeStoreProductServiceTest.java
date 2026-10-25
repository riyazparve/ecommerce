package com.riyaz.ecom.productcatalog.services;

import com.riyaz.ecom.productcatalog.dtos.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.exceptions.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mappers.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.models.Product;
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
        String keyboard = "Keyboard";

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(keyboard);
        Product product = new Product();
        product.setName(keyboard);

        when(fakeStoreClient.getAllFakeStoreProducts()).thenReturn(List.of(dto));
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        List<Product> result = fakeStoreProductService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(keyboard, result.get(0).getName());
    }

    @Test
    void getProductById_mapsSingleDtoToEntity() {
        String mouse = "Mouse";
        long id = 14L;

        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(mouse);
        Product product = new Product();
        product.setName(mouse);

        when(fakeStoreClient.getFakeStoreProductsById(id)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.getProductById(id);

        assertEquals(mouse, result.getName());
    }

    @Test
    void createProduct_mapsAndPersistsCreatedProduct() {
        String monitor = "Monitor";

        Product product = new Product();
        product.setName(monitor);
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(monitor);

        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.createFakeStoreProduct(dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.createProduct(product);

        assertEquals(monitor, result.getName());
    }

    @Test
    void replaceProduct_mapsAndReturnsReplacement() {
        String router = "Router";
        long id = 21L;

        Product product = new Product();
        product.setName(router);
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(router);

        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.replaceFakeStoreProduct(id, dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.replaceProduct(id, product);

        assertEquals(router, result.getName());
    }

    @Test
    void updateProduct_whenExistingProductMissing_throwsProductNotFound() {
        String cable = "Cable";
        long id = 99L;

        Product product = new Product();
        product.setName(cable);

        when(fakeStoreClient.getFakeStoreProductsById(id)).thenReturn(null);

        assertThrows(ProductNotFoundException.class, () -> fakeStoreProductService.updateProduct(id, product));
    }

    @Test
    void updateProduct_whenExistingProductPresent_returnsUpdatedEntity() {
        String speaker = "Speaker";
        long id = 17L;

        Product product = new Product();
        product.setName(speaker);
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setTitle(speaker);

        when(fakeStoreClient.getFakeStoreProductsById(id)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toDto(product)).thenReturn(dto);
        when(fakeStoreClient.updateFakeStoreProduct(id, dto)).thenReturn(dto);
        when(fakeStoreProductDtoMapper.toEntity(dto)).thenReturn(product);

        Product result = fakeStoreProductService.updateProduct(id, product);

        assertEquals(speaker, result.getName());
    }

    @Test
    void deleteProduct_delegatesToClient() {
        long id = 12L;

        fakeStoreProductService.deleteProduct(id);
        verify(fakeStoreClient).deleteFakeStoreProduct(id); // verify deleteFakeStoreProduct is called only once
    }
}
