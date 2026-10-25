package com.riyaz.ecom.productcatalog.repository;

import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("dev")
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    public void testFindById() {
        Optional<Category> category = categoryRepository.findById(1L);
        assertTrue(category.isPresent());
        assertEquals("Accessories", category.get().getName());
//        askForProducts(category.get());
    }

    @Test
    @Transactional
    public void testFindByName() {
        assertNotNull(categoryRepository);
        Optional<Category> category = categoryRepository.findByName("Electronics");
        assertTrue(category.isPresent());
        assertEquals("Electronics", category.get().getName());
//        askForProducts(category.get());
    }

    @Test
    @Transactional
    public void testFindAll_NPlusOneProblem() {
        List<Category> categories = categoryRepository.findAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
//        for (Category category : categories) {
//            askForProducts(category);
//        }
    }


    private static void askForProducts(Category category) {
        for (Product product : category.getProducts()) {
            assertNotNull(product);
        }
    }

}