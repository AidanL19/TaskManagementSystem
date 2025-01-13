package database;

import models.Task;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    // Constructor: Initializes the DAO with a database connection.
    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    // Adds a new task to the database.
    public void addTask(Task task) throws SQLException {
        String query = "INSERT INTO tasks (title, description, due_date, timezone, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
            statement.setString(4, task.getTimeZone());
            statement.setString(5, task.getStatus());
            statement.executeUpdate();
        }
    }

    // Retrieves all tasks from the database and returns them as a list.
    public List<Task> getAllTasks() throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks";
        try (Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Task task = new Task(
                    resultSet.getInt("id"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("due_date").toLocalDateTime(),
                    resultSet.getString("timezone"),
                    resultSet.getString("status")
                );
                tasks.add(task);
            }
        }
        return tasks;
    }

    // Retrieves a specific task by its ID.
    public Task getTaskById(int id) throws SQLException {
        String query = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getTimestamp("due_date").toLocalDateTime(),
                        resultSet.getString("timezone"),
                        resultSet.getString("status")
                    );
                }
            }
        }
        return null; 
    }

    // Updates an existing task in the database.
    public void updateTask(Task task) throws SQLException {
        String query = "UPDATE tasks SET title = ?, description = ?, due_date = ?, timezone = ?, status = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, task.getTitle());
            statement.setString(2, task.getDescription());
            statement.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
            statement.setString(4, task.getTimeZone());
            statement.setString(5, task.getStatus());
            statement.setInt(6, task.getId());
            statement.executeUpdate();
        }
    }

    // Deletes a task from the database by its ID.
    public void deleteTask(int id) throws SQLException {
        String query = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}