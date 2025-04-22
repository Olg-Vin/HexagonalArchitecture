package org.vinio;

import org.vinio.adapter.primary.console.ConsoleOrderManager;
import org.vinio.adapter.secondary.persistence.InMemoryDeliveryRepository;
import org.vinio.adapter.secondary.persistence.InMemoryOrderRepository;
import org.vinio.adapter.secondary.persistence.InMemoryProductRepository;
import org.vinio.domain.port.primary.DeliveryUseCase;
import org.vinio.domain.port.primary.SupplyOrderUseCase;
import org.vinio.domain.port.secondary.DeliveryRepository;
import org.vinio.domain.port.secondary.ProductRepository;
import org.vinio.domain.port.secondary.SupplyOrderRepository;
import org.vinio.domain.service.DeliveryService;
import org.vinio.domain.service.SupplyOrderService;

public class Main {
    public static void main(String[] args) {
        // Инициализация исходящих адаптеров
        SupplyOrderRepository orderRepository = new InMemoryOrderRepository();
        ProductRepository productRepository = new InMemoryProductRepository();
        DeliveryRepository deliveryRepository = new InMemoryDeliveryRepository();

        // Инициализация сервиса предметной области
        SupplyOrderUseCase supplyOrderUseCase = new SupplyOrderService(
                productRepository,
                deliveryRepository,
                orderRepository
        );

        DeliveryUseCase deliveryUseCase = new DeliveryService(
                deliveryRepository,
                orderRepository
        );

        // Инициализация входящего адаптера
        ConsoleOrderManager consoleUI = new ConsoleOrderManager(supplyOrderUseCase, deliveryUseCase);

        // Запуск приложения
        System.out.println("Запуск системы управления заказами");
        consoleUI.start();
    }
}