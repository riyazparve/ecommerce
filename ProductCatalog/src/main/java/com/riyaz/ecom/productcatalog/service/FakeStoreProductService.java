package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.mapper.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements IProductService {

    private final RestTemplateBuilder restTemplateBuilder;
    private final FakeStoreClient fakeStoreClient;
    private final FakeStoreProductDtoMapper fakeStoreProductDtoMapper;

    public FakeStoreProductService(RestTemplateBuilder restTemplateBuilder, 
                                   FakeStoreClient fakeStoreClient,
                                   FakeStoreProductDtoMapper fakeStoreProductDtoMapper) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreClient = fakeStoreClient;
        this.fakeStoreProductDtoMapper = fakeStoreProductDtoMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtoList = fakeStoreClient.getAllFakeStoreProducts();
        return fakeStoreProductDtoList.stream().map(fakeStoreProductDtoMapper::toEntity).toList();
    }

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto productDto = fakeStoreClient.getFakeStoreProductsById(id);
        return fakeStoreProductDtoMapper.toEntity(productDto);
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(2L);
        return product;
    }

}
