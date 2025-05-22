package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.function.Function;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
  private ChaosShape shape;
  private int iterations;
  private Function<Point, Point> rule;
  private boolean drawPolygon;
  private boolean customColor;
  private Color color;

  public GamePanel() {
    setPreferredSize(new Dimension(500, 500));
    setBackground(Color.BLACK);
    repaint();
    setVisible(true);
  }

  /**
   * Paints the ChaosShape with the parameters given
   *
   * @param shape       The ChaosShape to paint
   * @param iterations  The number of points to paint
   * @param rule        The method to choose each painted Point
   * @param drawPolygon Whether to draw the outer shape of ChaosShape
   */
  public void paintShape(ChaosShape shape, int iterations, Function<Point, Point> rule, boolean drawPolygon) {
    this.shape = shape;
    this.iterations = iterations;
    this.rule = rule;
    this.drawPolygon = drawPolygon;
    this.customColor = false;
    repaint();
  }

  /**
   * Paints the ChaosShape with the parameters given
   *
   * @param shape       The ChaosShape to paint
   * @param iterations  The number of points to paint
   * @param rule        The method to choose each painted Point
   * @param drawPolygon Whether to draw the outer shape of ChaosShape
   * @param color       The color to draw the points in
   */
  public void paintShape(ChaosShape shape, int iterations, Function<Point, Point> rule, boolean drawPolygon,
      Color color) {
    this.shape = shape;
    this.iterations = iterations;
    this.rule = rule;
    this.drawPolygon = drawPolygon;
    this.color = color;
    this.customColor = true;
    repaint();
  }

  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);
    if (drawPolygon)
      g.drawPolygon(shape.polygon);
    if (iterations > 0) {
      Point currentPoint = shape.getRandomInside();
      ChaosShape.ColorPoint[] pointList = new ChaosShape.ColorPoint[iterations];

      for (int i = 0; i < iterations; i++) {
        pointList[i] = shape.getNextColorPoint(currentPoint, rule);
        if (!customColor)
          g.setColor(pointList[i].color);
        else
          g.setColor(color);
        g.drawLine(pointList[i].x, pointList[i].y, pointList[i].x, pointList[i].y);
        currentPoint = pointList[i];

      }

    }
  }
}
