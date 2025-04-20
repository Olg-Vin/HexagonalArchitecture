package org.vinio.domain.model.supplyOrderAgregate;

import org.vinio.domain.model.DomainEvent;

import java.time.LocalDateTime;

/**
 * Заказ поставщику
 * Позволяет создать заказ, подтвердить заказ, отправить заказ
 * */
public class SupplyOrder {
    private static int staticId = 0;
    private final int id;
    private final String productName;
    private final int productQuantity;
    private OrderStatus orderStatus = OrderStatus.CREATED;
    private final LocalDateTime createDate = LocalDateTime.now();

    DomainEvent domainEvent = new DomainEvent();

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

    public SupplyOrder confirmOrder() {
        if (orderStatus != OrderStatus.CREATED) {
            throw new IllegalStateException("Заказ можно подтвердить только из состояния CREATED.");
        }
        this.orderStatus = OrderStatus.CONFIRMED;
        return this;
    }

    public SupplyOrder sentOrder() {
        if (orderStatus != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Отправить можно только подтверждённый заказ.");
        }
        this.orderStatus = OrderStatus.SENT;
        domainEvent.supplyOrderSent(this.id, this.productName, this.productQuantity);
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
}