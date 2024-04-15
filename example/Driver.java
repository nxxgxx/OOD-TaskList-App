package org.example;

import java.time.LocalDate;

/**
 * The main class for the application. It initializes the necessary components and starts the GUI.
 */
public class Driver {

  /**
   * The entry point of the application.
   * @param args The command-line arguments (not used in this application).
   */
  public static void main(String[] args) {
    // Create a TaskModel instance with the current date
    TaskModel taskModel = new TaskModel(LocalDate.now());

    // Create instances of controllers and GUI with appropriate arguments
    TaskController taskController = new TaskController(taskModel, new TaskView());
    EventController eventController = new EventController(taskModel, new TaskView());
    NonEventController nonEventController = new NonEventController(taskModel);
    TaskListGUI taskListGUI = new TaskListGUI(taskController, eventController, nonEventController);

    // Set the controller for the GUI
    taskListGUI.setController(taskController);

    // Start the GUI
    taskListGUI.start();
  }
}
