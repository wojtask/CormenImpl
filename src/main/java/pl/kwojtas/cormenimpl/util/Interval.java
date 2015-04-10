package pl.kwojtas.cormenimpl.util;

/**
 * Implements an interval.
 */
public class Interval {

    /**
     * The beginning of the interval.
     */
    public double a;

    /**
     * The end of the interval.
     */
    public double b;

    /**
     * Creates an interval from given beginning and end.
     *
     * @param a the beginning of the new interval
     * @param b the end of the new interval
     */
    public Interval(double a, double b) {
        this.a = a;
        this.b = b;
    }

}
