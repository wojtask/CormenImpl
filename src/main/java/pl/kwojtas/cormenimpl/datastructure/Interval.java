package pl.kwojtas.cormenimpl.datastructure;

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
     * Creates an interval from given lower and upper bound.
     *
     * @param a the lower bound of the new interval
     * @param b the upper bound of the new interval
     * @throws IllegalStateException if {@code a > b}
     */
    public Interval(double a, double b) {
        if (a > b) {
            throw new IllegalStateException("Lower bound greater than upper bound");
        }
        this.a = a;
        this.b = b;
    }

}
