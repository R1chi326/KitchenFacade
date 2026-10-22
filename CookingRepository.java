package org.example.subsystemB;

import java.io.FileWriter;
import java.io.IOException;

public class CookingRepository {
    public void saveState(CookingTask task) {
        try (FileWriter writer = new FileWriter("cooking_state.txt")) {
            writer.write(task.getTaskId() + "," + task.getStationName() + "," + task.getStatus());
            System.out.println("[Subsystem B] State saved to disk.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}