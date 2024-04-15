package org.example;

/**
 * Enumeration representing task priorities.
 */
public enum Priority {
  /**
   * Represents high priority tasks.
   */
  RED,

  /**
   * Represents medium priority tasks.
   */
  YELLOW,

  /**
   * Represents low priority tasks.
   */
  GREEN;

  /**
   * Returns the string representation of the priority.
   * @return The name of the enum constant.
   */
  @Override
  public String toString() {
    return name();
  }
}
