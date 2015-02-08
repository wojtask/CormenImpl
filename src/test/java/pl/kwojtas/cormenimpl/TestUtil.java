package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.util.Util.leq;

public class TestUtil {

    public static <T> void assertArrayEquals(Array<T> expected, Array<T> actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 1; i <= expected.length; i++) {
            assertEquals(expected.at(i), actual.at(i));
        }
    }

    private static <T> void assertArrayContains(Array<T> array, T element) {
        boolean found = false;
        for (int i = 1; i <= array.length && !found; i++) {
            found = array.at(i).equals(element);
        }
        assertTrue(found);
    }

    public static <T> void assertShuffled(Array<T> original, Array<T> shuffled) {
        assertEquals(original.length, shuffled.length);
        for (int i = 1; i <= original.length; i++) {
            assertArrayContains(shuffled, original.at(i));
        }
        for (int i = 1; i <= shuffled.length; i++) {
            assertArrayContains(original, shuffled.at(i));
        }
    }

    public static <T extends Comparable> void sortArray(Array<T> array, Comparator<T> comparator) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 1; i <= array.length; i++) {
            arrayList.add(array.at(i));
        }
        arrayList.sort(comparator);
        for (int i = 1; i <= array.length; i++) {
            array.set(i, arrayList.get(i - 1));
        }
    }

    public static <T extends Comparable> void sortArray(Array<T> array) {
        sortArray(array, Comparator.<T>naturalOrder());
    }

    public static <T extends Comparable> void assertSorted(Array<T> array) {
        for (int i = 2; i <= array.length; i++) {
            assertTrue(leq(array.at(i - 1), array.at(i)));
        }
    }

    public static <T> void assertSorted(Array<T> array, Comparator<T> comparator) {
        for (int i = 2; i <= array.length; i++) {
            assertTrue(comparator.compare(array.at(i - 1), array.at(i)) <= 0);
        }
    }

}
