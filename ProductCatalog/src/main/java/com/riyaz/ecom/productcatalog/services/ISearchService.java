package com.riyaz.ecom.productcatalog.services;

import com.riyaz.ecom.productcatalog.dtos.ProductDto;
import com.riyaz.ecom.productcatalog.dtos.SearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISearchService {
    Page<ProductDto> findAll(Pageable pageable);

    Page<ProductDto> search(SearchRequestDto searchRequestDto);
}
