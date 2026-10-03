package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.repository.CategoryRepository;
import com.riyaz.ecom.productcatalog.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private StorageProductService storageProductService;

    @Test
    void getAllProducts_returnsEverythingFromRepository() {
        Product product = new Product();
        product.setName("Chair");
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = storageProductService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals("Chair", result.get(0).getName());
    }

    @Test
    void getProductById_whenIdIsInvalid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> storageProductService.getProductById(0L));
        assertThrows(IllegalArgumentException.class, () -> storageProductService.getProductById(-1L));
    }

    @Test
    void getProductById_whenIdIsValid_returnsEntity() {
        Product product = new Product();
        product.setName("Desk");
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));

        Product result = storageProductService.getProductById(5L);

        assertEquals("Desk", result.getName());
    }

    @Test
    void createProduct_whenCategoryExists_reusesSavedCategory() {
        Category category = new Category();
        category.setName("Electronics");
        Category storedCategory = new Category();
        storedCategory.setName("Electronics");
        Product product = new Product();
        product.setCategory(category);
        product.setName("Monitor");

        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(storedCategory));
        when(productRepository.save(product)).thenReturn(product);

        Product result = storageProductService.createProduct(product);

        assertEquals("Electronics", result.getCategory().getName());
        verify(categoryRepository).findByName("Electronics");
    }

    @Test
    void createProduct_whenCategoryDoesNotExist_savesNewCategory() {
        Category category = new Category();
        category.setName("Books");
        Product product = new Product();
        product.setCategory(category);
        product.setName("Clean Code");

        when(categoryRepository.findByName("Books")).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);
        when(productRepository.save(product)).thenReturn(product);

        Product result = storageProductService.createProduct(product);

        assertEquals("Books", result.getCategory().getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void replaceProduct_andUpdateProduct_delegateToRepository() {
        Product replacement = new Product();
        replacement.setName("Replacement");
        when(productRepository.save(replacement)).thenReturn(replacement);

        Product replaceResult = storageProductService.replaceProduct(10L, replacement);
        Product updateResult = storageProductService.updateProduct(10L, replacement);

        assertEquals("Replacement", replaceResult.getName());
        assertEquals("Replacement", updateResult.getName());
        verify(productRepository, times(2)).save(replacement);
    }

    @Test
    void deleteProduct_callsRepositoryDeleteById() {
        storageProductService.deleteProduct(9L);

        verify(productRepository).deleteById(9L);
    }
}
