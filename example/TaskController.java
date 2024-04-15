package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Represents the controller in the MVC architecture for managing tasks.
 */
public class TaskController {
  private final TaskModel model; // Define the model variable
  private final TaskView taskView; // Define the taskView variable

  /**
   * Constructs a TaskController with the specified TaskModel and TaskView.
   * @param taskModel The TaskModel to be associated with this controller.
   * @param taskView The TaskView to be associated with this controller.
   */
  public TaskController(TaskModel taskModel, TaskView taskView) {
    this.model = taskModel; // Initialize the model variable with the provided taskModel
    this.taskView = taskView; // Initialize the taskView variable with the provided taskView
  }

  /**
   * Adds a new task with the specified description, due date, and priority.
   * @param description The description of the task.
   * @param dueDate The due date of the task.
   * @param priority The priority of the task.
   */
  public void addTask(String description, LocalDate dueDate, Priority priority) {
    Task task = new Task(description, dueDate, false, priority);
    model.addTask(task);
  }

  /**
   * Removes the task with the specified ID.
   * @param taskId The ID of the task to be removed.
   */
  public void removeTask(int taskId) {
    model.removeTask(taskId);
  }

  /**
   * Marks the task with the specified ID as completed.
   * @param taskId The ID of the task to be marked as completed.
   */
  public void markTaskCompleted(int taskId) {
    model.markTaskCompleted(taskId);
  }

  /**
   * Gets all tasks from the model.
   * @return A list of all tasks.
   */
  public List<Task> getAllTasks() {
    return model.getAllTasks();
  }

  /**
   * Edits the task with the specified ID, updating its description, due date, and priority.
   * @param taskId The ID of the task to be edited.
   * @param description The new description of the task.
   * @param dueDate The new due date of the task.
   * @param priority The new priority of the task.
   */
  public void editTask(int taskId, String description, LocalDate dueDate, Priority priority) {
    Task taskToUpdate = model.getTaskById(taskId);
    if (taskToUpdate != null) {
      taskToUpdate.setDescription(description);
      taskToUpdate.setDate(dueDate);
      taskToUpdate.setPriority(priority);
      // You can add more attributes as needed
      // Update the task in the model
      model.updateTask(taskToUpdate);
      JOptionPane.showMessageDialog(null, "Task updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    } else {
      // Task with the given ID not found
      JOptionPane.showMessageDialog(null, "Task not found with ID: " + taskId, "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Saves the task list to a CSV file with the specified filename.
   * @param filename The name of the CSV file.
   */
  public void saveTaskListToCSV(String filename) {
    try {
      model.saveTasksToCSV(filename);
      JOptionPane.showMessageDialog(null, "Task list saved to CSV file: " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error saving task list to CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Loads tasks from a CSV file with the specified filename.
   * @param filename The name of the CSV file.
   */
  public void loadTaskListFromCSV(String filename) {
    try {
      model.loadTasksFromCSV(filename);
      JOptionPane.showMessageDialog(null, "Task list loaded from CSV file: " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error loading tasks from CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * Gets tasks filtered by completion status.
   * @param completed The completion status to filter by.
   * @return A list of tasks filtered by completion status.
   */
  public List<Task> getTasksByCompletion(boolean completed) {
    return model.getAllTasks().stream()
        .filter(task -> task.isCompleted() == completed)
        .collect(Collectors.toList());
  }

  /**
   * Gets tasks filtered by priority.
   * @param priority The priority to filter by.
   * @return A list of tasks filtered by priority.
   */
  public List<Task> getTasksByPriority(Priority priority) {
    return model.getAllTasks().stream()
        .filter(task -> task.getPriority() == priority)
        .collect(Collectors.toList());
  }

  /**
   * Gets tasks filtered by due date.
   * @param filterDate The due date to filter by.
   * @return A list of tasks filtered by due date.
   */
  public List<Task> getTasksByDate(LocalDate filterDate) {
    return model.getAllTasks().stream()
        .filter(task -> task.getDueDate().isEqual(filterDate) || task.getDueDate().isAfter(filterDate))
        .collect(Collectors.toList());
  }

  /**
   * Gets the task with the specified ID.
   * @param taskId The ID of the task to retrieve.
   * @return The task with the specified ID, or null if not found.
   */
  public Task getTaskById(int taskId) {
    return model.getTaskById(taskId); // Use the model field here
  }

  /**
   * Gets the task view associated with this controller.
   * @return The task view.
   */
  public TaskView getTaskView() {
    return taskView;
  }
}
