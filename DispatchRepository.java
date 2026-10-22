package org.example.subsystemC;

import java.io.FileWriter;
import java.io.IOException;

public class DispatchRepository {
    public void saveState(DeliveryDispatch dispatch) {
        try (FileWriter writer = new FileWriter("dispatch_state.txt")) {
            writer.write(dispatch.getDispatchId() + "," + dispatch.getTotalAmount() + "," + dispatch.getStatus());
            System.out.println("[Subsystem C] State saved to disk.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}