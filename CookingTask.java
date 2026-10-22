package org.example.subsystemB;

public class CookingTask {
    private String taskId;
    private String stationName;
    private String status; // PENDING, COOKING_IN_PROGRESS, READY_FOR_PICKUP, FAILED_QUALITY

    public CookingTask(String taskId, String stationName, String status) {
        this.taskId = taskId;
        this.stationName = stationName;
        this.status = status;
    }

    public String getTaskId() { return taskId; }
    public String getStationName() { return stationName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}