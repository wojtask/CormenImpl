package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a point in a 2-dimensional space.
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
     * Creates a point of given coordinates.
     *
     * @param x the first coordinate
     * @param y the second coordinate
     */
    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
