package org.example.facade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class KitchenAdminFacade {

    // Административный метод для проверки всех сохраненных состояний на диске
    public void printAllPersistedStates() {
        System.out.println("\n[Admin Facade] --- Inspection of Persisted States ---");
        printFile("intake_state.txt", "Subsystem A (Intake)");
        printFile("cooking_state.txt", "Subsystem B (Cooking)");
        printFile("dispatch_state.txt", "Subsystem C (Dispatch)");
    }

    private void printFile(String fileName, String label) {
        try {
            Path path = Path.of(fileName);
            if (Files.exists(path)) {
                String content = Files.readString(path);
                System.out.println(label + " -> " + content);
            } else {
                System.out.println(label + " -> No state found (File doesn't exist).");
            }
        } catch (IOException e) {
            System.out.println(label + " -> Error reading state.");
        }
    }
}