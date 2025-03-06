package com.bosorio.product_service.application.service;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.exception.NotFoundException;
import com.bosorio.product_service.application.mapper.ProductMapper;
import com.bosorio.product_service.application.usecases.ProductUseCases;
import com.bosorio.product_service.domain.model.Product;
import com.bosorio.product_service.domain.port.ProductPersistencePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bosorio.product_service.application.mapper.ProductMapper.fromProductToProductDto;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCases {

    private final ProductPersistencePort productPersistencePort;

    @Override
    @CacheEvict(value = "productList", allEntries = true)
    public ProductDto createProduct(CreateProductDto productDto) {
        Product product = productPersistencePort.save(new Product(
                null,
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock())
        );
        return fromProductToProductDto(product);
    }

    @Override
    @Cacheable(value = "productList", key = "'all'")
    public List<ProductDto> getAllProducts() {
        return productPersistencePort.findAll()
                .stream()
                .map(ProductMapper::fromProductToProductDto)
                .toList();
    }

    @Override
    @Cacheable(value = "product", key = "#id")
    public ProductDto getProductById(Long id) {
        return productPersistencePort.findById(id)
                .map(ProductMapper::fromProductToProductDto)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    @Transactional
    public ProductDto getProductByIdForUpdate(Long id) {
        return productPersistencePort.findByIdForUpdate(id)
                .map(ProductMapper::fromProductToProductDto)
                .orElseThrow(() -> new NotFoundException("Product not found"));
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    @Transactional
    public ProductDto updateProduct(Long id, CreateProductDto productDto) {
        Product product = productPersistencePort.findByIdForUpdate(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        product = productPersistencePort.update(product);

        return fromProductToProductDto(product);
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productPersistencePort.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        productPersistencePort.delete(product);
    }
}
