package org.example.subsystemA;

public class InventoryChecker {
    public boolean reserveIngredients(String dishName) {
        // Простая бизнес-логика: если блюдо не пустое, резервируем
        if (dishName == null || dishName.isEmpty()) {
            return false;
        }
        System.out.println("[Subsystem A] Ingredients reserved for: " + dishName);
        return true;
    }

    public void releaseIngredients(String dishName) {
        System.out.println("[Subsystem A - Rollback] Released ingredients for: " + dishName);
    }
}