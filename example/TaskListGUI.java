package org.example;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.*;

/**
 * The TaskListGUI class represents the graphical user interface for managing tasks.
 * It provides functionality for adding, removing, editing, filtering, and displaying tasks.
 */
public class TaskListGUI extends JFrame {

  private final TaskController taskController;
  private final EventController eventController; // Declare EventController
  private final NonEventController nonEventController; // Declare NonEventController
  private ConfettiPanel confettiPanel;

  /**
   * Constructs a TaskListGUI object with the specified controllers.
   *
   * @param taskController      The controller for task-related actions.
   * @param eventController     The controller for event-related actions.
   * @param nonEventController  The controller for non-event-related actions.
   */
  public TaskListGUI(TaskController taskController, EventController eventController, NonEventController nonEventController) {
    this.taskController = taskController; // Initialize TaskController
    this.eventController = eventController; // Initialize EventController
    this.nonEventController = nonEventController; // Initialize NonEventController
    this.confettiPanel = new ConfettiPanel(); // Initialize the ConfettiPanel

    setTitle("Task List Manager");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(800, 700);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // LEFT SIDE BUTTONS
    JPanel leftPanel = new JPanel(new GridLayout(0, 1, 5, 5));
    leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

    JButton addButton = new JButton("Add Task");
    addButton.setPreferredSize(new Dimension(150, 75));
    addButton.addActionListener(e -> showAddTaskDialog());
    leftPanel.add(addButton);

    JButton removeButton = new JButton("Remove Task");
    removeButton.setPreferredSize(new Dimension(150, 75));
    removeButton.addActionListener(e -> {
      showRemoveTaskDialog(); // Call the method to show the remove task dialog
    });
    leftPanel.add(removeButton);

    JButton editTaskButton = new JButton("Edit Task");
    editTaskButton.setPreferredSize(new Dimension(150, 75));
    editTaskButton.addActionListener(e -> {
      showEditTaskDialog(); // Call the method to show the edit task dialog
    });
    leftPanel.add(editTaskButton);

    JButton markCompletedButton = new JButton("Mark Task Completed");
    markCompletedButton.setPreferredSize(new Dimension(150, 75));
    markCompletedButton.addActionListener(e -> {
      String taskIdString = JOptionPane.showInputDialog("Enter Task ID to Mark Completed:");
      if (taskIdString != null && !taskIdString.isEmpty()) {
        try {
          int taskId = Integer.parseInt(taskIdString);
          taskController.markTaskCompleted(taskId);

          // Start the confetti animation
          startConfettiAnimation();

          // Show confirmation dialog
          JOptionPane.showMessageDialog(TaskListGUI.this, "Task marked as completed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid Task ID", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    leftPanel.add(markCompletedButton);

    mainPanel.add(leftPanel);

    // RIGHT SIDE BUTTONS
    JPanel rightPanel = new JPanel(new GridLayout(0, 1, 5, 5));

    JButton filterButton = new JButton("Filter Tasks");
    filterButton.setPreferredSize(new Dimension(150, 75));
    filterButton.addActionListener(e -> {
      // Display filter options dialog
      showFilterOptionsDialog();
    });
    rightPanel.add(filterButton);

    JButton viewAllTasksButton = new JButton("View All Tasks");
    viewAllTasksButton.setPreferredSize(new Dimension(150, 75));
    viewAllTasksButton.addActionListener(e -> {
      List<Task> allTasks = taskController.getAllTasks();
      StringBuilder tasksText = new StringBuilder();
      for (Task task : allTasks) {
        tasksText.append(task.toString()).append("\n");
        tasksText.append("\n");
      }
      // Update a JTextArea with the list of tasks
      JTextArea tasksTextArea = new JTextArea(tasksText.toString());
      JScrollPane scrollPane = new JScrollPane(tasksTextArea);
      JOptionPane.showMessageDialog(TaskListGUI.this, scrollPane, "All Tasks", JOptionPane.INFORMATION_MESSAGE);
    });
    rightPanel.add(viewAllTasksButton);

    JButton saveButton = new JButton("Save Tasks");
    saveButton.setPreferredSize(new Dimension(150, 75));
    saveButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showSaveDialog(TaskListGUI.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        String filename = fileChooser.getSelectedFile().getPath();
        taskController.saveTaskListToCSV(filename);
      }
    });
    rightPanel.add(saveButton);

    JButton loadButton = new JButton("Load Tasks");
    loadButton.setPreferredSize(new Dimension(150, 75));
    loadButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int result = fileChooser.showOpenDialog(TaskListGUI.this);
      if (result == JFileChooser.APPROVE_OPTION) {
        String filename = fileChooser.getSelectedFile().getPath();
        taskController.loadTaskListFromCSV(filename);
      }
    });
    rightPanel.add(loadButton);

    mainPanel.add(rightPanel);

    confettiPanel = new ConfettiPanel(); // Initialize the ConfettiPanel
    getContentPane().add(mainPanel, BorderLayout.NORTH);
    getContentPane().add(confettiPanel, BorderLayout.CENTER); // Add ConfettiPanel to the center

    setVisible(true);
  }

  private void showAddTaskDialog() {
    JDialog dialog = new JDialog(this, "Add Task", true);
    dialog.setLayout(new BorderLayout());
    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

    panel.add(new JLabel("Description:"));
    JTextField descriptionField = new JTextField();
    panel.add(descriptionField);

    panel.add(new JLabel("Date (MM/DD/YYYY):"));
    JTextField dateField = new JTextField();
    panel.add(dateField);

    panel.add(new JLabel("Priority:"));
    JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
    panel.add(priorityComboBox);

    dialog.add(panel, BorderLayout.CENTER);

    JButton addButton = new JButton("Add");
    addButton.addActionListener(e -> {
      String description = descriptionField.getText();
      String dateString = dateField.getText();
      Priority priority = (Priority) priorityComboBox.getSelectedItem();
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate selectedDate = LocalDate.parse(dateString, formatter);

        // Validate if the selected date is not in the past
        if (selectedDate.isBefore(LocalDate.now())) {
          JOptionPane.showMessageDialog(TaskListGUI.this, "Please select a current or future date.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        taskController.addTask(description, selectedDate, priority);
        dialog.dispose();
      } catch (DateTimeParseException ex) {
        JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid date format. Please use MM/DD/YYYY", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    dialog.add(addButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  private void showRemoveTaskDialog() {
    String taskIdString = JOptionPane.showInputDialog("Enter Task ID to Remove:");
    if (taskIdString != null && !taskIdString.isEmpty()) {
      try {
        int taskId = Integer.parseInt(taskIdString);
        taskController.removeTask(taskId);
        JOptionPane.showMessageDialog(TaskListGUI.this, "Task removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid Task ID", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void showEditTaskDialog() {
    String taskIdString = JOptionPane.showInputDialog("Enter Task ID to Edit:");
    if (taskIdString != null && !taskIdString.isEmpty()) {
      try {
        int taskId = Integer.parseInt(taskIdString);
        Task taskToEdit = taskController.getTaskById(taskId);
        if (taskToEdit != null) {
          populateEditTaskForm(taskToEdit);
        } else {
          JOptionPane.showMessageDialog(TaskListGUI.this, "Task with ID " + taskId + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid Task ID", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void populateEditTaskForm(Task taskToEdit) {
    JDialog dialog = new JDialog(this, "Edit Task", true);
    dialog.setLayout(new BorderLayout());
    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

    panel.add(new JLabel("Description:"));
    JTextField descriptionField = new JTextField(taskToEdit.getDescription());
    panel.add(descriptionField);

    panel.add(new JLabel("Date (MM/DD/YYYY):"));
    JTextField dateField = new JTextField(taskToEdit.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    panel.add(dateField);

    panel.add(new JLabel("Priority:"));
    JComboBox<Priority> priorityComboBox = new JComboBox<>(Priority.values());
    priorityComboBox.setSelectedItem(taskToEdit.getPriority());
    panel.add(priorityComboBox);

    dialog.add(panel, BorderLayout.CENTER);

    JButton editButton = new JButton("Edit");
    editButton.addActionListener(e -> {
      String description = descriptionField.getText();
      String dateString = dateField.getText();
      Priority priority = (Priority) priorityComboBox.getSelectedItem();
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate selectedDate = LocalDate.parse(dateString, formatter);

        // Validate if the selected date is not in the past
        if (selectedDate.isBefore(LocalDate.now())) {
          JOptionPane.showMessageDialog(TaskListGUI.this, "Please select a current or future date.", "Error", JOptionPane.ERROR_MESSAGE);
          return;
        }

        taskController.editTask(taskToEdit.getId(), description, selectedDate, priority);
        dialog.dispose();
      } catch (DateTimeParseException ex) {
        JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid date format. Please use MM/DD/YYYY", "Error", JOptionPane.ERROR_MESSAGE);
      }
    });

    dialog.add(editButton, BorderLayout.SOUTH);
    dialog.setVisible(true);
  }

  // Method to show filter options dialog
  private void showFilterOptionsDialog() {
    // Create a dialog box or dropdown menu with filter options
    // For example, using JOptionPane:
    String[] options = {"Filter by Date", "Filter by Priority", "Filter by Completion"};
    String selectedOption = (String) JOptionPane.showInputDialog(
        TaskListGUI.this,
        "Choose a filter option:",
        "Filter Tasks",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]);

    // Apply the selected filter
    if (selectedOption != null) {
      switch (selectedOption) {
        case "Filter by Date":
          // Show date filter dialog
          showDateFilterDialog(); // Add this method call
          break;
        case "Filter by Priority":
          // Show priority filter dialog
          showPriorityFilterDialog();
          break;
        case "Filter by Completion":
          // Show completion filter dialog
          showCompletionFilterDialog();
          break;
        default:
          break;
      }
    }
  }
  // Method to show date filter dialog
  private void showDateFilterDialog() {
    // Implement date filter dialog
    String dateString = JOptionPane.showInputDialog("Enter Date (MM/DD/YYYY):");
    if (dateString != null && !dateString.isEmpty()) {
      try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate filterDate = LocalDate.parse(dateString, formatter);

        // Filter tasks based on the selected date
        List<Task> filteredTasks = taskController.getTasksByDate(filterDate);

        // Display the filtered tasks
        displayFilteredTasks(filteredTasks);
      } catch (DateTimeParseException ex) {
        JOptionPane.showMessageDialog(TaskListGUI.this, "Invalid date format. Please use MM/DD/YYYY", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  // Method to show priority filter dialog
  private void showPriorityFilterDialog() {
    // Implement priority filter dialog
    Object[] options = {"Red", "Yellow", "Green"};
    String selectedPriority = (String) JOptionPane.showInputDialog(
        TaskListGUI.this,
        "Choose a priority level:",
        "Filter by Priority",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]);

    // Filter tasks based on the selected priority
    if (selectedPriority != null) {
      List<Task> filteredTasks = taskController.getTasksByPriority(Priority.valueOf(selectedPriority.toUpperCase()));

      // Display the filtered tasks
      displayFilteredTasks(filteredTasks);
    }
  }

  // Method to show completion filter dialog
  private void showCompletionFilterDialog() {
    // Implement completion filter dialog
    String[] options = {"Completed", "Not Completed"};
    String selectedOption = (String) JOptionPane.showInputDialog(
        TaskListGUI.this,
        "Choose a completion status:",
        "Filter by Completion",
        JOptionPane.PLAIN_MESSAGE,
        null,
        options,
        options[0]);

    // Filter tasks based on the selected completion status
    if (selectedOption != null) {
      boolean completed = selectedOption.equals("Completed");
      List<Task> filteredTasks = taskController.getTasksByCompletion(completed);

      // Display the filtered tasks
      displayFilteredTasks(filteredTasks);
    }
  }

  // Method to display the filtered tasks
  private void displayFilteredTasks(List<Task> tasks) {
    StringBuilder tasksText = new StringBuilder();
    for (Task task : tasks) {
      tasksText.append(task.toString()).append("\n");
      tasksText.append("\n");
    }
    // Update a JTextArea with the list of tasks
    JTextArea tasksTextArea = new JTextArea(tasksText.toString());
    JScrollPane scrollPane = new JScrollPane(tasksTextArea);
    JOptionPane.showMessageDialog(TaskListGUI.this, scrollPane, "Filtered Tasks", JOptionPane.INFORMATION_MESSAGE);
  }

  // Method to start the confetti animation
  private void startConfettiAnimation() {
    confettiPanel.startAnimation();
    // Adjust the delay as needed
    Timer timer = new Timer(5000, e -> {
      resetAnimation(); // Reset the animation
      getContentPane().add(confettiPanel, BorderLayout.CENTER); // Re-add the confetti panel to the content pane
      revalidate(); // Revalidate the GUI to reflect the changes
      repaint(); // Repaint the GUI
      ((Timer) e.getSource()).stop();
    });
    timer.setRepeats(false);
    timer.start();
  }

  // Method to reset the animation
  public void resetAnimation() {
    confettiPanel.stopAnimation(); // Stop the confetti animation
    confettiPanel.clearConfetti(); // Clear any existing confetti pieces
  }

  public static void main(String[] args) {
    TaskModel taskModel = new TaskModel(LocalDate.now());
    TaskController taskController = new TaskController(taskModel, new TaskView());
    EventController eventController = new EventController(taskModel, new TaskView()); // Correct constructor arguments
    NonEventController nonEventController = new NonEventController(taskModel); // Correct constructor arguments
    SwingUtilities.invokeLater(() -> new TaskListGUI(taskController, eventController, nonEventController));

  }

  public EventController getEventController() {
    return eventController;
  }

  public NonEventController getNonEventController() {
    return nonEventController;
  }

  public void start() {
  }

  public void setController(TaskController eventController) {
  }
}
