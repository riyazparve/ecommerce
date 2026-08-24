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
@RequestMapping("/products")
public class ProductController {
    private final IProductService productService;
    private final ProductValidator productValidator;
    private final ProductDtoMapper productDtoMapper;

    public ProductController(IProductService productService, ProductValidator productValidator, ProductDtoMapper productDtoMapper) {
        this.productService = productService;
        this.productValidator = productValidator;
        this.productDtoMapper = productDtoMapper;
    }


    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products.stream().map(productDtoMapper::toDto).toList());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long id) {
        productValidator.validateProductId(id);
        Product product = productService.getProductById(id);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }
        return ResponseEntity.ok(productDtoMapper.toDto(product));
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product request = productDtoMapper.toEntity(productDto);
        Product response = productService.createProduct(request);
        return ResponseEntity.ok(productDtoMapper.toDto(response));
    }

}
