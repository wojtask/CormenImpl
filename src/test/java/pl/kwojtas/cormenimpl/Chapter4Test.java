package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.Array;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.sortArray;

public class Chapter4Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter4> constructor = Chapter4.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldFindMissingInteger() {
        Array<Integer> array = new Array<>(12, 1, 6, 11, 4, 3, 0, 10, 13, 7, 5, 2, 9);
        Array<Integer> original = new Array<>(array);

        Integer actualMissingInteger = Chapter4.findMissingInteger(array);

        assertNotNull(actualMissingInteger);
        sortArray(original, Comparator.naturalOrder());
        int i = 1;
        while (i <= original.length && original.at(i) == i - 1) {
            i++;
        }
        assertEquals(Integer.valueOf(i - 1), actualMissingInteger);
        while (i <= original.length) {
            assertEquals(Integer.valueOf(i), original.at(i));
            i++;
        }
    }

    @Test
    public void shouldGetLeftmostMinimaIndicesOfMongeArray() {
        Array<Array<Double>> mongeArray = new Array<>(
                new Array<>(10.0, 17.0, 13.0, 28.0, 23.0),
                new Array<>(17.0, 22.0, 16.0, 29.0, 23.0),
                new Array<>(24.0, 28.0, 22.0, 34.0, 24.0),
                new Array<>(11.0, 13.0, 6.0, 17.0, 7.0),
                new Array<>(45.0, 44.0, 32.0, 37.0, 23.0),
                new Array<>(36.0, 33.0, 19.0, 21.0, 6.0),
                new Array<>(75.0, 66.0, 51.0, 53.0, 34.0)
        );
        Array<Array<Double>> original = new Array<>(mongeArray);

        Array<Integer> actualLeftmostMinimaIndices = Chapter4.mongeLeftmostMinimaIndices(mongeArray);

        assertNotNull(actualLeftmostMinimaIndices);
        assertMinimaIndices(original, actualLeftmostMinimaIndices);
    }

    private void assertMinimaIndices(Array<Array<Double>> originalMongeArray, Array<Integer> actualLeftmostMinimaIndices) {
        int columns = originalMongeArray.at(1).length;
        for (int i = 1; i <= actualLeftmostMinimaIndices.length; i++) {
            int actualLeftmostMinimumIndex = actualLeftmostMinimaIndices.at(i);
            assertTrue(1 <= actualLeftmostMinimumIndex && actualLeftmostMinimumIndex <= columns);
            for (int j = 1; j <= actualLeftmostMinimumIndex - 1; j++) {
                assertTrue(originalMongeArray.at(i).at(actualLeftmostMinimumIndex) < originalMongeArray.at(i).at(j));
            }
        }
    }

}
