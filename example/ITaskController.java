package org.example;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface representing a controller for tasks.
 */
public interface ITaskController {

  /**
   * Adds a new task with the given description, due date, and priority.
   * @param description The description of the task.
   * @param dueDate The due date of the task.
   * @param priority The priority of the task.
   */
  void addTask(String description, LocalDate dueDate, Priority priority);

  /**
   * Removes the task with the specified ID.
   * @param taskId The ID of the task to remove.
   */
  void removeTask(int taskId);

  /**
   * Changes the due date of the task with the specified ID.
   * @param taskId The ID of the task to update.
   * @param newDate The new due date for the task.
   */
  void changeTaskDate(int taskId, LocalDate newDate);

  /**
   * Marks the task with the specified ID as completed.
   * @param taskId The ID of the task to mark as completed.
   */
  void markTaskCompleted(int taskId);

  /**
   * Gets all tasks.
   * @return A list containing all tasks.
   */
  List<Task> getAllTasks();

  /**
   * Edits the details of the task with the specified ID.
   * @param taskId The ID of the task to edit.
   * @param description The new description of the task.
   * @param dueDate The new due date of the task.
   * @param priority The new priority of the task.
   */
  void editTask(int taskId, String description, LocalDate dueDate, Priority priority);

  /**
   * Saves the task list to a CSV file.
   * @param filename The name of the CSV file.
   */
  void saveTaskListToCSV(String filename);

  /**
   * Loads the task list from a CSV file.
   * @param filename The name of the CSV file.
   */
  void loadTaskListFromCSV(String filename);

  /**
   * Gets tasks filtered by completion status.
   * @param completed True to retrieve completed tasks, false for incomplete tasks.
   * @return A list containing tasks filtered by completion status.
   */
  List<Task> getTasksByCompletion(boolean completed);

  /**
   * Gets tasks filtered by priority.
   * @param priority The priority to filter tasks by.
   * @return A list containing tasks filtered by priority.
   */
  List<Task> getTasksByPriority(Priority priority);

  /**
   * Gets tasks filtered by date.
   * @param filterDate The date to filter tasks by.
   * @return A list containing tasks filtered by date.
   */
  List<Task> getTasksByDate(LocalDate filterDate);

  /**
   * Getsthe task with the specified ID.
   * @param taskId The ID of the task to retrieve.
   * @return The task with the specified ID, or null if not found.
   */
  Task getTaskById(int taskId);
}
