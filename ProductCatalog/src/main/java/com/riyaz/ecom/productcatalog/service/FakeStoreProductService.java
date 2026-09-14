package com.riyaz.ecom.productcatalog.service;

import com.riyaz.ecom.productcatalog.dto.FakeStoreProductDto;
import com.riyaz.ecom.productcatalog.exception.ProductNotFoundException;
import com.riyaz.ecom.productcatalog.mapper.FakeStoreProductDtoMapper;
import com.riyaz.ecom.productcatalog.model.Product;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("fakeStoreProductService")
@ConditionalOnProperty(name = "product.service.selected-service", havingValue = "fakeStoreProductService")
public class FakeStoreProductService implements IProductService {

    private final FakeStoreClient fakeStoreClient;
    private final FakeStoreProductDtoMapper fakeStoreProductDtoMapper;

    public FakeStoreProductService(
            FakeStoreClient fakeStoreClient,
            FakeStoreProductDtoMapper fakeStoreProductDtoMapper) {
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
//        product.setId(2L); TODO check impact of this line
        FakeStoreProductDto productDto = fakeStoreProductDtoMapper.toDto(product);
        FakeStoreProductDto createdProductDto = fakeStoreClient.createFakeStoreProduct(productDto);
        return fakeStoreProductDtoMapper.toEntity(createdProductDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto productDto = fakeStoreProductDtoMapper.toDto(product);
        FakeStoreProductDto replacedProductDto = fakeStoreClient.replaceFakeStoreProduct(id, productDto);
        return fakeStoreProductDtoMapper.toEntity(replacedProductDto);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        FakeStoreProductDto existingProductDto = fakeStoreClient.getFakeStoreProductsById(id);
        if (existingProductDto == null) {
            throw new ProductNotFoundException("Product not found");
        }

        FakeStoreProductDto productDto = fakeStoreProductDtoMapper.toDto(product);
        FakeStoreProductDto updatedProductDto = fakeStoreClient.updateFakeStoreProduct(id, productDto);
        return fakeStoreProductDtoMapper.toEntity(updatedProductDto);
    }

    @Override
    public void deleteProduct(Long id) {
        fakeStoreClient.deleteFakeStoreProduct(id);
    }

}
