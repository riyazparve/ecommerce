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
        String chair = "Chair";

        Product product = new Product();
        product.setName(chair);
        when(productRepository.findAll()).thenReturn(List.of(product));

        List<Product> result = storageProductService.getAllProducts();

        assertEquals(1, result.size());
        assertEquals(chair, result.get(0).getName());
    }

    @Test
    void getProductById_whenIdIsInvalid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> storageProductService.getProductById(0L));
        assertThrows(IllegalArgumentException.class, () -> storageProductService.getProductById(-1L));
    }

    @Test
    void getProductById_whenIdIsValid_returnsEntity() {
        String desk = "Desk";

        Product product = new Product();
        product.setName(desk);
        when(productRepository.findById(5L)).thenReturn(Optional.of(product));

        Product result = storageProductService.getProductById(5L);

        assertEquals(desk, result.getName());
    }

    @Test
    void createProduct_whenCategoryExists_reusesSavedCategory() {
        String electronics = "Electronics";

        Category category = new Category();
        category.setName(electronics);
        Category storedCategory = new Category();
        storedCategory.setName(electronics);
        Product product = new Product();
        product.setCategory(category);
        product.setName("Monitor");

        when(categoryRepository.findByName(electronics)).thenReturn(Optional.of(storedCategory));
        when(productRepository.save(product)).thenReturn(product);

        Product result = storageProductService.createProduct(product);

        assertEquals(electronics, result.getCategory().getName());
        verify(categoryRepository).findByName(electronics);
    }

    @Test
    void createProduct_whenCategoryDoesNotExist_savesNewCategory() {
        String books = "Books";

        Category category = new Category();
        category.setName(books);
        Product product = new Product();
        product.setCategory(category);
        product.setName("Clean Code");

        when(categoryRepository.findByName(books)).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);
        when(productRepository.save(product)).thenReturn(product);

        Product result = storageProductService.createProduct(product);

        assertEquals(books, result.getCategory().getName());
        verify(categoryRepository).save(category);
    }

    @Test
    void replaceProduct_andUpdateProduct_delegateToRepository() {
        String replacementString = "Replacement";
        long id = 10L;

        Product replacement = new Product();
        replacement.setName(replacementString);
        when(productRepository.save(replacement)).thenReturn(replacement);

        Product replaceResult = storageProductService.replaceProduct(id, replacement);
        Product updateResult = storageProductService.updateProduct(id, replacement);

        assertEquals(replacementString, replaceResult.getName());
        assertEquals(replacementString, updateResult.getName());
        verify(productRepository, times(2)).save(replacement);
    }

    @Test
    void deleteProduct_callsRepositoryDeleteById() {
        long id = 9L;

        storageProductService.deleteProduct(id);
        verify(productRepository).deleteById(id);
    }
}
