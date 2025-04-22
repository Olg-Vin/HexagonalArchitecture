package org.vinio.domain.port.secondary;

import org.vinio.domain.model.deliveryAgregate.Delivery;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository {
    Optional<Delivery> findById(int id);

    void save(Delivery supplyOrder);

    List<Delivery> findAll();
}
