package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import com.riyaz.ecom.productcatalog.validator.ProductValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    //TODO 2.1. Browsing: Users should be able to browse products by different categories.
    //TODO 2.3. Search: Users must be able to search for products using keywords.
}
