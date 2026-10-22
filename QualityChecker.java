package org.example.subsystemB;

public class QualityChecker {
    public boolean checkQuality(CookingTask task) {
        // Здесь имитируем проверку качества (в реальном сценарии может упасть для теста компенсации)
        System.out.println("[Subsystem B] Quality check passed for task: " + task.getTaskId());
        return true;
    }
}