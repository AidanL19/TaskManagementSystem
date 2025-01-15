package models;

import java.time.LocalDateTime;

public class Task {
    // Fields for task properties

    // Unique identifier for the task
    private int id; 
    // Title of the task
    private String title; 
    // Description or details about the task
    private String description; 
    // Due date and time of the task
    private LocalDateTime dueDate;
    // Time zone of the task 
    private String timeZone;
    // UTC due date and time of the task
    private LocalDateTime dueDateUTC;
    // Current status of the task 
    private String status; 

    // Constructor with all fields, used for tasks retrieved from the database
    public Task(int id, String title, String description, LocalDateTime dueDate, String timeZone, LocalDateTime dueDateUTC, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.timeZone = timeZone;
        this.dueDateUTC = dueDateUTC;
        this.status = status;
    }

    // Constructor without ID, used for new tasks before they're saved in the database
    public Task(String title, String description, LocalDateTime dueDate, String timeZone, LocalDateTime dueDateUTC, String status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.timeZone = timeZone;
        this.dueDateUTC = dueDateUTC;
        this.status = status;
    }

    // Getters and setters for each field

    // Get the task's unique identifier
    public int getId() {
        return id; 
    }

    // Set the task's unique identifier
    public void setId(int id) {
        this.id = id; 
    }

    // Get the task's title
    public String getTitle() {
        return title; 
    }

    // Set the task's title
    public void setTitle(String title) {
        this.title = title; 
    }

    // Get the task's description
    public String getDescription() {
        return description; 
    }

    // Set the task's description
    public void setDescription(String description) {
        this.description = description; 
    }

    // Get the task's due date
    public LocalDateTime getDueDate() {
        return dueDate; 
    }

    // Set the task's due date
    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    // Get the task's time zone
    public String getTimeZone() {
        return timeZone; 
    }

    // Set the task's time zone
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone; 
    }

    // Get the task's UTC due date
    public LocalDateTime getDueDateUTC() {
        return dueDateUTC; 
    }

    // Set the task's UTC due date
    public void setDueDateUTC(LocalDateTime dueDateUTC) {
        this.dueDateUTC = dueDateUTC; 
    }

    // Get the task's status
    public String getStatus() {
        return status; 
    }

    // Set the task's status
    public void setStatus(String status) {
        this.status = status; 
    }

    // Method to return a string representation of the task
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", timeZone='" + timeZone + '\'' +
                ", dueDateUTC=" + dueDateUTC +
                ", status='" + status + '\'' +
                '}';
    }
}
