package org.example.subsystemA;

public class OrderIntake {
    private String orderId;
    private String dishName;
    private String status; // CREATED, INGREDIENTS_RESERVED, CANCELLED

    public OrderIntake(String orderId, String dishName, String status) {
        this.orderId = orderId;
        this.dishName = dishName;
        this.status = status;
    }

    public String getOrderId() { return orderId; }
    public String getDishName() { return dishName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}