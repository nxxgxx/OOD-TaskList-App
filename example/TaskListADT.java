package org.example;

// Define the TaskListADT interface
public interface TaskListADT {
  void addTask(Task task);
  void removeTask(int taskId);
  Task getTaskById(int taskId);
}