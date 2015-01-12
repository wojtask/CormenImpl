package pl.kwojtas.cormenimpl;

public class Util {

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

}
