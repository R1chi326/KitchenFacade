package org.example.facade;

import org.example.subsystemA.*;
import org.example.subsystemB.*;
import org.example.subsystemC.*;

public class OrderProcessingFacade {
    private InventoryChecker inventoryChecker = new InventoryChecker();
    private IntakeRepository intakeRepo = new IntakeRepository();

    private StationAllocator stationAllocator = new StationAllocator();
    private QualityChecker qualityChecker = new QualityChecker();
    private CookingRepository cookingRepo = new CookingRepository();

    private BillingModule billingModule = new BillingModule();
    private DispatchManager dispatchManager = new DispatchManager();
    private DispatchRepository dispatchRepo = new DispatchRepository();

    // Главный метод цепочки A -> B -> C с поддержкой компенсации
    public boolean processOrder(String orderId, String dishName, boolean simulateBFail, boolean simulateCFail) {
        System.out.println("\n--- Starting Order Chain for: " + dishName + " ---");

        // Шаг А: Резервация ингредиентов
        boolean aSuccess = inventoryChecker.reserveIngredients(dishName);
        OrderIntake intake = new OrderIntake(orderId, dishName, aSuccess ? "INGREDIENTS_RESERVED" : "CREATED");
        intakeRepo.saveState(intake);

        if (!aSuccess) {
            System.out.println("[Facade] Step A failed.");
            return false;
        }

        // Шаг Б: Готовка и проверка качества
        String station = stationAllocator.allocateStation(dishName);
        CookingTask task = new CookingTask(orderId, station, "COOKING_IN_PROGRESS");
        cookingRepo.saveState(task);

        if (simulateBFail) {
            System.out.println("[Facade] SIMULATED FAIL at Step B (Quality check rejected)!");
            // Компенсация глубины 1: откатываем A
            inventoryChecker.releaseIngredients(dishName);
            intake.setStatus("CANCELLED");
            intakeRepo.saveState(intake);
            task.setStatus("FAILED_QUALITY");
            cookingRepo.saveState(task);
            return false;
        }

        task.setStatus("READY_FOR_PICKUP");
        cookingRepo.saveState(task);

        // Шаг С: Финализация и доставка
        double amount = billingModule.calculateBill(dishName);
        DeliveryDispatch dispatch = new DeliveryDispatch(orderId, amount, "PREPARING_BILL");
        dispatchRepo.saveState(dispatch);

        if (simulateCFail) {
            System.out.println("[Facade] SIMULATED FAIL at Step C (Delivery failed)!");
            // Компенсация глубины 2: откатываем B, затем A
            System.out.println("[Facade - Rollback] Disposing cooked meal in Subsystem B.");
            task.setStatus("DISPOSED");
            cookingRepo.saveState(task);

            inventoryChecker.releaseIngredients(dishName);
            intake.setStatus("CANCELLED");
            intakeRepo.saveState(intake);

            dispatch.setStatus("FAILED_DELIVERY");
            dispatchRepo.saveState(dispatch);
            return false;
        }

        dispatchManager.dispatchOrder(orderId);
        dispatch.setStatus("DISPATCHED");
        dispatchRepo.saveState(dispatch);

        System.out.println("--- Order Chain Completed Successfully! ---");
        return true;
    }
}