package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

/**
 * A shape for ChaosGame
 *
 */
public class ChaosShape {
  private int shapeSides;
  public Polygon polygon;
  private int radius;
  private Color[] colorList;

  /**
   * Generates a new regular shape
   *
   * @param sides  The sides of the new shape
   * @param radius THe radius of the new shape
   * @param center The center point of the new shape
   */
  public ChaosShape(int sides, int radius, Point center) {
    Random r = new Random();
    shapeSides = sides;
    this.radius = radius;

    colorList = new Color[sides];
    for (int i = 0; i < sides; i++) {
      colorList[i] = new Color(Color.HSBtoRGB(r.nextFloat(), 1.0f, 1.0f));
    }

    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];
    for (int i = 0; i < sides; i++) {
      xPoints[i] = center.x + (int) (radius * Math.sin((double) i / sides * 2 * Math.PI));
      yPoints[i] = center.y + (int) (radius * Math.cos((double) i / sides * 2 * Math.PI));
    }
    polygon = new Polygon(xPoints, yPoints, sides);
  }

  /**
   * Gets the next point for ChaosGame by taking the midpoint of parameter
   * 'currentPoint' and a chosen vertex
   * Also gets the color of the point based on the vertex chosen
   *
   * @param currentPoint The point to move from
   * @param pointChooser The <Point, Point> method to choose the next point
   * @return The next ColorPoint
   */
  public ColorPoint getNextColorPoint(Point currentPoint, Function<Point, Point> pointChooser) {
    Point tempPoint = pointChooser.apply(currentPoint);

    return new ColorPoint(currentPoint.x + (tempPoint.x - currentPoint.x) / 2,
        currentPoint.y + (tempPoint.y - currentPoint.y) / 2,
        colorList[Arrays.asList(getPoints()).indexOf(tempPoint)]);
  }

  /**
   * Gets a random point inside of this object's shape
   *
   * @return The random point
   */
  public Point getRandomInside() {
    Point currentPoint = new Point((int) (Math.random() * radius * 2), (int) (Math.random() * radius * 2));
    while (!polygon.contains(currentPoint))
      currentPoint = new Point((int) (Math.random() * radius * 2), (int) (Math.random() * radius * 2));
    return currentPoint;
  }

  /**
   * @return A list of this shape's verticies
   */
  private Point[] getPoints() {
    Point[] points = new Point[polygon.npoints];
    for (int i = 0; i < polygon.npoints; i++) {
      points[i] = new Point(polygon.xpoints[i], polygon.ypoints[i]);
    }
    return points;
  }

  /**
   * @return A random vertex of this shape
   */
  public Point getRandomCorner() {
    Point[] points = getPoints();
    return points[new Random().nextInt(points.length)];

  }

  /**
   * A class that's just a Point with a Color
   */
  public class ColorPoint extends Point {
    public Color color;

    public ColorPoint(int x, int y, Color color) {
      super(x, y);
      this.color = color;
    }
  }

}
