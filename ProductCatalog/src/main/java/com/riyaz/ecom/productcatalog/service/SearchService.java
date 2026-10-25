package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.dto.SearchRequestDto;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SearchService implements ISearchService {
    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    public SearchService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        // MapStruct method passed as a method reference
        return productPage.map(productDtoMapper::toDto);
    }

    @Override
    public Page<ProductDto> search(SearchRequestDto searchRequestDto) {
        Sort sortByIdDesc = Sort.by("id").descending();
        Sort sortByPriceDesc = Sort.by(Sort.Direction.DESC, "price");
        Sort sort = sortByIdDesc.and(sortByPriceDesc);
        PageRequest pageableRequest = PageRequest.of(searchRequestDto.getPageNumber(), searchRequestDto.getPageSize(), sort);
        Page<Product> productPage = productRepository.findByName(searchRequestDto.getSearchTerm(), pageableRequest);
        return productPage.map(productDtoMapper::toDto);
    }
}
