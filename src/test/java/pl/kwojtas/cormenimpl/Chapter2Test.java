package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.ZeroBasedIndexedArray;

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
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter2.insertionSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayInNonincreasingOrderUsingInsertionSort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter2.nonincreasingInsertionSort(array);

        assertShuffled(original, array);
        assertSorted(array, Comparator.reverseOrder());
    }

    @Test
    public void shouldFindKeyUsingLinearSearch() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int key = 6;

        Integer actualIndex = Chapter2.linearSearch(array, key);

        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(6), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingLinearSearch() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int key = 4;

        Integer actualIndex = Chapter2.linearSearch(array, key);

        assertNull(actualIndex);
    }

    @Test
    public void shouldAddTwoNumbersInBinary() {
        Array<Integer> firstNumberBits = Array.of(0, 0, 1, 1, 0, 1, 0, 0, 0, 0, 1);
        Array<Integer> secondNumberBits = Array.of(0, 1, 1, 1, 1, 0, 0, 1, 0, 1, 1);
        int a = bitsToNumber(firstNumberBits);
        int b = bitsToNumber(secondNumberBits);
        int bitsLength = firstNumberBits.length;

        Array<Integer> actualSumBits = Chapter2.binaryAdd(firstNumberBits, secondNumberBits);

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
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter2.selectionSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingMergeSort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter2.mergeSort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldMergeArrayUsingMerge_() {
        Array<Integer> array = Array.of(2, 5, 7, 9, 1, 3, 6, 6, 6, 7, 8, 8);
        Array<Integer> original = Array.copyOf(array);
        int mid = 4;

        Chapter2.merge_(array, 1, mid, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindKeyUsingRecursiveBinarySearch() {
        Array<Integer> array = Array.of(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        Array<Integer> original = Array.copyOf(array);
        int key = 3;

        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingRecursiveBinarySearch() {
        Array<Integer> array = Array.of(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        int key = 4;

        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        assertNull(actualIndex);
    }

    @Test
    public void shouldFindKeyUsingIterativeBinarySearch() {
        Array<Integer> array = Array.of(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        Array<Integer> original = Array.copyOf(array);
        int key = 3;

        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(Integer.valueOf(key), original.at(actualIndex));
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingIterativeBinarySearch() {
        Array<Integer> array = Array.of(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        int key = 4;

        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        assertNull(actualIndex);
    }

    @Test
    public void shouldFindSum() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int sum = 17;

        boolean sumFound = Chapter2.sumSearch(array, sum);

        assertTrue(sumFound);
    }

    @Test
    public void shouldNotFindNonexistentSum() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 1, 6, 6, 3, 1, 7, 8);
        int sum = 18;

        boolean sumFound = Chapter2.sumSearch(array, sum);

        assertFalse(sumFound);
    }

    @Test
    public void shouldSortArrayUsingBubbleSort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter2.bubbleSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldEvaluatePolynomialUsingHornersRule() {
        ZeroBasedIndexedArray<Double> coefficients = ZeroBasedIndexedArray.of(-1.5, 3.2, 1.6, 3.4, -5.0, 0.0, -1.0, 1.0);
        double x = -2.0;
        double expectedResult = -300.7;
        double delta = 1e-15;

        double actualResult = Chapter2.horner(coefficients, x);

        assertEquals(expectedResult, actualResult, delta);
    }

    @Test
    public void shouldEvaluatePolynomialNaively() {
        ZeroBasedIndexedArray<Double> coefficients = ZeroBasedIndexedArray.of(-1.5, 3.2, 1.6, 3.4, -5.0, 0.0, -1.0, 1.0);
        double x = -2.0;
        double expectedResult = -300.7;
        double delta = 1e-15;

        double actualResult = Chapter2.naivePolynomialEvaluation(coefficients, x);

        assertEquals(expectedResult, actualResult, delta);
    }

    @Test
    public void shouldCountInversions() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        int actualInversions = Chapter2.countInversions(array, 1, array.length);

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
