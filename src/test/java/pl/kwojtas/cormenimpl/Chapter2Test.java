package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

public class Chapter2Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter2> constructor = Chapter2.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldSortArrayUsingInsertionSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.insertionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayInNonincreasingOrderUsingInsertionSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.nonincreasingInsertionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array, Comparator.<Integer>reverseOrder());
    }

    @Test
    public void shouldFindKeyUsingLinearSearch() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);
        int key = 6;

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(6), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingLinearSearch() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int key = 4;

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @Test
    public void shouldAddTwoNumbersInBinary() {
        // given
        Array<Integer> firstNumberBits = new Array<>(0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1);
        Array<Integer> secondNumberBits = new Array<>(0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1);
        int a = bitsToNumber(firstNumberBits);
        int b = bitsToNumber(secondNumberBits);
        int bitsLength = firstNumberBits.length;

        // when
        Array<Integer> actualSumBits = Chapter2.binaryAdd(firstNumberBits, secondNumberBits);

        // then
        assertNotNull(actualSumBits);
        assertEquals(bitsLength + 1, actualSumBits.length);
        assertEquals(a + b, bitsToNumber(actualSumBits));
    }

    private int bitsToNumber(Array<Integer> bits) {
        int number = 0;
        for (int i = bits.length; i >= 1; i--) {
            number *= 2;
            number += bits.at(i);
        }
        return number;
    }

    @Test
    public void shouldSortArrayUsingSelectionSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.selectionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingMergeSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.mergeSort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldMergeArrayUsingMerge_() {
        // given
        Array<Integer> array = new Array<>(2, 5, 7, 9, 1, 3, 6, 6, 6, 7, 8, 8);
        Array<Integer> original = new Array<>(array);
        int mid = 4;

        // when
        Chapter2.merge_(array, 1, mid, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindKeyUsingRecursiveBinarySearch() {
        // given
        Array<Integer> array = new Array<>(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        Array<Integer> original = new Array<>(array);
        int key = 3;

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingRecursiveBinarySearch() {
        // given
        Array<Integer> array = new Array<>(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        int key = 4;

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNull(actualIndex);
    }

    @Test
    public void shouldFindKeyUsingIterativeBinarySearch() {
        // given
        Array<Integer> array = new Array<>(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        Array<Integer> original = new Array<>(array);
        int key = 3;

        // when
        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingIterativeBinarySearch() {
        // given
        Array<Integer> array = new Array<>(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        int key = 4;

        // when
        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @Test
    public void shouldFindSum() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int sum = 17;

        // when
        boolean sumFound = Chapter2.sumSearch(array, sum);

        // then
        assertTrue(sumFound);
    }

    @Test
    public void shouldNotFindNonexistentSum() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int sum = 18;

        // when
        boolean sumFound = Chapter2.sumSearch(array, sum);

        // then
        assertFalse(sumFound);
    }

    @Test
    public void shouldSortArrayUsingBubbleSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.bubbleSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldEvaluatePolynomialUsingHornersRule() {
        // given
        ZeroBasedIndexedArray<Double> coefficients = new ZeroBasedIndexedArray<>(-1.5, 3.2, 1.6, 3.4, -5.0, 0.0, -1.0, 1.0);
        double x = -2.0;
        double expectedResult = -300.7;
        double delta = 1e-15;

        // when
        double actualResult = Chapter2.horner(coefficients, x);

        // then
        assertEquals(expectedResult, actualResult, delta);
    }

    @Test
    public void shouldEvaluatePolynomialNaively() {
        // given
        ZeroBasedIndexedArray<Double> coefficients = new ZeroBasedIndexedArray<>(-1.5, 3.2, 1.6, 3.4, -5.0, 0.0, -1.0, 1.0);
        double x = -2.0;
        double expectedResult = -300.7;
        double delta = 1e-15;

        // when
        double actualResult = Chapter2.naivePolynomialEvaluation(coefficients, x);

        // then
        assertEquals(expectedResult, actualResult, delta);
    }

    @Test
    public void shouldCountInversions() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        int actualInversions = Chapter2.countInversions(array, 1, array.length);

        // then
        assertInversions(original, actualInversions);
    }

    private void assertInversions(Array<Integer> original, int actualInversions) {
        int expectedInversions = 0;
        for (int i = 1; i <= original.length - 1; i++) {
            for (int j = i + 1; j <= original.length; j++) {
                if (original.at(i) > original.at(j)) {
                    expectedInversions++;
                }
            }
        }
        assertEquals(expectedInversions, actualInversions);
    }

}
