package org.vinio.domain.model;


import org.vinio.domain.model.deliveryAgregate.Delivery;
import org.vinio.domain.port.secondary.ProductRepository;

// Когда происходит событие SupplyOrderSent, создается новый агрегат Delivery
// Агрегат Delivery содержит ссылку на идентификатор соответствующего SupplyOrder
// Состояние заказа в SupplyOrder обновляется в зависимости от событий агрегата Delivery
public class DomainEvent {
    ProductRepository productRepository;
    public Delivery supplyOrderSent(int supplyOrderId, String productName, int productQuantity){
        return new Delivery(supplyOrderId).setDeliveryItem(
                        productRepository.findByName(productName).orElseThrow(),
                        productQuantity);
    }
}
