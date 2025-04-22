package org.vinio.adapter.secondary.persistence;

import org.vinio.domain.model.deliveryAgregate.Product;
import org.vinio.domain.port.secondary.ProductRepository;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();

    public InMemoryProductRepository() {
        addProduct(new Product("P1", "Сыр", 299.0, LocalDateTime.of(2025, 5, 15, 15, 15, 15)));
        addProduct(new Product("P2", "Хлеб", 59.0, LocalDateTime.now()));
        addProduct(new Product("P3", "Лаваш", 99.0, LocalDateTime.of(2025, 5, 15, 15, 15, 15)));
        addProduct(new Product("P4", "Курица", 399.0, LocalDateTime.of(2025, 5, 15, 15, 15, 15)));
        addProduct(new Product("P5", "Соус", 99.0, LocalDateTime.of(2025, 5, 15, 15, 15, 15)));
    }

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Optional<Product> findByName(String name) {
        return products.values().stream()
                .filter(product -> product.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

}
