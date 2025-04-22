package org.vinio.adapter.primary.console;

import org.vinio.domain.model.supplyOrderAgregate.OrderStatus;
import org.vinio.domain.model.supplyOrderAgregate.SupplyOrder;
import org.vinio.domain.port.primary.DeliveryUseCase;
import org.vinio.domain.port.primary.SupplyOrderUseCase;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleOrderManager {
    private final SupplyOrderUseCase supplyOrderService;
    private final DeliveryUseCase deliveryService;
    private final Scanner scanner;
    private String currentOrderId;
    private String currentUser;

    public ConsoleOrderManager(SupplyOrderUseCase supplyOrderService,
                               DeliveryUseCase deliveryService) {
        this.supplyOrderService = supplyOrderService;
        this.deliveryService = deliveryService;
        this.scanner = new Scanner(System.in);
        this.currentOrderId = null;
        this.currentUser = null;
    }

    public void start() {
        int choice;
        do {
//            if (currentUser == null) {
//                System.out.println("Введите пользователя (Supplier/User):");
//                currentUser = scanner.nextLine();
//            }
            currentUser = "User";
            showMainMenu();
            choice = readIntInput();
            scanner.nextLine(); // очистка буфера
            handleMainMenuChoice(choice);
        } while (choice != 0);
    }

    private void showMainMenu() {
        System.out.println("\n===== Система управления заказами =====");
        if (currentOrderId != null) {
            System.out.println("Текущий заказ: #" + currentOrderId);
        }

        if (currentUser.equals("User")) {
            System.out.println("1. Просмотр всех заказов");
            System.out.println("2. Создание заказа поставщику");
            System.out.println("3. Отправка заказа поставщику");
            System.out.println("4. Получение подтверждения от поставщика");
            System.out.println("5. Отслеживание статуса заказа");
            System.out.println("6. Приёмка поставки и контроль качества");
            System.out.println("7. Возврат поставки");
        }

        if (currentUser.equals("Supplier")) {
            System.out.println("1. Просмотр всех заказов");
            System.out.println("2. Подтвердить заказ и отправить поставку");
        }

//        System.out.println("-1. Разлогиниться");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void handleMainMenuChoice(int choice) {
        if ("User".equals(currentUser)) {
            switch (choice) {
                case 1 -> viewAllOrders();
                case 2 -> createSupplyOrder();
                case 3 -> sendSupplyOrder();
                case 4 -> confirmSupplyFromSupplier();
                case 5 -> trackOrderStatus();
                case 6 -> qualityControl();
                case 7 -> returnSupply();
                case 0 -> System.out.println("Выход из системы...");
                case -1 -> this.currentUser = null;
                default -> System.out.println("Неверный выбор.");
            }
        } else if ("Supplier".equals(currentUser)) {
            switch (choice) {
                case 1 -> viewAllOrders();
                case 2 -> confirmAndSendDelivery();
                case 0 -> System.out.println("Выход из системы...");
                case -1 -> this.currentUser = null;
                default -> System.out.println("Неверный выбор.");
            }
        }
    }

    private void viewAllOrders() {
        System.out.println("Все заказы:");
        var orders = supplyOrderService.showAllOrders();
        if (orders.isEmpty()) {
            System.out.println("Нет заказов.");
        } else {
            orders.forEach(order -> System.out.println(order));
        }
    }

    private void createSupplyOrder() {
        Map<String, Integer> products = new HashMap<>();
        String exit;
        do {
            System.out.print("Введите название продукта: ");
            String productName = scanner.nextLine();
            System.out.print("Введите количество: ");
            int quantity = readIntInput();
            products.put(productName, quantity);
            scanner.nextLine();
            System.out.println("Есть ли ещё продукты? (да/нет)");
            exit = scanner.nextLine();
        }
        while (exit.equals("да"));


//        var order = supplyOrderService.createSupplyOrder(productName, quantity);
        SupplyOrder supplyOrder = supplyOrderService.createSupplyOrderMap(products);
        currentOrderId = String.valueOf(supplyOrder.getId());
        System.out.println("Создан заказ с ID: " + currentOrderId);
    }

    private void sendSupplyOrder() {
        if (currentOrderId == null) {
            System.out.println("Сначала создайте или выберите заказ.");
            return;
        }
        int orderId = Integer.parseInt(currentOrderId);
        supplyOrderService.sendSupplyOrder(orderId);
        System.out.println("Заказ #" + orderId + " отправлен поставщику.");
    }

    private void confirmSupplyFromSupplier() {
        if (currentOrderId == null) {
            System.out.println("Сначала создайте или выберите заказ.");
            return;
        }
        int orderId = Integer.parseInt(currentOrderId);
        supplyOrderService.confirmSupplyAndCreateDelivery(orderId);
        System.out.println("Заказ #" + orderId + " подтверждён поставщиком.");
    }

    private void trackOrderStatus() {
        if (currentOrderId == null) {
            System.out.println("Сначала создайте или выберите заказ.");
            return;
        }
        int orderId = Integer.parseInt(currentOrderId);
        String status = supplyOrderService.getOrderStatus(orderId);
        System.out.println("Статус заказа #" + orderId + ": " + status);
    }

    private void qualityControl() {
        if (currentOrderId == null) {
            System.out.println("Сначала создайте или выберите заказ.");
            return;
        }
        int orderId = Integer.parseInt(currentOrderId);
        if (!supplyOrderService.getOrderStatus(orderId).equals(OrderStatus.DELIVERY_SENT.toString())) {
            System.out.println("Поставка ещё не отправлена");
            return;
        }
//        System.out.print("Прошла ли поставка проверку качества? (да/нет): ");
//        String input = scanner.nextLine().trim().toLowerCase();
//        boolean passed = input.equals("да");

        deliveryService.getDeliveryAndQualityControl(orderId);
    }

    private void confirmAndSendDelivery() {
        if (currentOrderId == null) {
            System.out.println("Сначала выберите заказ.");
            return;
        }

        int orderId = Integer.parseInt(currentOrderId);
        try {
            supplyOrderService.confirmSupplyAndCreateDelivery(orderId);
            System.out.println("Заказ #" + orderId + " подтверждён и поставка отправлена.");
        } catch (Exception e) {
            System.out.println("Ошибка при подтверждении и отправке доставки: " + e.getMessage());
        }
    }


    private void returnSupply() {
        if (currentOrderId == null) {
            System.out.println("Сначала создайте или выберите заказ.");
            return;
        }
        int orderId = Integer.parseInt(currentOrderId);
        if (!supplyOrderService.getOrderStatus(orderId).equals(OrderStatus.DELIVERY_ARRIVED.toString())) {
            System.out.println("Поставка ещё не пришла");
            return;
        }
        deliveryService.handleDeliveryReturn(orderId);
    }


    private int readIntInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }

    private double readDoubleInput() {
        try {
            return scanner.nextDouble();
        } catch (Exception e) {
            return -1;
        }
    }

}
