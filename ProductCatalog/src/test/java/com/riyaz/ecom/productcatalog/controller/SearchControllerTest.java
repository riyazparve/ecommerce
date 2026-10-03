package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.dto.SearchRequestDto;
import com.riyaz.ecom.productcatalog.service.ISearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchControllerTest {

    @Mock
    private ISearchService searchService;

    @InjectMocks
    private SearchController searchController;

    @Test
    void getAllProducts_returnsPageFromSearchService() {
        ProductDto productDto = new ProductDto();
        productDto.setName("Camera");
        Page<ProductDto> expected = new PageImpl<>(List.of(productDto));

        when(searchService.findAll(any(Pageable.class))).thenReturn(expected);

        Page<ProductDto> actual = searchController.getAllProducts(0, 5, "name", true);

        assertSame(expected, actual);
        verify(searchService).findAll(any(Pageable.class));
    }

    @Test
    void getProductsByName_delegatesToSearchService() {
        SearchRequestDto request = new SearchRequestDto();
        request.setSearchTerm("Phone");
        request.setPageNumber(0);
        request.setPageSize(3);
        ProductDto productDto = new ProductDto();
        productDto.setName("Phone");
        Page<ProductDto> expected = new PageImpl<>(List.of(productDto));

        when(searchService.search(request)).thenReturn(expected);

        Page<ProductDto> actual = searchController.getProductsByName(request);

        assertSame(expected, actual);
        assertEquals(1, actual.getContent().size());
        verify(searchService).search(request);
    }
}
