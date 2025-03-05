package com.bosorio.product_service.infrastructure.adapter.out.mapper;

import com.bosorio.product_service.domain.model.Product;
import com.bosorio.product_service.infrastructure.adapter.out.persistence.entity.ProductEntity;

public class ProductEntityMapper {

    public static ProductEntity fromProductToProductEntity(Product product) {
        return new ProductEntity(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

    public static Product fromProductEntityToProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getId(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getPrice(),
                productEntity.getStock()
        );
    }

}
