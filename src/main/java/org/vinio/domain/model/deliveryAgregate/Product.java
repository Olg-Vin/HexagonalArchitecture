package org.vinio.domain.model.deliveryAgregate;

import java.time.LocalDateTime;

public class Product {
    private final String id;
    private final String name;
    private final double price;
    private final LocalDateTime expirationDate;

    public Product(String id, String name, double price, LocalDateTime expirationDate) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }
}
