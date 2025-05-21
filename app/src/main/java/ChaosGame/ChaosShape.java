package ChaosGame;

import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class ChaosShape {
  private int shapeSides;
  public Polygon polygon;
  private int radius;

  public ChaosShape(int sides, int radius, Point center) {
    System.out.print(sides + ", " + radius + ", " + center);
    shapeSides = sides;
    this.radius = radius;
    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];
    for (int i = 0; i < sides; i++) {
      xPoints[i] = center.x + (int) (radius * Math.sin((double) i / sides * 2 * Math.PI));
      yPoints[i] = center.y + (int) (radius * Math.cos((double) i / sides * 2 * Math.PI));
      System.out.println("Point " + i + ": " + xPoints[i] + ", " + yPoints[i]);
    }
    polygon = new Polygon(xPoints, yPoints, sides);
  }

  public Point getNextPoint(Point currentPoint, Function<Point, Point> pointChooser) {
    Point tempPoint = pointChooser.apply(currentPoint);

    return new Point(currentPoint.x + (tempPoint.x - currentPoint.x) / 2,
        currentPoint.y + (tempPoint.y - currentPoint.y) / 2);
  }

  public Point getRandomInside() {
    Point currentPoint = new Point((int) (Math.random() * radius * 2), (int) (Math.random() * radius * 2));
    while (!polygon.contains(currentPoint))
      currentPoint = new Point((int) (Math.random() * radius * 2), (int) (Math.random() * radius * 2));
    return currentPoint;
  }

  public Point[] getPoints() {
    Point[] points = new Point[polygon.npoints];
    for (int i = 0; i < polygon.npoints; i++) {
      points[i] = new Point(polygon.xpoints[i], polygon.ypoints[i]);
    }
    return points;
  }

  public Point getRandomCorner() {
    Point[] points = getPoints();
    return points[new Random().nextInt(points.length)];

  }

}
