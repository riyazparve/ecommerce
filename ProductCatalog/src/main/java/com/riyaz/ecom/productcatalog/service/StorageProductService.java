package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import com.riyaz.ecom.productcatalog.repository.CategoryRepository;
import com.riyaz.ecom.productcatalog.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.print.attribute.Attribute;
import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService{
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        if (id <= 0) {
//            return ResponseEntity.badRequest().build();
            throw new IllegalArgumentException("productId should be greater than 0");
        }
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        
        if (category != null && category.getName() != null) {
            // Find or create category by name
            Category resolvedCategory = categoryRepository.findByName(category.getName())
                    .orElseGet(() -> categoryRepository.save(category));
            product.setCategory(resolvedCategory);
        }
        
        return productRepository.save(product);
    }
}
