package org.example;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Represents a task with a unique ID, description, date, completion status, and priority.
 */
public class Task implements ITask {
  private static int nextId = 1; // Static variable to track the next available ID
  private final int id; // Unique ID for each task
  private String description;
  private LocalDate date;
  private boolean completed;
  private Priority priority;

  /**
   * Constructs a task with the specified description, date, completion status, and priority.
   * @param description The description of the task.
   * @param date The due date of the task.
   * @param completed The completion status of the task.
   * @param priority The priority of the task.
   */
  public Task(String description, LocalDate date, boolean completed, Priority priority) {
    this.id = nextId++; // increment ID numbers
    this.description = description;
    this.date = date;
    this.completed = completed;
    this.priority = priority;
  }

  @Override
  public void printTask() {
    System.out.println("Task ID: " + id);
    System.out.println("Description: " + description);
    System.out.println("Date: " + date);
    System.out.println("Completed: " + completed);
    System.out.println("Priority: " + priority);
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public LocalDate getDate() {
    return date;
  }

  @Override
  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public boolean getCompleted() {
    return completed;
  }

  @Override
  public void setCompleted(boolean completed) {
    this.completed = completed;
  }

  @Override
  public Priority getPriority() {
    return priority;
  }

  @Override
  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  @Override
  public void changeDate(IDate newDate) {
    this.date = newDate.getCurrentDate();
  }

  @Override
  public void changeCompletion() {
    this.completed = !this.completed;
  }

  @Override
  public String toCSVString() {
    return String.format("%d,%s,%s,%s,%s", id, description, date, completed, priority);
  }

  @Override
  public boolean isCompleted() {
    return completed;
  }

  @Override
  public LocalDate getDueDate() {
    return date;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Task task = (Task) obj;
    return completed == task.completed &&
        Objects.equals(description, task.description) &&
        Objects.equals(date, task.date) &&
        priority == task.priority;
  }

  @Override
  public int hashCode() {
    return Objects.hash(description, date, completed, priority);
  }

  @Override
  public int compareTo(Task otherTask) {
    return this.priority.compareTo(otherTask.getPriority());
  }

  @Override
  public String toString() {
    // String representation of the task
    return "Task ID: " + id + "\n" +
        "Description: " + description + "\n" +
        "Date: " + date + "\n" +
        "Completed: " + completed + "\n" +
        "Priority: " + priority;
  }
}
