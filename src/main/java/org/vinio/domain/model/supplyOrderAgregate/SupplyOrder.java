package org.vinio.domain.model.supplyOrderAgregate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * SupplyOrder - Заказ поставщику
 * Команды:
 * SupplyOrder() - создать заказ поставщику
 *   - инициирует событие SupplyOrderCreated - заказ поставщику создан
 * confirmOrder() - подтвердить заказ
 *   - инициирует событие SupplyOrderConfirmed - заказ подтверждён
 * sendOrder() - отправить заказ поставщику
 *   - инициирует событие SupplyOrderSent - заказ отправлен поставщику
 * */
public class SupplyOrder {
    private static int staticId = 0;
    private final int id;
    private String productName;
    private int productQuantity;
    private final Map<String, Integer> products = new HashMap<>();
    private OrderStatus orderStatus = OrderStatus.CREATED;
    private final LocalDateTime createDate = LocalDateTime.now();

    public SupplyOrder(String productName, int productQuantity) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя продукта не может быть пустым.");
        }
        if (productQuantity <= 0) {
            throw new IllegalArgumentException("Количество продукта должно быть больше 0.");
        }

        this.id = staticId++;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    public SupplyOrder(Map<String, Integer> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Список продуктов не может быть пустым.");
        }

        for (Map.Entry<String, Integer> entry : products.entrySet()) {
            if (entry.getKey() == null || entry.getKey().trim().isEmpty()) {
                throw new IllegalArgumentException("Имя продукта не может быть пустым.");
            }
            if (entry.getValue() <= 0) {
                throw new IllegalArgumentException("Количество продукта должно быть больше 0.");
            }
            this.products.put(entry.getKey(), entry.getValue());
        }
        this.id = staticId++;
    }

    public SupplyOrder confirmOrder() {
        if (orderStatus != OrderStatus.SENT) {
            throw new IllegalStateException("Поставщик пока не может подтвердить заказ");
        }
        this.orderStatus = OrderStatus.CONFIRMED;
        return this;
    }

    public SupplyOrder sentOrder() {
        if (orderStatus != OrderStatus.CREATED) {
            throw new IllegalStateException("Нельзя отправить поставщику несуществующий заказ");
        }
        this.orderStatus = OrderStatus.SENT;
        return this;
    }

    public SupplyOrder sentDelivery() {
        if (orderStatus != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Поставщик не может отправить неподтверждённый заказ");
        }
        this.orderStatus = OrderStatus.DELIVERY_SENT;
        return this;
    }

    public SupplyOrder arrivedOrder() {
        if (orderStatus != OrderStatus.DELIVERY_SENT) {
            throw new IllegalStateException("Прибыть может только отправленный заказ");
        }
        this.orderStatus = OrderStatus.DELIVERY_ARRIVED;
        return this;
    }

    public SupplyOrder rejectOrder() {
        if (orderStatus != OrderStatus.DELIVERY_ARRIVED) {
            throw new IllegalStateException("Нельзя отклонить не прибывший заказ");
        }
        this.orderStatus = OrderStatus.DELIVERY_REJECTED;
        return this;
    }

    public SupplyOrder completeOrder() {
        if (orderStatus != OrderStatus.DELIVERY_ARRIVED) {
            throw new IllegalStateException("Нельзя принять не прибывший заказ");
        }
        this.orderStatus = OrderStatus.DELIVERY_COMPLETED;
        return this;
    }

    public SupplyOrder returnOrder() {
        if (orderStatus != OrderStatus.DELIVERY_REJECTED) {
            throw new IllegalStateException("Невозможно вернуть эту доставку");
        }
        this.orderStatus = OrderStatus.RETURNED;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Map<String, Integer> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "SupplyOrder{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productQuantity=" + productQuantity +
                ", products=" + products +
                ", orderStatus=" + orderStatus +
                ", createDate=" + createDate +
                '}';
    }
}