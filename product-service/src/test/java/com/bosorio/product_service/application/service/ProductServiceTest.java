package com.bosorio.product_service.application.service;

import com.bosorio.product_service.application.dto.CreateProductDto;
import com.bosorio.product_service.application.dto.ProductDto;
import com.bosorio.product_service.application.exception.NotFoundException;
import com.bosorio.product_service.domain.model.Product;
import com.bosorio.product_service.domain.port.ProductPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductPersistencePort productPersistencePort;

    @InjectMocks
    private ProductService productService;

    private CreateProductDto createProductDto;
    private Product product;
    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createProductDto = new CreateProductDto();
        createProductDto.setName("Test Product");
        createProductDto.setDescription("Test Description");
        createProductDto.setPrice(BigDecimal.valueOf(100.0));
        createProductDto.setStock(10);

        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setDescription("Test Description");
        product.setPrice(BigDecimal.valueOf(100.0));
        product.setStock(10);

        productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(BigDecimal.valueOf(100.0));
        productDto.setStock(10);
    }

    @Test
    void createProductShouldReturnProductDto() {
        when(productPersistencePort.save(any(Product.class))).thenReturn(product);

        ProductDto result = productService.createProduct(createProductDto);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.getId());
        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice(), result.getPrice());
        assertEquals(productDto.getStock(), result.getStock());

        verify(productPersistencePort, times(1)).save(any(Product.class));
    }

    @Test
    void getAllProductsShouldReturnListOfProductDto() {
        when(productPersistencePort.findAll()).thenReturn(Collections.singletonList(product));

        List<ProductDto> result = productService.getAllProducts();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDto.getId(), result.get(0).getId());
        assertEquals(productDto.getName(), result.get(0).getName());
        assertEquals(productDto.getDescription(), result.get(0).getDescription());
        assertEquals(productDto.getPrice(), result.get(0).getPrice());
        assertEquals(productDto.getStock(), result.get(0).getStock());

        verify(productPersistencePort, times(1)).findAll();
    }

    @Test
    void getProductByIdShouldReturnProductDto() {
        when(productPersistencePort.findById(anyLong())).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.getId());
        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice(), result.getPrice());
        assertEquals(productDto.getStock(), result.getStock());

        verify(productPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void getProductByIdShouldThrowNotFoundException() {
        when(productPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.getProductById(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productPersistencePort, times(1)).findById(anyLong());
    }

    @Test
    void getProductByIdForUpdateShouldReturnProductDto() {
        when(productPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.of(product));

        ProductDto result = productService.getProductByIdForUpdate(1L);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.getId());
        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice(), result.getPrice());
        assertEquals(productDto.getStock(), result.getStock());

        verify(productPersistencePort, times(1)).findByIdForUpdate(anyLong());
    }

    @Test
    void getProductByIdForUpdateShouldThrowNotFoundException() {
        when(productPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.getProductByIdForUpdate(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productPersistencePort, times(1)).findByIdForUpdate(anyLong());
    }

    @Test
    void updateProductShouldReturnUpdatedProductDto() {
        when(productPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.of(product));
        when(productPersistencePort.update(any(Product.class))).thenReturn(product);

        ProductDto result = productService.updateProduct(1L, createProductDto);

        assertNotNull(result);
        assertEquals(productDto.getId(), result.getId());
        assertEquals(productDto.getName(), result.getName());
        assertEquals(productDto.getDescription(), result.getDescription());
        assertEquals(productDto.getPrice(), result.getPrice());
        assertEquals(productDto.getStock(), result.getStock());

        verify(productPersistencePort, times(1)).findByIdForUpdate(anyLong());
        verify(productPersistencePort, times(1)).update(any(Product.class));
    }

    @Test
    void updateProductShouldThrowNotFoundException() {
        when(productPersistencePort.findByIdForUpdate(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.updateProduct(1L, createProductDto);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productPersistencePort, times(1)).findByIdForUpdate(anyLong());
        verify(productPersistencePort, never()).update(any(Product.class));
    }

    @Test
    void deleteProductShouldDeleteProduct() {
        when(productPersistencePort.findById(anyLong())).thenReturn(Optional.of(product));
        doNothing().when(productPersistencePort).delete(any(Product.class));

        productService.deleteProduct(1L);

        verify(productPersistencePort, times(1)).findById(anyLong());
        verify(productPersistencePort, times(1)).delete(any(Product.class));
    }

    @Test
    void deleteProductShouldThrowNotFoundException() {
        when(productPersistencePort.findById(anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productPersistencePort, times(1)).findById(anyLong());
        verify(productPersistencePort, never()).delete(any(Product.class));
    }
}