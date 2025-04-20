package org.vinio.domain.port.secondary;

import org.vinio.domain.model.deliveryAgregate.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(String id);
    Optional<Product> findByName(String id);
    List<Product> findAll();
}
