package org.example.subsystemA;

import java.io.FileWriter;
import java.io.IOException;

public class IntakeRepository {
    public void saveState(OrderIntake order) {
        try (FileWriter writer = new FileWriter("intake_state.txt")) {
            writer.write(order.getOrderId() + "," + order.getDishName() + "," + order.getStatus());
            System.out.println("[Subsystem A] State saved to disk.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}