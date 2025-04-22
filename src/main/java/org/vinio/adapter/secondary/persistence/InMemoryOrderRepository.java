package org.vinio.adapter.secondary.persistence;

import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;
import org.vinio.domain.port.secondary.SupplyOrderRepository;

import java.util.*;

public class InMemoryOrderRepository implements SupplyOrderRepository {
    private final Map<Integer, SupplyOrder> orders = new HashMap<>();
    @Override
    public Optional<SupplyOrder> findById(int id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public void save(SupplyOrder supplyOrder) {
        orders.put(supplyOrder.getId(), supplyOrder);
    }

    @Override
    public List<SupplyOrder> findAll() {
        return new ArrayList<>(orders.values());
    }
}
