package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The TaskModel class represents the model component in the MVC architecture for task management.
 * It provides methods to interact with tasks, such as adding, retrieving, and modifying tasks.
 */
public class TaskModel implements ITaskList {
  private final TaskList taskList;

  /**
   * Initializes a TaskModel with the current date.
   *
   * @param currentDate The current date.
   */
  public TaskModel(LocalDate currentDate) {
    this.taskList = new TaskList();
  }

  /**
   * Adds a task to the model.
   *
   * @param task The task to add.
   */
  public void addTask(Task task) {
    taskList.addTask(task);
  }

  /**
   * Retrieves all tasks from the model.
   *
   * @return A list containing all tasks.
   */
  public List<Task> getAllTasks() {
    return taskList.getAllTasks();
  }

  /**
   * Saves tasks to a CSV file.
   *
   * @param filename The name of the CSV file.
   * @throws IOException If an I/O error occurs.
   */
  public void saveTasksToCSV(String filename) throws IOException {
    try (FileWriter writer = new FileWriter(filename)) {
      List<Task> allTasks = taskList.getAllTasks();
      for (Task task : allTasks) {
        writer.write(task.toCSVString() + "\n");
      }
    }
  }

  /**
   * Loads tasks from a CSV file into the model.
   *
   * @param filename The name of the CSV file.
   * @throws IOException If an I/O error occurs.
   */
  public void loadTasksFromCSV(String filename) throws IOException {
    taskList.loadFromCSV(filename);
  }

  /**
   * Removes a task from the model.
   *
   * @param taskId The ID of the task to remove.
   */
  public void removeTask(int taskId) {
    taskList.removeTask(taskId);
  }

  /**
   * Changes the due date of a task.
   *
   * @param task  The ID of the task.
   * @param newDate The new due date for the task.
   */
  @Override
  public void changeDate(Task task, LocalDate newDate) {

  }

  /**
   * Changes the due date of a task.
   *
   * @param taskId  The ID of the task.
   * @param newDate The new due date for the task.
   */
  public void changeTaskDate(int taskId, LocalDate newDate) {
    taskList.changeTaskDate(taskId, newDate);
  }

  /**
   * Marks a task as completed.
   *
   * @param taskId The ID of the task to mark as completed.
   */

  public void markTaskCompleted(int taskId) {
    taskList.markTaskCompleted(taskId);
  }

  @Override
  public void updateTask(Task task) {

  }

  /**
   * Retrieves a task by its ID.
   *
   * @param taskId The ID of the task to retrieve.
   * @return The task with the specified ID, or null if not found.
   */
  public Task getTaskById(int taskId) {
    return taskList.getTaskById(taskId);
  }

  public void updateTask() {
  }
}
