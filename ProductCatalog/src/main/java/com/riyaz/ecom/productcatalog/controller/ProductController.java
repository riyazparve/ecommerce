package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import com.riyaz.ecom.productcatalog.validator.ProductValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // 2.2. Product Details: Detailed product pages with product images, descriptions, specifications, and other relevant information.
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

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> replaceProduct(@PathVariable("productId") Long id, @RequestBody ProductDto productDto) {
        Product request = productDtoMapper.toEntity(productDto);
        Product response = productService.replaceProduct(id, request);
        return ResponseEntity.ok(productDtoMapper.toDto(response));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") Long id, @RequestBody ProductDto productDto) {
        Product request = productDtoMapper.toEntity(productDto);
        Product response = productService.updateProduct(id, request);
        return ResponseEntity.ok(productDtoMapper.toDto(response));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("productId") Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //TODO 2.1. Browsing: Users should be able to browse products by different categories.
}
