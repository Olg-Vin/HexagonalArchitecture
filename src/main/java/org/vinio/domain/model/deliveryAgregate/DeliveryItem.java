package org.vinio.domain.model.deliveryAgregate;

public class DeliveryItem {
    private Product product;
    private int productQuantity;

    public DeliveryItem(Product product, int productQuantity) {
        this.product = product;
        this.productQuantity = productQuantity;
    }
}
