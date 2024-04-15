package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class ConfettiPanel extends JPanel {

  private final List<ConfettiPiece> confettiPieces;
  private final Timer animationTimer;
  private final Random random;
  private static final int ANIMATION_DURATION = 5000; // 5 seconds in milliseconds
  private long animationStartTime;

  public ConfettiPanel() {
    confettiPieces = new ArrayList<>();
    random = new Random();

    animationTimer = new Timer(50, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (animationStartTime == -1) {
          animationStartTime = System.currentTimeMillis();
        }

        long elapsedTime = System.currentTimeMillis() - animationStartTime;
        if (elapsedTime > ANIMATION_DURATION) {
          animationTimer.stop(); // Stop the animation after the specified duration
          confettiPieces.clear(); // Clear the list of confetti pieces
          animationStartTime = -1; // Reset the animation start time
        } else {
          moveConfetti(); // Move the confetti pieces
          repaint(); // Repaint the panel
        }
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    for (ConfettiPiece piece : confettiPieces) {
      g2d.setColor(piece.getColor());
      g2d.fillRect(piece.getX(), piece.getY(), piece.getSize(), piece.getSize());
    }
  }

  public void startAnimation() {
    animationStartTime = System.currentTimeMillis(); // Record the start time of the animation
    // Add confetti pieces to the panel
    for (int i = 0; i < 100; i++) {
      confettiPieces.add(createRandomConfettiPiece());
    }

    animationTimer.start(); // Start the animation timer
  }

  private ConfettiPiece createRandomConfettiPiece() {
    Color color = getRandomColor();
    int size = random.nextInt(10) + 5;
    int x = random.nextInt(getWidth());
    int y = random.nextInt(getHeight());
    return new ConfettiPiece(x, y, size, color);
  }

  private void moveConfetti() {
    for (ConfettiPiece piece : confettiPieces) {
      piece.setY(piece.getY() + piece.getSpeed());
      if (piece.getY() > getHeight()) {
        piece.setY(0); // Reset the confetti piece to the top when it reaches the bottom
      }
    }
  }

  private Color getRandomColor() {
    int r = random.nextInt(256);
    int g = random.nextInt(256);
    int b = random.nextInt(256);
    return new Color(r, g, b);
  }

  public void stopAnimation() {
    animationTimer.stop();
  }

  public void clearConfetti() {
    confettiPieces.clear();
  }

  // Inner class for ConfettiPiece
  private static class ConfettiPiece {
    private final int x;
    private int y;
    private final int size;
    private final Color color;

    // Constructor for ConfettiPiece
    public ConfettiPiece(int x, int y, int size, Color color) {
      this.x = x;
      this.y = y;
      this.size = size;
      this.color = color;
    }

    // Getter methods for ConfettiPiece properties
    public int getX() {
      return x;
    }

    public int getY() {
      return y;
    }

    public int getSize() {
      return size;
    }

    public Color getColor() {
      return color;
    }

    public int getSpeed() {
      return 10;
    }

    public void setY(int y) {
      this.y = y;
    }
  }
}
