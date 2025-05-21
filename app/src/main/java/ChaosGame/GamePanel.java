package ChaosGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Point;
import java.util.function.Function;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
  private ChaosShape shape;
  private int iterations;
  private Function<Point, Point> rule;
  private boolean drawPolygon;

  public GamePanel() {
    setPreferredSize(new Dimension(500, 500));
    setBackground(Color.BLACK);
    repaint();
    setVisible(true);
  }

  public void paintShape(ChaosShape shape, int iterations, Function<Point, Point> rule, boolean drawPolygon) {
    this.shape = shape;
    this.iterations = iterations;
    this.rule = rule;
    this.drawPolygon = drawPolygon;
    repaint();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    if (drawPolygon)
      g.drawPolygon(shape.polygon);
    if (iterations > 0) {
      Point currentPoint = shape.getRandomInside();
      Point[] pointList = new Point[iterations];

      for (int i = 0; i < iterations; i++) {
        pointList[i] = shape.getNextPoint(currentPoint, rule);
        g.drawLine(pointList[i].x, pointList[i].y, pointList[i].x, pointList[i].y);
        currentPoint = pointList[i];

      }

    }
  }
}
