package com.riyaz.ecom.productcatalog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDto {
    private String searchTerm;
    private Integer  pageNumber;
    private Integer pageSize;
}
