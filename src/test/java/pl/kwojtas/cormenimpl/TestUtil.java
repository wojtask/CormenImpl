package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.Array;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Fundamental.leq;

public class TestUtil {

    public static <E> void assertArrayEquals(Array<E> expected, Array<E> actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 1; i <= expected.length; i++) {
            assertEquals(expected.at(i), actual.at(i));
        }
    }

    private static <E> void assertArrayContains(Array<E> array, E element) {
        boolean found = false;
        for (int i = 1; i <= array.length && !found; i++) {
            found = array.at(i).equals(element);
        }
        assertTrue(found);
    }

    public static <E> void assertShuffled(Array<E> original, Array<E> shuffled) {
        assertEquals(original.length, shuffled.length);
        for (int i = 1; i <= original.length; i++) {
            assertArrayContains(shuffled, original.at(i));
        }
        for (int i = 1; i <= shuffled.length; i++) {
            assertArrayContains(original, shuffled.at(i));
        }
    }

    public static <E extends Comparable<? super E>> void sortArray(Array<E> array, Comparator<E> comparator) {
        ArrayList<E> arrayList = new ArrayList<>();
        for (int i = 1; i <= array.length; i++) {
            arrayList.add(array.at(i));
        }
        arrayList.sort(comparator);
        for (int i = 1; i <= array.length; i++) {
            array.set(i, arrayList.get(i - 1));
        }
    }

    public static <E extends Comparable<? super E>> void sortArray(Array<E> array) {
        sortArray(array, Comparator.naturalOrder());
    }

    public static <E extends Comparable<? super E>> void assertSorted(Array<E> array) {
        for (int i = 2; i <= array.length; i++) {
            assertTrue(leq(array.at(i - 1), array.at(i)));
        }
    }

    public static <E> void assertSorted(Array<E> array, Comparator<E> comparator) {
        for (int i = 2; i <= array.length; i++) {
            assertTrue(comparator.compare(array.at(i - 1), array.at(i)) <= 0);
        }
    }

}
