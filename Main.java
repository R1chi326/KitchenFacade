package org.example;

import org.example.facade.OrderProcessingFacade;
import org.example.facade.KitchenAdminFacade;

public class Main {
    public static void main(String[] args) {
        OrderProcessingFacade facade = new OrderProcessingFacade();

        System.out.println("=== 1. Успешный сценарий (Happy Path) ===");
        facade.processOrder("ORD-001", "Steak", false, false);

        System.out.println("\n=== 2. Сценарий сбоя на Шаге B (Откат A) ===");
        facade.processOrder("ORD-002", "Soup", true, false);

        System.out.println("\n=== 3. Сценарий сбоя на Шаге C (Откат B и A) ===");
        facade.processOrder("ORD-003", "Salad", false, true);

        System.out.println("\n=== 4. Проверка административного фасада (Admin Inspection) ===");
        KitchenAdminFacade adminFacade = new KitchenAdminFacade();
        adminFacade.printAllPersistedStates();
    }
}