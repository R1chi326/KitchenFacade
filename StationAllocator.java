package org.example.subsystemB;

public class StationAllocator {
    public String allocateStation(String dishName) {
        System.out.println("[Subsystem B] Station allocated for dish: " + dishName);
        return "Grill-Station";
    }
}