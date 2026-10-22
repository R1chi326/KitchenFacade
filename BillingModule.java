package org.example.subsystemC;

public class BillingModule {
    public double calculateBill(String dishName) {
        System.out.println("[Subsystem C] Calculating bill for: " + dishName);
        return 15.00; // Фиксированная цена для примера
    }
}