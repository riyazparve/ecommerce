package com.riyaz.ecom.productcatalog.services;

import com.riyaz.ecom.productcatalog.models.Category;
import com.riyaz.ecom.productcatalog.models.Product;
import com.riyaz.ecom.productcatalog.repositories.CategoryRepository;
import com.riyaz.ecom.productcatalog.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("storageProductService")
// matchIfMissing = true configures this as the fallback option if no property is defined
@ConditionalOnProperty(name = "product.service.selected-service", havingValue = "storageProductService", matchIfMissing = true)
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

    @Override
    public Product replaceProduct(Long id, Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
