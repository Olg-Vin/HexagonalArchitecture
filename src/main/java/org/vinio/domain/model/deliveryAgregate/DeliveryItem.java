package org.vinio.domain.model.deliveryAgregate;

import org.vinio.domain.model.deliveryAgregate.Product;

public class DeliveryItem {
    private Product product;
    private int productQuantity;

    public DeliveryItem(Product product, int productQuantity) {
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "DeliveryItem{" +
                "product=" + product +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
