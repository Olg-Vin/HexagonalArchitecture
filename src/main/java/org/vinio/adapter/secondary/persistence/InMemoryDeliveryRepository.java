package org.vinio.adapter.secondary.persistence;

import org.vinio.domain.model.deliveryAgregate.Delivery;
import org.vinio.domain.port.secondary.DeliveryRepository;

import java.util.*;

public class InMemoryDeliveryRepository implements DeliveryRepository {
    private final Map<Integer, Delivery> deliveries = new HashMap<>();

    @Override
    public Optional<Delivery> findById(int id) {
        return Optional.ofNullable(deliveries.get(id));
    }

    @Override
    public void save(Delivery delivery) {
        deliveries.put(delivery.getId(), delivery);
    }

    @Override
    public List<Delivery> findAll() {
        return new ArrayList<>(deliveries.values());
    }

}
