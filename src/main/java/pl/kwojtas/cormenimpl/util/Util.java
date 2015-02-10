package pl.kwojtas.cormenimpl.util;

import java.util.Random;

public class Util {

    private static Random rand = new Random(System.currentTimeMillis());

    public static boolean less(Comparable a, Comparable b) {
        return a.compareTo(b) < 0;
    }

    public static boolean leq(Comparable a, Comparable b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean greater(Comparable a, Comparable b) {
        return a.compareTo(b) > 0;
    }

    public static boolean geq(Comparable a, Comparable b) {
        return a.compareTo(b) >= 0;
    }

    public static <T extends Comparable> T min(T a, T b) {
        return leq(a, b) ? a : b;
    }

    public static <T extends Comparable> T max(T a, T b) {
        return geq(a, b) ? a : b;
    }

    public static int random() {
        return rand.nextBoolean() ? 1 : 0;
    }

    public static int ceil(int a, int b) {
        return a / b + (a % b == 0 ? 0 : 1);
    }

}
