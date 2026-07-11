package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

public interface IProductService {
    List<Product> getAllProducts() ;
    Product getProductById(Long id) ;
}
