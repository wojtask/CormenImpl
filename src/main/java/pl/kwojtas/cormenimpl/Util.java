package pl.kwojtas.cormenimpl;

import java.util.Comparator;

public class Util {

    public static boolean less(Comparator c, Object v, Object w) {
        return (c.compare(v, w) < 0);
    }

    public static boolean less(Object v, Object w) {
        return less(Comparator.naturalOrder(), v, w);
    }

}
