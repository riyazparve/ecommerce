package com.riyaz.ecom.productcatalog.services;

import com.riyaz.ecom.productcatalog.dtos.ProductDto;
import com.riyaz.ecom.productcatalog.dtos.SearchRequestDto;
import com.riyaz.ecom.productcatalog.mappers.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.models.Product;
import com.riyaz.ecom.productcatalog.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDtoMapper productDtoMapper;

    @InjectMocks
    private SearchService searchService;

    @Test
    void findAll_mapsRepositoryPageToProductDTOs() {
        String laptop = "Laptop";

        Product product = new Product();
        product.setName(laptop);
        ProductDto dto = new ProductDto();
        dto.setName(laptop);
        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        Page<ProductDto> result = searchService.findAll(PageRequest.of(0, 5));

        assertEquals(1, result.getContent().size());
        assertEquals(laptop, result.getContent().get(0).getName());
    }

    @Test
    void search_mapsMatchingProductsToDTOs() {
        String phone = "Phone";
        
        SearchRequestDto request = new SearchRequestDto();
        request.setSearchTerm(phone);
        request.setPageNumber(1);
        request.setPageSize(2);

        Product product = new Product();
        product.setName(phone);
        ProductDto dto = new ProductDto();
        dto.setName(phone);
        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findByName(eq(phone), any(Pageable.class))).thenReturn(productPage);
        when(productDtoMapper.toDto(product)).thenReturn(dto);

        Page<ProductDto> result = searchService.search(request);

        assertEquals(1, result.getContent().size());
        assertEquals(phone, result.getContent().get(0).getName());
    }
}
