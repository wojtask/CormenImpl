package pl.kwojtas.cormenimpl;

import java.util.Random;

public class Util {

    private static Random rand = new Random(System.currentTimeMillis());

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean leq(Comparable v, Comparable w) {
        return v.compareTo(w) <= 0;
    }

    public static boolean greater(Comparable v, Comparable w) {
        return v.compareTo(w) > 0;
    }

    public static boolean geq(Comparable v, Comparable w) {
        return v.compareTo(w) >= 0;
    }

    public static int random() {
        return rand.nextBoolean() ? 1 : 0;
    }

}
