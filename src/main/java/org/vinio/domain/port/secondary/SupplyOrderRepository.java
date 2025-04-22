package org.vinio.domain.port.secondary;

import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;

import java.util.List;
import java.util.Optional;

public interface SupplyOrderRepository {
    Optional<SupplyOrder> findById(int id);

    void save(SupplyOrder supplyOrder);

    List<SupplyOrder> findAll();
}
