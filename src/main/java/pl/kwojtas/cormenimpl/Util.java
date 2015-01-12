package pl.kwojtas.cormenimpl;

import java.util.Comparator;

public class Util {

    public static boolean less(Comparator c, Object v, Object w) {
        return (c.compare(v, w) < 0);
    }

    public static boolean less(Object v, Object w) {
        return less(Comparator.naturalOrder(), v, w);
    }

    public static boolean leq(Comparator c, Object v, Object w) {
        return (c.compare(v, w) <= 0);
    }

    public static boolean leq(Object v, Object w) {
        return leq(Comparator.naturalOrder(), v, w);
    }

    public static boolean greater(Comparator c, Object v, Object w) {
        return (c.compare(v, w) > 0);
    }

    public static boolean greater(Object v, Object w) {
        return greater(Comparator.naturalOrder(), v, w);
    }

    public static boolean geq(Comparator c, Object v, Object w) {
        return (c.compare(v, w) >= 0);
    }

    public static boolean geq(Object v, Object w) {
        return geq(Comparator.naturalOrder(), v, w);
    }

}
