package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Interface representing a list of tasks.
 */
public interface ITaskList {

  /**
   * Adds a task to the list.
   * @param task The task to add.
   */
  void addTask(Task task);

  /**
   * Removes a task from the list.
   * @param taskId The ID of the task to remove.
   */
  void removeTask(int taskId);

  /**
   * Changes the date of a task in the list.
   * @param task The task to update.
   * @param newDate The new date for the task.
   */
  void changeDate(Task task, LocalDate newDate);

  /**
   * Gets all tasks in the list.
   * @return A list containing all tasks.
   */
  List<Task> getAllTasks();

  /**
   * Gets the task with the specified ID.
   * @param taskId The ID of the task to retrieve.
   * @return The task with the specified ID, or null if not found.
   */
  Task getTaskById(int taskId);

  /**
   * Marks a task as completed.
   * @param taskId The ID of the task to mark as completed.
   */
  void markTaskCompleted(int taskId);

  /**
   * Updates the details of a task in the list.
   * @param task The task with updated details.
   */
  void updateTask(Task task);

  /**
   * Saves the tasks in the list to a CSV file.
   * @param filename The name of the CSV file.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  void saveTasksToCSV(String filename) throws IOException;

  /**
   * Loads tasks from a CSV file into the list.
   * @param filename The name of the CSV file.
   * @throws IOException If an I/O error occurs while reading from the file.
   */
  void loadTasksFromCSV(String filename) throws IOException;
}
