package com.bosorio.product_service.domain.port;

import com.bosorio.product_service.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductPersistencePort {

    Product save(Product product);
    Optional<Product> findById(Long id);
    Optional<Product> findByIdForUpdate(Long id);
    List<Product> findAll();
    Product update(Product product);
    void delete(Product product);

}
