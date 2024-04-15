package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// TaskList to implement TaskListADT and incorporate recursion
public class TaskList implements TaskListADT {
  private Node head; // Reference to the first node in the linked list

  // Constructor to initialize the linked list with a null head
  public TaskList() {
    this.head = null;
  }

  /**
   * Adds a new task to the end of the linked list.
   *
   * @param task The task to add.
   */
  public void addTask(Task task) {
    Node newNode = new Node(task);
    if (head == null) {
      head = newNode;
    } else {
      Node current = head;
      while (current.getNext() != null) {
        current = current.getNext();
      }
      current.setNext(newNode);
    }
  }

  /**
   * Retrieves all tasks from the linked list.
   *
   * @return A list containing all tasks.
   */
  public List<Task> getAllTasks() {
    List<Task> allTasks = new ArrayList<>();
    Node currentNode = head;
    while (currentNode != null) {
      allTasks.add(currentNode.getTask());
      currentNode = currentNode.getNext();
    }
    return allTasks;
  }

  /**
   * Loads tasks from a CSV file into the list.
   *
   * @param filename The name of the CSV file to load tasks from.
   */
  public void loadFromCSV(String filename) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] attributes = line.split(",");
        // Create task object from CSV attributes and add to the list
        addTask(createTaskFromCSV(attributes));
      }
    } catch (IOException e) {
      System.out.println("Error loading tasks from CSV: " + e.getMessage());
    }
  }

  /**
   * Loads tasks from a CSV file into the list.
   *
   * @param attributes The name of the CSV file to load tasks from.
   */
  private Task createTaskFromCSV(String[] attributes) {
    // Parse attributes and create a Task object
    String description = attributes[1];
    LocalDate date = LocalDate.parse(attributes[2]);
    boolean completed = Boolean.parseBoolean(attributes[3]);
    String priorityString = attributes[4]; // Assuming priority is a string representation
    Priority priority = Priority.valueOf(priorityString);

    return new Task(description, date, completed, priority); // Assuming id is set internally in Task
  }

  /**
   * Marks a task with the specified ID as completed.
   *
   * @param taskId The ID of the task to mark as completed.
   */
  public void markTaskCompleted(int taskId) {
    Node currentNode = head;
    while (currentNode != null) {
      if (currentNode.getTask().getId() == taskId) {
        currentNode.getTask().setCompleted(true);
        return; // Task found and marked as completed, exit the method
      }
      currentNode = currentNode.getNext();
    }
    // If the loop completes without finding the task, it means the task ID was not found
    System.out.println("Task with ID " + taskId + " not found.");
  }

  /**
   * Changes the due date of a task with the specified ID.
   *
   * @param taskId  The ID of the task to change the due date for.
   * @param newDate The new due date for the task.
   */
  public void changeTaskDate(int taskId, LocalDate newDate) {
    Node currentNode = head;
    while (currentNode != null) {
      if (currentNode.getTask().getId() == taskId) {
        currentNode.getTask().setDate(newDate);
        return; // Task found and date changed, exit the method
      }
      currentNode = currentNode.getNext();
    }
    // If the loop completes without finding the task, it means the task ID was not found
    System.out.println("Task with ID " + taskId + " not found.");
  }

  /**
   * Removes a task with the specified ID from the list.
   *
   * @param taskId The ID of the task to remove.
   */
  public void removeTask(int taskId) {
    head = removeTaskRecursive(head, taskId);
  }

  // Recursive helper method to remove task
  private Node removeTaskRecursive(Node current, int taskId) {
    if (current == null) {
      return null; // Base case: task not found
    }
    if (current.getTask().getId() == taskId) {
      return current.getNext(); // Found task, remove it
    }
    // Recur for the rest of the list
    current.setNext(removeTaskRecursive(current.getNext(), taskId));
    return current;
  }

  /**
   * Retrieves a task by its ID.
   *
   * @param taskId The ID of the task to retrieve.
   * @return The task with the specified ID, or null if not found.
   */
  public Task getTaskById(int taskId) {
    return getTaskByIdRecursive(head, taskId);
  }

  // Recursive helper method to get task by ID
  private Task getTaskByIdRecursive(Node current, int taskId) {
    if (current == null) {
      return null; // Base case: task not found
    }
    if (current.getTask().getId() == taskId) {
      return current.getTask(); // Found task
    }
    // Recursion for the rest of the list
    return getTaskByIdRecursive(current.getNext(), taskId);
  }

  /**
   * Inner class representing a node in the linked list.
   */
  private static class Node {
    private final Task task; // Task object
    private Node next; // Reference to the next node

    /**
     * Initializes a node with the specified task.
     *
     * @param task The task contained in the node.
     */
    public Node(Task task) {
      this.task = task;
      this.next = null;
    }

    // Getter method for the next node
    public Node getNext() {
      return next;
    }

    // Setter method for the next node
    public void setNext(Node next) {
      this.next = next;
    }

    // Getter method for the task
    public Task getTask() {
      return task;
    }
  }

}
