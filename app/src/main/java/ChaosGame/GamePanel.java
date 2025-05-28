package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * ChaosGame GamePanel
 * not chatgpt even though it looks like it lol
 *
 */
public class GamePanel extends JPanel {
  // BufferedImage keeps its contents through repaints
  private BufferedImage image;
  private Thread drawThread;
  // Volatile - Makes it so that threads can accurately read the value
  private volatile boolean stopThread;

  public GamePanel(int width, int height) {
    setPreferredSize(new Dimension(width, height));
    setBackground(Color.BLACK);
    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    repaint();
    setVisible(true);
  }

  /**
   * Paints the ChaosShape with the parameters given
   * Uses a BufferedImage to store the screen so it doesn't get cleared in
   * paintComponent
   * Paints on a seperate thread so the window doesn't freeze due to
   * Thread.sleep()
   *
   * I got the main idea from chatgpt but wrote most of the code myself
   *
   * @param shape       The ChaosShape to paint
   * @param iterations  The number of points to paint
   * @param rule        The method to choose each painted Point
   * @param drawPolygon Whether to draw the outer shape of ChaosShape
   * @param color       The color to draw the points in, null for auto
   * @param rotation    The rotation in degrees to turn the resultant points
   *                    around their vertecies
   */
  public void paintShape(ChaosShape shape, int iterations, Function<Point, Point> rule, boolean drawPolygon,
      Color color, double rotation) {
    Graphics2D g = image.createGraphics();
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, image.getWidth(), image.getHeight());

    if (drawPolygon) {
      g.setColor(Color.WHITE);
      g.drawPolygon(shape.polygon);
    }
    g.dispose();
    repaint();

    // Tells the thread to stop and waits, then untoggles the flag adn makes a new
    // thread
    stopThread = true;

    try {
      while (drawThread != null && drawThread.isAlive())
        drawThread.join(50);
    } catch (InterruptedException ex) {
    }

    stopThread = false;

    // new thread so I can call repaint() on the main thread at the same time
    // chatgpt
    drawThread = new Thread(() -> {
      Point current = shape.getRandomInside();
      Graphics2D g2 = image.createGraphics();

      for (int i = 0; i < iterations; i++) {
        current = shape.getNextPoint(current, rule, rotation);

        if (color == null)
          g2.setColor(shape.getPointColor(current));
        else
          g2.setColor(color);

        g2.drawLine(current.x, current.y, current.x, current.y);

        if (i % 100 == 0) {
          SwingUtilities.invokeLater(this::repaint);
        }

        try {
          Thread.sleep(0, 50000);
        } catch (InterruptedException ignored) {
        }
        ;
        if (stopThread)
          break;
      }

      g2.dispose();
      SwingUtilities.invokeLater(this::repaint);
    });

    drawThread.start();
  }

  // Just puts the BufferedImage on the screen
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, null);
  }
}
