package com.bosorio.product_service.infrastructure.adapter.in.controller;

import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDto> getProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/{id}/no-cache")
    public ProductDto getProductByIdNoCache(@PathVariable Long id) {
        return productService.getProductByIdForUpdate(id);
    }

}
