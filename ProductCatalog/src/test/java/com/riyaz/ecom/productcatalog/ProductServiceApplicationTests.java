package com.riyaz.ecom.productcatalog;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;
import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.mapper.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private FakeStoreProductDtoMapper fakeStoreProductDtoMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void applicationMainRunsWithoutWebServer() {
        assertDoesNotThrow(() -> ProductCatalogServiceApplication.main(new String[]{"--spring.main.web-application-type=none"}));
    }

    @Test
    void productDtoMapper_roundTripsProductAndCategory() {
        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Smart devices");

        Product product = new Product();
        product.setName("Laptop");
        product.setDescription("Core i7");
        product.setCategory(category);
        product.setPrice(999.99);
        product.setImageUrl("/images/laptop.png");
        product.setIsPrimeProduct(Boolean.TRUE);

        ProductDto productDto = productDtoMapper.toDto(product);
        assertNotNull(productDto);
        assertEquals("Laptop", productDto.getName());
        assertEquals("Core i7", productDto.getDescription());
        assertEquals(999.99, productDto.getPrice());
        assertEquals("/images/laptop.png", productDto.getImageUrl());
        assertNotNull(productDto.getCategory());
        assertEquals("Electronics", productDto.getCategory().getName());

        Product mappedBack = productDtoMapper.toEntity(productDto);
        assertNotNull(mappedBack);
        assertEquals("Laptop", mappedBack.getName());
        assertEquals("Core i7", mappedBack.getDescription());
        assertEquals(999.99, mappedBack.getPrice());
        assertEquals("/images/laptop.png", mappedBack.getImageUrl());
        assertNotNull(mappedBack.getCategory());
        assertEquals("Electronics", mappedBack.getCategory().getName());

        CategoryDto categoryDto = productDtoMapper.toCategoryDto(category);
        assertEquals("Electronics", categoryDto.getName());
        Category mappedCategory = productDtoMapper.toCategory(categoryDto);
        assertEquals("Electronics", mappedCategory.getName());
    }

    @Test
    void fakeStoreProductDtoMapper_mapsToAndFromEntity() {
        FakeStoreProductDto dto = new FakeStoreProductDto();
        dto.setId(7L);
        dto.setTitle("Phone");
        dto.setDescription("A smartphone");
        dto.setPrice(699.00);
        dto.setCategory("electronics");
        dto.setImage("/images/phone.png");

        Product product = fakeStoreProductDtoMapper.toEntity(dto);
        assertNotNull(product);
        assertEquals("Phone", product.getName());
        assertEquals("A smartphone", product.getDescription());
        assertEquals(699.00, product.getPrice());
        assertEquals("/images/phone.png", product.getImageUrl());
        assertNull(product.getCategory());
        assertNull(product.getIsPrimeProduct());

        FakeStoreProductDto mappedBack = fakeStoreProductDtoMapper.toDto(product);
        assertEquals("Phone", mappedBack.getTitle());
        assertEquals("/images/phone.png", mappedBack.getImage());
        assertNull(mappedBack.getCategory());
    }
}
