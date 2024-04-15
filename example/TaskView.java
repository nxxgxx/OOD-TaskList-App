package org.example;

/**
 * The TaskView class represents the view component in the MVC architecture for task management.
 * It provides methods to display tasks to the user interface.
 */
public class TaskView {

  /**
   * Displays information about a single task.
   *
   * @param task The task to display.
   */
  public void displayTask(Task task) {
    System.out.println("Task ID: " + task.getId());
    System.out.println("Description: " + task.getDescription());
    System.out.println("Date: " + task.getDate());
    System.out.println("Completed: " + task.getCompleted());
    System.out.println("Priority: " + task.getPriority());
    System.out.println();
  }
}
