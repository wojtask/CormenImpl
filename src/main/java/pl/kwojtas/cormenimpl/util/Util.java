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
     * @param <T> the type of {@code b}
     * @return {@code true} if {@code a < b}, or {@code false} otherwise
     */
    public static <T> boolean less(Comparable<? super T> a, T b) {
        return a.compareTo(b) < 0;
    }

    /**
     * Checks if one value is less than or equal to another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <T> the type of {@code b}
     * @return {@code true} if {@code a <= b}, or {@code false} otherwise
     */
    public static <T> boolean leq(Comparable<? super T> a, T b) {
        return a.compareTo(b) <= 0;
    }

    /**
     * Checks if one value is greater than another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <T> the type of {@code b}
     * @return {@code true} if {@code a > b}, or {@code false} otherwise
     */
    public static <T> boolean greater(Comparable<? super T> a, T b) {
        return a.compareTo(b) > 0;
    }

    /**
     * Checks if one value is greater than or equal to another value.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <T> the type of {@code b}
     * @return {@code true} if {@code a >= b}, or {@code false} otherwise
     */
    public static <T> boolean geq(Comparable<? super T> a, T b) {
        return a.compareTo(b) >= 0;
    }

    /**
     * Returns the minimum of two values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <T> the type of the values
     * @return the minimum of {@code a} and {@code b}
     */
    public static <T extends Comparable<? super T>> T min(T a, T b) {
        return leq(a, b) ? a : b;
    }

    /**
     * Returns the maximum of two values.
     *
     * @param a   the first value
     * @param b   the second value
     * @param <T> the type of the values
     * @return the maximum of {@code a} and {@code b}
     */
    public static <T extends Comparable<? super T>> T max(T a, T b) {
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
