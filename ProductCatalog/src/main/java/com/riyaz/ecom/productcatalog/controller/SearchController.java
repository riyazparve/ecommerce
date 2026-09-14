package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.dto.SearchRequestDto;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.ISearchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final ISearchService searchService;
    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    //TODO 2.1. Browsing: Users should be able to browse products by different categories.
    @GetMapping("/all")
    public Page<ProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return searchService.findAll(pageable);
    }

    //TODO 2.3. Search: Users must be able to search for products using keywords.
    @GetMapping
    public Page<ProductDto> getProductsByName(@RequestBody SearchRequestDto searchRequestDto) {
        return searchService.search(searchRequestDto);
    }
}
