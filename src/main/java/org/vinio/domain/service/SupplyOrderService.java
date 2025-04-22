package org.vinio.domain.service;


import org.vinio.domain.model.deliveryAgregate.Delivery;
import org.vinio.domain.model.deliveryAgregate.Product;
import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;
import org.vinio.domain.port.primary.SupplyOrderUseCase;
import org.vinio.domain.port.secondary.DeliveryRepository;
import org.vinio.domain.port.secondary.ProductRepository;
import org.vinio.domain.port.secondary.SupplyOrderRepository;

import java.util.Map;

// Когда происходит событие SupplyOrderSent, создается новый агрегат Delivery
// Агрегат Delivery содержит ссылку на идентификатор соответствующего SupplyOrder
// Состояние заказа в SupplyOrder обновляется в зависимости от событий агрегата Delivery
// По идее такая механика реализуется при помощи команд и событий, которые я игнорирую))
public class SupplyOrderService implements SupplyOrderUseCase {
    ProductRepository productRepository;
    DeliveryRepository deliveryRepository;
    SupplyOrderRepository supplyOrderRepository;

    @Override
    public SupplyOrder createSupplyOrder(String productName, int quantity) {
        SupplyOrder supplyOrder = new SupplyOrder(productName, quantity);
        supplyOrderRepository.save(supplyOrder);
        return supplyOrder;
    }

    @Override
    public void sendSupplyOrder(int orderId) {
        SupplyOrder supplyOrder = supplyOrderRepository.findById(orderId).orElseThrow();
        supplyOrderSent(supplyOrder);
        supplyOrderRepository.save(supplyOrder.sentOrder());
    }

    @Override
    public void confirmSupplyAndCreateDelivery(int orderId) {
        SupplyOrder supplyOrder = supplyOrderRepository.findById(orderId).orElseThrow();
        supplyOrder.confirmOrder();
    }

    @Override
    public String getOrderStatus(int orderId) {
        SupplyOrder supplyOrder = supplyOrderRepository.findById(orderId).orElseThrow();
        return supplyOrder.getOrderStatus().toString();
    }

    private void supplyOrderSent(SupplyOrder supplyOrder) {
        Delivery delivery = new Delivery(supplyOrder.getId());
        for (Map.Entry<String, Integer> entry : supplyOrder.getProducts().entrySet()) {
            Product product = productRepository.findByName(entry.getKey()).orElseThrow();
            delivery.setDeliveryItem(product, entry.getValue());
        }
        deliveryRepository.save(delivery);
    }

}
