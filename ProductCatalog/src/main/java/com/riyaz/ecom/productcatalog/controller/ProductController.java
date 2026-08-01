package com.riyaz.ecom.productcatalog.controller;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
//    @Qualifier("fakeStoreProductService")
    private IProductService productService;

//    Wiring beans using Constructor Inject this is replaced by @Autowired
//    public ProductController(IProductService productService) {
//        this.productService = productService;
//    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> responseAllProductList = productService.getAllProducts();
        return ResponseEntity.ok(responseAllProductList.stream().map(ProductDtoMapper::toDto).toList());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long id) {
        if (id <= 0) {
//            return ResponseEntity.badRequest().build();
            throw new IllegalArgumentException("productId should be greater than 0");
        }
        Product product = productService.getProductById(id);
        if (product == null) {
//            return ResponseEntity.notFound().build();
            throw new IllegalArgumentException("No product found with productId = " + id);
        }
        ProductDto responseProductDto = ProductDtoMapper.toDto(product);
        return ResponseEntity.ok(responseProductDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        Product request = ProductDtoMapper.toEntity(productDto);
        Product response = productService.createProduct(request);
        return ResponseEntity.ok(ProductDtoMapper.toDto(response));
    }

}
