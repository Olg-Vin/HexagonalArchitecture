package org.vinio.domain.model.deliveryAgregate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Поставка
 * Delivery() - отправить поставку со стороны поставщика
 * CheckProducts() - проверить качество продукта
 * */
public class Delivery {
    private static int staticId = 0;
    private final int id;
    private final int supplyOrderId;
    private List<DeliveryItem> deliveryItem = new ArrayList<>();
    private final LocalDateTime createdAt = LocalDateTime.now();

    public Delivery(int supplyOrderId) {
        this.id = staticId++;
        this.supplyOrderId = supplyOrderId;
    }

    public void setDeliveryItem(Product product, int quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException("Продукт не может быть null, количество должно быть > 0.");
        }

        this.deliveryItem.add(new DeliveryItem(product, quantity));
    }

    public boolean checkProducts(){
        for (DeliveryItem deliveryItem : this.deliveryItem) {
            if (!checkExpirationDate(deliveryItem.getProduct().getExpirationDate())) {
                return false;
            }
        }
        return true;
    }

    private boolean checkExpirationDate(LocalDateTime expirationDate) {
        return expirationDate.isAfter(LocalDateTime.now());
    }

    public int getId() {
        return id;
    }

    public int getSupplyOrderId() {
        return supplyOrderId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<DeliveryItem> getDeliveryItem() {
        return deliveryItem;
    }
}
