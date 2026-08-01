package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.CategoryDto;
import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.dto.ProductDto;
import com.riyaz.ecom.productcatalog.mapper.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.mapper.ProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Category;
import com.riyaz.ecom.productcatalog.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements IProductService {

    @Autowired
    private FakeStoreClient fakeStoreClient;

    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtoList = fakeStoreClient.getAllFakeStoreProducts();
        return fakeStoreProductDtoList.stream().map(FakeStoreProductDtoMapper::toEntity).toList();
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto productDto = fakeStoreClient.getFakeStoreProductsById(id);
        return FakeStoreProductDtoMapper.toEntity(productDto);
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(2L);
        return product;
    }

}
