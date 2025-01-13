import database.DatabaseManager;
import database.TaskDAO;
import models.Task;

import java.sql.Connection;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DatabaseManager.getConnection()) {
            // Create a TaskDAO instance to perform CRUD operations
            TaskDAO taskDAO = new TaskDAO(connection);

            // Test adding a new task
            Task newTask = new Task("Complete Project", "Finish the Java project by Monday", LocalDateTime.now(), "UTC", "pending");
            taskDAO.addTask(newTask);
            System.out.println("Task added successfully!");

            // Test retrieving all tasks
            System.out.println("All Tasks:");
            taskDAO.getAllTasks().forEach(System.out::println);

            // Test retrieving a specific task by ID
            Task task = taskDAO.getTaskById(1);
            System.out.println("Task with ID 1: " + task);

            // Test updating a task
            if (task != null) {
                task.setStatus("completed");
                taskDAO.updateTask(task);
                System.out.println("Task updated successfully!");
            }

            // Test deleting a task
            /* 
            if (task != null) {
                taskDAO.deleteTask(task.getId());
                System.out.println("Task deleted successfully!");
            }
            */
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
