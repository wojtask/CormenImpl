package pl.kwojtas.cormenimpl.util;

/**
 * Implements a point in 2-dimensional space.
 */
public class Point2D {

    /**
     * The first coordinate of the point.
     */
    public double x;

    /**
     * The second coordinate of the point.
     */
    public double y;

    /**
     * Creates a point from given coordinates.
     *
     * @param x the first coordinate
     * @param y the second coordinate
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
