package com.bosorio.product_service.infrastructure.adapter.in.controller;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDto productDto) {
        ProductDto data = productService.createProduct(productDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product created successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody CreateProductDto productDto) {
        ProductDto data = productService.updateProduct(id, productDto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product updated successfully");
        response.put("data", data);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product deleted successfully");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
