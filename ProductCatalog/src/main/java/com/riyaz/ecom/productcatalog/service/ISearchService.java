package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.dto.SearchRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISearchService {
    Page<ProductDto> findAll(Pageable pageable);

    Page<ProductDto> search(SearchRequestDto searchRequestDto);
}
