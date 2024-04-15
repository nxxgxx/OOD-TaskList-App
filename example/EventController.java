package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class EventController implements ITaskController {
  private final ITaskList model;
  public TaskView view;

  public EventController(TaskModel model, TaskView view) {
    this.model = model;
    this.view = view;
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
    model.changeDate(model.getTaskById(taskId), newDate);
  }

  @Override
  public void markTaskCompleted(int taskId) {
    model.markTaskCompleted(taskId);
  }

  @Override
  public List<Task> getAllTasks() {
    return model.getAllTasks();
  }

  @Override
  public void editTask(int taskId, String description, LocalDate dueDate, Priority priority) {
    Task taskToUpdate = model.getTaskById(taskId);
    if (taskToUpdate != null) {
      taskToUpdate.setDescription(description);
      taskToUpdate.setDate(dueDate);
      taskToUpdate.setPriority(priority);
      model.updateTask(taskToUpdate);
    } else {
      // Task with the given ID not found
      System.out.println("Task not found with ID: " + taskId);
    }
  }

  @Override
  public void saveTaskListToCSV(String filename) {
    try {
      model.saveTasksToCSV(filename);
      System.out.println("Task list saved to CSV file: " + filename);
    } catch (IOException e) {
      System.out.println("Error saving task list to CSV file: " + e.getMessage());
    }
  }

  @Override
  public void loadTaskListFromCSV(String filename) {
    try {
      model.loadTasksFromCSV(filename);
      System.out.println("Task list loaded from CSV file: " + filename);
    } catch (IOException e) {
      System.out.println("Error loading tasks from CSV: " + e.getMessage());
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
