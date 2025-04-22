package org.vinio.domain.service;

import org.vinio.domain.model.deliveryAgregate.Delivery;
import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;
import org.vinio.domain.port.primary.DeliveryUseCase;
import org.vinio.domain.port.secondary.DeliveryRepository;
import org.vinio.domain.port.secondary.SupplyOrderRepository;

public class DeliveryService implements DeliveryUseCase {
    DeliveryRepository deliveryRepository;
    SupplyOrderRepository supplyOrderRepository;

    public DeliveryService(DeliveryRepository deliveryRepository,
                           SupplyOrderRepository supplyOrderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.supplyOrderRepository = supplyOrderRepository;
    }

    @Override
    public void getDeliveryAndQualityControl(int deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow();
        SupplyOrder supplyOrder = supplyOrderRepository.findById(delivery.getSupplyOrderId()).orElseThrow();
        supplyOrderRepository.save(supplyOrder.arrivedOrder());

        if (!delivery.checkProducts()) {
            System.out.println("Получен некачественный продукт, поставка не принята");
            System.out.println(delivery.getDeliveryItem());
            supplyOrderRepository.save(supplyOrder.rejectOrder());
            handleDeliveryReturn(deliveryId);
            return;
        }

        System.out.println("С качеством всё в порядке, поставка принята");
        supplyOrderRepository.save(supplyOrder.completeOrder());
    }

    @Override
    public void handleDeliveryReturn(int supplyOrderId) {
        SupplyOrder supplyOrder = supplyOrderRepository.findById(supplyOrderId).orElseThrow();
        supplyOrderRepository.save(supplyOrder.returnOrder());
        System.out.println("Заказ возвращён");
    }
}
