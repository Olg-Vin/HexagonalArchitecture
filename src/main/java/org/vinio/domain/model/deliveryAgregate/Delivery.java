package org.vinio.domain.model.deliveryAgregate;

import java.time.LocalDateTime;

/**
 * Поставка
 * Отправить поставку, проверить качество продуктов
 * */
public class Delivery {
    private static int staticId = 0;
    private final int id;
    private final int supplyOrderId;
    private DeliveryItem deliveryItem;
    private LocalDateTime createdAt = LocalDateTime.now();
    private DeliveryStatus deliveryStatus = DeliveryStatus.SENT;

    public Delivery(int supplyOrderId) {
        this.id = staticId++;
        this.supplyOrderId = supplyOrderId;
    }

    public Delivery setDeliveryItem(Product product, int productQuantity) {
        this.deliveryItem = new DeliveryItem(product, productQuantity);
        return this;
    }

    public void completeDelivery() {
        this.deliveryStatus = DeliveryStatus.DELIVERED;
        // возможно событие DeliveryCompletedEvent
    }

    public int getSupplyOrderId() {
        return supplyOrderId;
    }
}

