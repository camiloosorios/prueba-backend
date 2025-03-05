package com.bosorio.product_service.infrastructure.adapter;

import com.bosorio.product_service.domain.model.Product;
import com.bosorio.product_service.domain.port.ProductPersistencePort;
import com.bosorio.product_service.infrastructure.adapter.out.mapper.ProductEntityMapper;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.entity.ProductEntity;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.bosorio.product_service.infrastructure.adapter.out.mapper.ProductEntityMapper.fromProductEntityToProduct;
import static com.bosorio.product_service.infrastructure.adapter.out.mapper.ProductEntityMapper.fromProductToProductEntity;

@Component
@RequiredArgsConstructor
public class ProductAdapter implements ProductPersistencePort {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product product) {
        ProductEntity productEntity =  productRepository.save(fromProductToProductEntity(product));

        return fromProductEntityToProduct(productEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductEntityMapper::fromProductEntityToProduct);
    }

    @Override
    public Optional<Product> findByIdForUpdate(Long id) {
        return productRepository.findByIdForUpdate(id)
                .map(ProductEntityMapper::fromProductEntityToProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductEntityMapper::fromProductEntityToProduct)
                .toList();
    }

    @Override
    public Product update(Product product) {
        ProductEntity productEntity =  productRepository.save(fromProductToProductEntity(product));

        return fromProductEntityToProduct(productEntity);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(fromProductToProductEntity(product));
    }
}
