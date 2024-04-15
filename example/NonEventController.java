package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

/**
 * Controller for non-event tasks.
 */
public class NonEventController implements ITaskController {
  private final TaskModel model;

  /**
   * Constructs a NonEventController with the specified TaskModel.
   * @param model The TaskModel to be used by the controller.
   */
  public NonEventController(TaskModel model) {
    this.model = model;
  }

  @Override
  public void addTask(String description, LocalDate dueDate, Priority priority) {
    Task task = new Task(description, dueDate, false, priority);
    model.addTask(task);
  }

  @Override
  public void removeTask(int taskId) {
    model.removeTask(taskId);
  }

  @Override
  public void changeTaskDate(int taskId, LocalDate newDate) {
    model.changeTaskDate(taskId, newDate);
  }

  @Override
  public void markTaskCompleted(int taskId) {
    model.markTaskCompleted(taskId);
  }

  @Override
  public void editTask(int taskId, String description, LocalDate dueDate, Priority priority) {
    Task taskToUpdate = model.getTaskById(taskId);
    if (taskToUpdate != null) {
      taskToUpdate.setDescription(description);
      taskToUpdate.setDate(dueDate);
      taskToUpdate.setPriority(priority);
      model.updateTask();
    } else {
      System.out.println("Task not found with ID: " + taskId);
    }
  }

  @Override
  public List<Task> getAllTasks() {
    return model.getAllTasks();
  }

  @Override
  public void saveTaskListToCSV(String filename) {
    try {
      model.saveTasksToCSV(filename);
      JOptionPane.showMessageDialog(null, "Task list saved to CSV file: " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error saving task list to CSV file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public void loadTaskListFromCSV(String filename) {
    try {
      model.loadTasksFromCSV(filename);
      JOptionPane.showMessageDialog(null, "Task list loaded from CSV file: " + filename, "Success", JOptionPane.INFORMATION_MESSAGE);
    } catch (IOException e) {
      JOptionPane.showMessageDialog(null, "Error loading tasks from CSV: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  @Override
  public List<Task> getTasksByCompletion(boolean completed) {
    return model.getAllTasks().stream()
        .filter(task -> task.isCompleted() == completed)
        .collect(Collectors.toList());
  }

  @Override
  public List<Task> getTasksByPriority(Priority priority) {
    return model.getAllTasks().stream()
        .filter(task -> task.getPriority() == priority)
        .collect(Collectors.toList());
  }

  @Override
  public List<Task> getTasksByDate(LocalDate filterDate) {
    return model.getAllTasks().stream()
        .filter(task -> task.getDueDate().isEqual(filterDate) || task.getDueDate().isAfter(filterDate))
        .collect(Collectors.toList());
  }

  @Override
  public Task getTaskById(int taskId) {
    return model.getTaskById(taskId);
  }
}
