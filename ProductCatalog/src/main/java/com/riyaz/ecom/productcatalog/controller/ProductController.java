package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    @Qualifier("fakeStoreProductService")
    private IProductService productService;

//    Wiring beans using Constructor Inject this is replaced by @Autowired
//    public ProductController(IProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return products.stream().map(ProductDtoMapper::toDto).toList();
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable("productId") Long id) {
        return ProductDtoMapper.toDto(productService.getProductById(id));
    }


}
