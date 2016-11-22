package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.Point2D;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

public class Chapter8Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter8> constructor = Chapter8.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldSortArrayUsingCountingSort() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        Array<Integer> actualSorted = Array.ofLength(array.length);
        int boundary = 6;

        Chapter8.countingSort(array, actualSorted, boundary);

        assertShuffled(original, actualSorted);
        assertSorted(actualSorted);
    }

    @Test
    public void shouldSortArrayUsingNonStableCountingSort() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        Array<Integer> actualSorted = Array.ofLength(array.length);
        int boundary = 6;

        Chapter8.nonStableCountingSort(array, actualSorted, boundary);

        assertShuffled(original, actualSorted);
        assertSorted(actualSorted);
    }

    @Test
    public void shouldCountNumbersInRangeBeingEntirelyOutsideArray() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 6;
        int a = -2;
        int b = -1;

        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        assertElementsInRange(original, a, b, actualInRange);
    }

    @Test
    public void shouldCountNumbersInRangeBeingEntirelyInsideArray() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 6;
        int a = 1;
        int b = 5;

        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        assertElementsInRange(original, a, b, actualInRange);
    }

    @Test
    public void shouldCountNumbersInRangeWithLowerBoundOutsideArray() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 6;
        int a = -2;
        int b = 3;

        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        assertElementsInRange(original, a, b, actualInRange);
    }

    @Test
    public void shouldCountNumbersInRangeWithUpperBoundOutsideArray() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 6;
        int a = 3;
        int b = 12;

        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        assertElementsInRange(original, a, b, actualInRange);
    }

    @Test
    public void shouldCountNumbersInRangeWithBoundsOutsideArray() {
        Array<Integer> array = Array.of(6, 0, 2, 0, 1, 3, 4, 6, 1, 3, 2);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 6;
        int a = -4;
        int b = 16;

        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        assertElementsInRange(original, a, b, actualInRange);
    }

    private void assertElementsInRange(Array<Integer> originalArray, int a, int b, int actualInRange) {
        int expectedInRange = 0;
        for (int i = 1; i <= originalArray.length; i++) {
            if (a <= originalArray.at(i) && originalArray.at(i) <= b) {
                expectedInRange++;
            }
        }
        assertEquals(expectedInRange, actualInRange);
    }

    @Test
    public void shouldSortArrayUsingRadixSort() {
        Array<Integer> array = Array.of(24015, 44036, 14014, 62027, 55033, 19012, 63032);
        Array<Integer> original = Array.copyOf(array);
        int digits = 5;

        Chapter8.radixSort(array, digits);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortNNumbersLessThanNSquare() {
        Array<Integer> array = Array.of(15, 56, 25, 66, 23, 92, 2, 45, 7, 39);
        Array<Integer> original = Array.copyOf(array);

        Chapter8.lessThanSquareSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingBucketSort() {
        Array<Double> array = Array.of(.15, .92, .56, .25, .66, .23, .9, .2, .45, .7, .39, .99, .3, .01, .33, .91, .65, .33, .21, .67, .16, .22);
        Array<Double> original = Array.copyOf(array);

        Chapter8.bucketSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortPointsInUnitCircle() {
        Array<Point2D> array = Array.of(new Point2D(.15, .79), new Point2D(.92, .16), new Point2D(.56, .06), new Point2D(.25, .33),
                new Point2D(.66, .15), new Point2D(.23, .81), new Point2D(.69, .72), new Point2D(.2, .37), new Point2D(.45, .88),
                new Point2D(.7, .07), new Point2D(.39, .55), new Point2D(.99, .04), new Point2D(.3, .49), new Point2D(.01, .68),
                new Point2D(.33, .08), new Point2D(.91, .4));
        Array<Point2D> original = Array.copyOf(array);

        Chapter8.pointsSort(array);

        assertShuffled(original, array);
        assertSorted(array, (p1, p2) -> {
            double dist1 = p1.x * p1.x + p1.y * p1.y;
            double dist2 = p2.x * p2.x + p2.y * p2.y;
            return dist1 < dist2 ? -1 : dist1 > dist2 ? 1 : 0;
        });
    }

    @Test
    public void shouldSortArrayUsingBitwiseSort() {
        Array<Integer> array = Array.of(1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0);
        Array<Integer> original = Array.copyOf(array);

        Chapter8.bitwiseSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingCountingSortInPlace() {
        Array<Integer> array = Array.of(7, 1, 3, 1, 2, 4, 5, 7, 2, 4, 3);
        Array<Integer> original = Array.copyOf(array);
        int boundary = 7;

        Chapter8.countingSortInPlace(array, boundary);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortVariableLengthIntegers() {
        Array<Integer> array = Array.of(235, -510207, 455, 0, 90199239, 317, 63534, -102, 301, 42342352, -100234, -231, -331, -66, 1, 3002, -1123581321);
        Array<Integer> original = Array.copyOf(array);

        Chapter8.variableLengthIntegersSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortVariableLengthNonnegativeIntegers() {
        Array<Integer> array = Array.of(44456, 23, 86734563, 222414667, 3, 235, 5666642, 3425, 80033, 234252566, 11, 900, 559311146, 1123451255, 3, 2341);
        Array<Integer> original = Array.copyOf(array);

        Chapter8.variableLengthIntegersSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortVariableLengthStrings() {
        Array<String> array = Array.of("lorem", "ipsum", "dolor", "sit", "amet", "consectetur", "adipiscing", "elit", "sed", "do",
                "eiusmod", "tempor", "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua");
        Array<String> original = Array.copyOf(array);

        Chapter8.variableLengthStringsSort(array, 1);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldGroupJugsUsingJugsGroup() {
        Array<Double> jugs1 = Array.of(5.0, 7.0, 9.0, 2.0, 6.0, 8.0, 6.0, 6.0, 3.0, 1.0, 7.0, 8.0);
        Array<Double> jugs2 = Array.of(6.0, 5.0, 9.0, 7.0, 3.0, 8.0, 2.0, 6.0, 8.0, 6.0, 7.0, 1.0);
        Array<Double> originalJugs1 = Array.copyOf(jugs1);
        Array<Double> originalJugs2 = Array.copyOf(jugs2);

        Chapter8.jugsGroup(jugs1, jugs2);

        assertShuffled(originalJugs1, jugs1);
        assertShuffled(originalJugs2, jugs2);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @Test
    public void shouldGroupJugsUsingJugsSort() {
        Array<Double> jugs1 = Array.of(5.0, 7.0, 9.0, 2.0, 6.0, 8.0, 6.0, 6.0, 3.0, 1.0, 7.0, 8.0);
        Array<Double> jugs2 = Array.of(6.0, 5.0, 9.0, 7.0, 3.0, 8.0, 2.0, 6.0, 8.0, 6.0, 7.0, 1.0);
        Array<Double> originalJugs1 = Array.copyOf(jugs1);
        Array<Double> originalJugs2 = Array.copyOf(jugs2);

        Chapter8.jugsMatch(jugs1, jugs2, 1, jugs1.length);

        assertShuffled(originalJugs1, jugs1);
        assertShuffled(originalJugs2, jugs2);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @Test
    public void shouldRearrangeArrayByAverages() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int k = 6;

        Chapter8.averageSort(array, k, 1, array.length);

        assertShuffled(original, array);
        for (int i = 1; i <= array.length - k; i++) {
            assertTrue(array.at(i) <= array.at(i + k));
        }
    }

}
