package org.example.subsystemC;

public class DeliveryDispatch {
    private String dispatchId;
    private double totalAmount;
    private String status; // PREPARING_BILL, DISPATCHED, FAILED_DELIVERY

    public DeliveryDispatch(String dispatchId, double totalAmount, String status) {
        this.dispatchId = dispatchId;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getDispatchId() { return dispatchId; }
    public double getTotalAmount() { return totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}