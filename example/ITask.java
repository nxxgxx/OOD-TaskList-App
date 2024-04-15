package org.example;

import java.time.LocalDate;

/**
 * Interface representing common behavior for tasks.
 */
interface ITask extends Comparable<Task> {

  /**
   * Prints details of the task.
   */
  void printTask();

  /**
   * Gets the ID of the task.
   * @return The ID of the task.
   */
  int getId();

  /**
   * Gets the description of the task.
   * @return The description of the task.
   */
  String getDescription();

  /**
   * Sets the description of the task.
   * @param description The new description of the task.
   */
  void setDescription(String description);

  /**
   * Gets the date of the task.
   * @return The date of the task.
   */
  LocalDate getDate();

  /**
   * Sets the date of the task.
   * @param date The new date of the task.
   */
  void setDate(LocalDate date);

  /**
   * Gets the completion status of the task.
   * @return True if the task is completed, false otherwise.
   */
  boolean getCompleted();

  /**
   * Sets the completion status of the task.
   * @param completed The new completion status of the task.
   */
  void setCompleted(boolean completed);

  /**
   * Gets the priority of the task.
   * @return The priority of the task.
   */
  Priority getPriority();

  /**
   * Sets the priority of the task.
   * @param priority The new priority of the task.
   */
  void setPriority(Priority priority);

  /**
   * Changes the date of the task.
   * @param newDate The new date for the task.
   */
  void changeDate(IDate newDate);

  /**
   * Toggles the completion status of the task.
   */
  void changeCompletion();

  /**
   * Converts the task to a CSV string format.
   * @return The task represented as a CSV string.
   */
  String toCSVString();

  /**
   * Checks if the task is completed.
   * @return True if the task is completed, false otherwise.
   */
  boolean isCompleted();

  /**
   * Gets the due date of the task.
   * @return The due date of the task.
   */
  LocalDate getDueDate(); // Corrected the return type to LocalDate
}
