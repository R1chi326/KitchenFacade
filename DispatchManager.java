package org.example.subsystemC;

public class DispatchManager {
    public boolean dispatchOrder(String dispatchId) {
        // Здесь имитируем отправку (может упасть для сценария глубины 2)
        System.out.println("[Subsystem C] Order dispatched successfully: " + dispatchId);
        return true;
    }
}