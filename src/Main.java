import database.DatabaseManager;
import database.TaskDAO;
import models.Task;
import utils.TimeZoneUtils;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Establish a database connection.
        try (Connection connection = DatabaseManager.getConnection()) {
            // Initialize TaskDAO to perform database operations.
            TaskDAO taskDAO = new TaskDAO(connection);

            // Create a Scanner for user input.
            Scanner scanner = new Scanner(System.in);

            // Display the menu until the user chooses to exit.
            while (true) {
                System.out.println("\n=== Task Management System ===");
                System.out.println("1. Add Task");
                System.out.println("2. View All Tasks");
                System.out.println("3. Update Task");
                System.out.println("4. Delete Task");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                // Read the user's menu choice.
                int choice = scanner.nextInt();
                // Consume the newline character.
                scanner.nextLine();

                // Perform the action based on the user's choice.
                switch (choice) {
                    case 1:
                        addTask(taskDAO, scanner);
                        break;
                    case 2:
                        viewAllTasks(taskDAO);
                        break;
                    case 3:
                        updateTask(taskDAO, scanner);
                        break;
                    case 4:
                        deleteTask(taskDAO, scanner);
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        // Exit the program.
                        return; 
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } 
        catch (Exception e) {
            // Print any unexpected errors.
            e.printStackTrace();
        }
    }

    // Prompts the user to input task details and adds the task to the database.
    private static void addTask(TaskDAO taskDAO, Scanner scanner) {
        try {
            // Prompt for task details.
            System.out.print("Enter title: ");
            String title = scanner.nextLine();
    
            System.out.print("Enter description: ");
            String description = scanner.nextLine();
    
            System.out.print("Enter due date (yyyy-MM-ddTHH:mm:ss): ");
            String dueDateInput = scanner.nextLine();
            LocalDateTime dueDate = LocalDateTime.parse(dueDateInput);
    
            String timeZone;
            String utcOffset;
    
            // Validate the time zone and fetch UTC offset.
            while (true) {
                System.out.print("Enter timezone (e.g., America/New_York): ");
                timeZone = scanner.nextLine();
    
                if (TimeZoneUtils.isValidTimeZone(timeZone)) {
                    utcOffset = TimeZoneUtils.getUTCOffset(timeZone);
                    if (utcOffset != null && !utcOffset.isEmpty()) {
                        break;
                    } 
                    else {
                        System.out.println("Error: Unable to fetch UTC offset. Please try again.");
                    }
                } 
                else {
                    System.out.println("Invalid timezone. Please try again.");
                }
            }
    
            // Convert dueDate to UTC using the offset.
            LocalDateTime dueDateUtc = adjustToUTC(dueDate, utcOffset);
    
            System.out.print("Enter status (pending/completed): ");
            String status = scanner.nextLine();
    
            // Create a new Task object with both original and UTC due dates.
            Task task = new Task(title, description, dueDate, timeZone, dueDateUtc, status);
    
            // Add the task to the database.
            taskDAO.addTask(task);
            System.out.println("Task added successfully!");
        } 
        catch (Exception e) {
            System.err.println("Failed to add task: " + e.getMessage());
        }
    }
    
    

    // Retrieves and displays all tasks from the database.
    private static void viewAllTasks(TaskDAO taskDAO) {
        try {
            System.out.println("\n=== All Tasks ===");

            // Fetch and display all tasks.
            taskDAO.getAllTasks().forEach(System.out::println);
        } 
        catch (Exception e) {
            System.err.println("Failed to retrieve tasks: " + e.getMessage());
        }
    }

    // Prompts the user to update the status of an existing task by its ID.
    private static void updateTask(TaskDAO taskDAO, Scanner scanner) {
        try {
            System.out.print("Enter task ID to update: ");
            int taskId = scanner.nextInt();
            // Consume the newline character.
            scanner.nextLine(); 

            // Fetch the task with the given ID.
            Task task = taskDAO.getTaskById(taskId);

            if (task == null) {
                System.out.println("Task not found!");
                return;
            }

            System.out.print("Enter new status: ");
            String newStatus = scanner.nextLine();

            // Update the task's status.
            task.setStatus(newStatus);
            taskDAO.updateTask(task);
            System.out.println("Task updated successfully!");
        } 
        catch (Exception e) {
            System.err.println("Failed to update task: " + e.getMessage());
        }
    }

    // Prompts the user to delete a task by its ID.
    private static void deleteTask(TaskDAO taskDAO, Scanner scanner) {
        try {
            System.out.print("Enter task ID to delete: ");
            int taskId = scanner.nextInt();

            // Delete the task with the given ID.
            taskDAO.deleteTask(taskId);
            System.out.println("Task deleted successfully!");
        } 
        catch (Exception e) {
            System.err.println("Failed to delete task: " + e.getMessage());
        }
    }

    // Adjusts a LocalDateTime to UTC based on the given offset (e.g., +05:00 or -03:30).
    private static LocalDateTime adjustToUTC(LocalDateTime localDateTime, String utcOffset) {
        // Extract hours.
        int hours = Integer.parseInt(utcOffset.substring(1, 3));
        // Extract minutes.
        int minutes = Integer.parseInt(utcOffset.substring(4, 6)); 
        // Check if the offset is negative.
        boolean isNegative = utcOffset.startsWith("-"); 

        // Adjust the LocalDateTime by subtracting the offset to convert to UTC.
        if (isNegative) {
            return localDateTime.plusHours(hours).plusMinutes(minutes);
        } 
        else {
            return localDateTime.minusHours(hours).minusMinutes(minutes);
        }
    }
}