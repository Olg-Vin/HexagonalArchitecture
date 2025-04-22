package org.vinio.domain.port.primary;

import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;

import java.util.List;
import java.util.Map;

/**
 * Операции подсистемы
 * 1. Создание заказа поставщику
 * 2. Отправка заказа поставщику (по идее тут меняется статус, что заказ отправлен)
 * 3. Подтверждение получения от поставщика
 *      - создание агрегата доставки при подтверждении отправки заказа
 * 4. Отслеживание статуса заказа
 * */
public interface SupplyOrderUseCase {
    List<SupplyOrder> showAllOrders();
    SupplyOrder createSupplyOrder(String productName, int quantity);
    SupplyOrder createSupplyOrderMap(Map<String, Integer> products);
    void sendSupplyOrder(int orderId);
    void confirmSupplyAndCreateDelivery(int orderId);
    String getOrderStatus(int orderId);
}
