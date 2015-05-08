package pl.kwojtas.cormenimpl.util;

/**
 * Implements an interval.
 */
public class Interval {

    /**
     * The lower bound of the interval.
     */
    public double a;

    /**
     * The upper bound of the interval.
     */
    public double b;

    /**
     * Creates an interval from a given lower bound and upper bound.
     *
     * @param a the lower bound of the new interval
     * @param b the upper bound of the new interval
     */
    public Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

}
