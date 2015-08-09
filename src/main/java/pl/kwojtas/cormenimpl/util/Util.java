package pl.kwojtas.cormenimpl.util;

import java.util.Random;

/**
 * The utility class containing useful simple procedures.
 */
public class Util {

    private Util() {
    }

    private static Random rand = new Random(System.currentTimeMillis());

    /**
     * Checks if one value is less than another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of {@code b}
     * @return {@code true} if {@code a < b}, or {@code false} otherwise
     */
    public static <E> boolean less(Comparable<? super E> a, E b) {
        return a.compareTo(b) < 0;
    }

    /**
     * Checks if one value is less than or equal to another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of {@code b}
     * @return {@code true} if {@code a <= b}, or {@code false} otherwise
     */
    public static <E> boolean leq(Comparable<? super E> a, E b) {
        return a.compareTo(b) <= 0;
    }

    /**
     * Checks if one value is greater than another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of {@code b}
     * @return {@code true} if {@code a > b}, or {@code false} otherwise
     */
    public static <E> boolean greater(Comparable<? super E> a, E b) {
        return a.compareTo(b) > 0;
    }

    /**
     * Checks if one value is greater than or equal to another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of {@code b}
     * @return {@code true} if {@code a >= b}, or {@code false} otherwise
     */
    public static <E> boolean geq(Comparable<? super E> a, E b) {
        return a.compareTo(b) >= 0;
    }

    /**
     * Returns the minimum of two values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of the values
     * @return the minimum of {@code a} and {@code b}
     */
    public static <E extends Comparable<? super E>> E min(E a, E b) {
        return leq(a, b) ? a : b;
    }

    /**
     * Returns the maximum of two values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <E> the type of the values
     * @return the maximum of {@code a} and {@code b}
     */
    public static <E extends Comparable<? super E>> E max(E a, E b) {
        return geq(a, b) ? a : b;
    }

    /**
     * Returns a bit at random.
     *
     * @return {@code 0} or {@code 1} at random
     */
    public static int random() {
        return rand.nextBoolean() ? 1 : 0;
    }

    /**
     * Returns the ceiling of a quotient.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the ceiling of {@code a / b}
     */
    public static int ceil(int a, int b) {
        return a / b + (a % b == 0 ? 0 : 1);
    }

}
