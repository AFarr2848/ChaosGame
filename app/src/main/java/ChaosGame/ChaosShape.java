package ChaosGame;
/*
 * Aaron Farrar
 * APCSA Pd 1
 * Chaos Game
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;
import java.util.function.Function;

/**
 * A shape for ChaosGame
 *
 */
public class ChaosShape {
  public Polygon polygon;
  private int radius;
  private Point center;
  private Color[] colorList;

  /**
   * Generates a new regular shape
   *
   * @param sides  The sides of the new shape
   * @param radius THe radius of the new shape
   * @param center The center point of the new shape
   */
  public ChaosShape(int sides, int radius, Point center, double skew) {
    Random r = new Random();
    this.radius = radius;
    this.center = center;

    colorList = new Color[sides];
    for (int i = 0; i < sides; i++) {
      colorList[i] = new Color(Color.HSBtoRGB(r.nextFloat(), 1.0f, 1.0f));
    }

    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];
    for (int i = 0; i < sides; i++) {
      xPoints[i] = center.x + (int) (radius * Math.sin((double) i / sides * 2 * Math.PI));
      yPoints[i] = center.y + (int) (radius * Math.cos((double) i / sides * 2 * Math.PI));
      if (skew < 1) {
        xPoints[i] *= skew;

      }
      if (skew > 1) {
        yPoints[i] *= -skew + 2;
      }
    }
    polygon = new Polygon(xPoints, yPoints, sides);
  }

  /**
   * Gets the next point for ChaosGame by taking the midpoint of parameter
   * 'currentPoint' and a chosen vertex
   *
   * @param currentPoint The point to move from
   * @param pointChooser The <Point, Point> method to choose the next point
   * @return The next point
   */
  public Point getNextPoint(Point currentPoint, Function<Point, Point> pointChooser, double rotation) {
    Point tempPoint = pointChooser.apply(currentPoint);

    Point newPoint = new Point(currentPoint.x + (tempPoint.x - currentPoint.x) / 2,
        currentPoint.y + (tempPoint.y - currentPoint.y) / 2);

    rotatePoint(newPoint, tempPoint, rotation);
    return newPoint;
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
   * Intended to be passed as a Function<Point, Point> to getNextPoint()
   * 
   * @return A random vertex of this shape
   */
  public Point getRandomCorner() {
    Point[] points = getPoints();
    return points[new Random().nextInt(points.length)];
  }

  /**
   * Intended to be passed as a Function<Point, Point> to getNextPoint()
   *
   * @param p Point
   * @return A random vertex that's not the closest one to point p
   */
  public Point getRandomCornerNotClosest(Point p) {
    int minDist = Integer.MAX_VALUE;
    Integer[] distList = new Integer[polygon.npoints];
    for (int i = 0; i < polygon.npoints; i++) {
      distList[i] = (int) p.distance(new Point(polygon.xpoints[i], polygon.ypoints[i]));
      if (distList[i] < minDist)
        minDist = distList[i];
    }
    int r = 0;
    Random rand = new Random();
    for (int i = 0; i < 1000; i++) {
      r = rand.nextInt(distList.length);
      if (distList[r] != minDist)
        return new Point(polygon.xpoints[r], polygon.ypoints[r]);
    }
    return new Point(polygon.xpoints[r], polygon.ypoints[r]);
  }

  /**
   * Intended to be passed as a Function<Point, Point> to getNextPoint()
   *
   * @param p Point
   * @return A random vertex that's not the furthest one from point p
   */
  public Point getRandomCornerNotFurthest(Point p) {
    int maxDist = Integer.MIN_VALUE;
    Integer[] distList = new Integer[polygon.npoints];
    for (int i = 0; i < polygon.npoints; i++) {
      distList[i] = (int) p.distance(new Point(polygon.xpoints[i], polygon.ypoints[i]));
      if (distList[i] > maxDist)
        maxDist = distList[i];
    }
    int r = 0;
    Random rand = new Random();
    for (int i = 0; i < 1000; i++) {
      r = rand.nextInt(distList.length);
      if (distList[r] != maxDist)
        return new Point(polygon.xpoints[r], polygon.ypoints[r]);
    }
    return new Point(polygon.xpoints[r], polygon.ypoints[r]);

  }

  /**
   * Rotates a point around a vertex by an amount of degrees
   *
   * @param p        The point to be rotated
   * @param vertex   The vertex
   * @param rotation The amount of rotation in degrees
   */
  public void rotatePoint(Point p, Point vertex, double rotation) {
    int pointX = p.x;
    int pointY = p.y;
    p.x = (int) (vertex.x + (pointX - vertex.x) * Math.cos(Math.toRadians(rotation))
        - (pointY - vertex.y) * Math.sin(Math.toRadians(rotation)));
    p.y = (int) (vertex.y + (pointX - vertex.x) * Math.sin(Math.toRadians(rotation))
        + (pointY - vertex.y) * Math.cos(Math.toRadians(rotation)));

  }

  /**
   * Gets the color of a point based on how far it is from the center
   *
   * @param p Point
   * @return A Color
   */
  public Color getPointColor(Point p) {
    int red = Math.min(255, (int) (center.distance(p) / radius * 255));
    int blue = Math.min(255, 255 - (int) ((center.distance(p) / radius) * 255));
    blue = blue < 0 ? 255 : blue;
    return new Color(red, 0, blue);

  }

}
