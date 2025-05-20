package ChaosGame;

import java.awt.Point;
import java.awt.Polygon;

public class ChaosShape {
  private int shapeSides;
  public Polygon polygon;

  public ChaosShape(int sides, int radius) {
    shapeSides = sides;
    int[] xPoints = new int[sides];
    int[] yPoints = new int[sides];
    for (int i = 0; i < sides; i++) {
      xPoints[i] = (int) (radius * Math.sin((double) i / sides * 2 * Math.PI));
      yPoints[i] = (int) (radius * Math.cos((double) i / sides * 2 * Math.PI));
      System.out.println("Point " + i + ": " + xPoints[i] + ", " + yPoints[i]);
    }
    polygon = new Polygon(xPoints, yPoints, sides);
  }

  public Point[] getPoints() {
    Point[] points = new Point[polygon.npoints];
    for (int i = 0; i < polygon.npoints; i++) {
      points[i] = new Point(polygon.xpoints[i], polygon.ypoints[i]);
    }
    return points;
  }

}
