package org.vinio.domain.port.primary;

/**
 * 5. Приёмка поставки и контроль качества
 * 6. Обработка возвратов в случае проблем с качеством
 * */
public interface DeliveryUseCase {
    void getDeliveryAndQualityControl(int deliveryId);
    void handleDeliveryReturn(int supplyOrderId);
}
