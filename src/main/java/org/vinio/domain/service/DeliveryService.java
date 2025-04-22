package org.vinio.domain.service;

import org.vinio.domain.model.deliveryAgregate.Delivery;
import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;
import org.vinio.domain.port.primary.DeliveryUseCase;
import org.vinio.domain.port.secondary.DeliveryRepository;
import org.vinio.domain.port.secondary.SupplyOrderRepository;

public class DeliveryService implements DeliveryUseCase {
    DeliveryRepository deliveryRepository;
    SupplyOrderRepository supplyOrderRepository;

    @Override
    public void getDeliveryAndQualityControl(int deliveryId, boolean passed) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        SupplyOrder supplyOrder = supplyOrderRepository.findById(delivery.getSupplyOrderId()).orElseThrow();
        supplyOrderRepository.save(supplyOrder.arrivedOrder());

        if (!delivery.checkProducts()) {
            System.out.println((char) 27 + "[33m" + "Получен некачественный продукт, поставка не принята");
            supplyOrderRepository.save(supplyOrder.rejectOrder());
            handleDeliveryReturn(deliveryId);
            return;
        }

        System.out.println((char) 27 + "[33m" + "С качеством всё в порядке, поставка принята");
        supplyOrderRepository.save(supplyOrder.completeOrder());
    }

    @Override
    public void handleDeliveryReturn(int supplyOrderId) {
        SupplyOrder supplyOrder = supplyOrderRepository.findById(supplyOrderId).orElseThrow();
        supplyOrderRepository.save(supplyOrder.returnOrder());
        System.out.println((char) 27 + "[33m" + "Заказ возвращён");
    }
}
