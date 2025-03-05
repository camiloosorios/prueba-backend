package com.bosorio.product_service.application.mapper;

import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.domain.model.Product;

public class ProductMapper {

    public static Product fromProductDtoToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getDescription(),
                productDto.getPrice(),
                productDto.getStock()
        );
    }

    public static ProductDto fromProductToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }

}
