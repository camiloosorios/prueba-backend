package com.bosorio.product_service.application.usecases;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;

import java.util.List;

public interface ProductUseCases {

    ProductDto createProduct(CreateProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long id);
    ProductDto getProductByIdForUpdate(Long id);
    ProductDto updateProduct(Long id, CreateProductDto productDto);
    void deleteProduct(Long id);

}
