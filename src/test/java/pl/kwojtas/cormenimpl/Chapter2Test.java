package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.util.Util.greater;

@RunWith(DataProviderRunner.class)
public class Chapter2Test {

    @DataProvider
    public static Object[][] provideDataForSorting() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23)},
                {new Array<>("aaa","eee","ccc","ddd","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingInsertionSort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter2.insertionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayInNonincreasingOrderUsingInsertionSort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter2.nonincreasingInsertionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array, Comparator.<T>reverseOrder());
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulLinearSearch() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 6},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 8},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3},
                {new Array<>("aaa","bbb","aaa","ccc"), "ccc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulLinearSearch")
    public <T> void shouldFindKeyUsingLinearSearch(Array<T> array, T key) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertTrue(original.at(actualIndex).equals(key));
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulLinearSearch() {
        return new Object[][]{
                {new Array<>(34), 35},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new Array<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulLinearSearch")
    public <T> void shouldNotFindKeyUsingLinearSearch(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @DataProvider
    public static Object[][] provideDataForBinaryAdd() {
        return new Object[][]{
                {new Array<>(0), new Array<>(0)},
                {new Array<>(1), new Array<>(0)},
                {new Array<>(0), new Array<>(1)},
                {new Array<>(1), new Array<>(1)},
                {new Array<>(0,0,1,1,0,1,0,0,0,0,1), new Array<>(0,1,1,1,1,0,0,1,0,1,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForBinaryAdd")
    public void shouldAddTwoNumbersInBinary(Array<Integer> firstNumberBits, Array<Integer> secondNumberBits) {
        // given
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
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingSelectionSort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter2.selectionSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForMergeSort() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(9,8,8,7,7,6,6,6,5,3,2,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForMergeSort")
    public void shouldSortArrayUsingMergeSort(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter2.mergeSort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForMergingUsingMerge_() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(5,1), 1},
                {new Array<>(2,5,7,9,1,3,6,6,6,7,8,8), 4},
                {new Array<>(1,2,3,5,6,7,9,6,6,7,8,8), 7},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 12},
                {new Array<>(-2.75,2.23,3.14,-0.53,2.55), 3},
                {new Array<>("aaa","eee","bbb","ccc","ddd"), 2}
        };
    }

    @Test
    @UseDataProvider("provideDataForMergingUsingMerge_")
    public <T extends Comparable> void shouldMergeArrayUsingMerge_(Array<T> array, int mid) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter2.merge_(array, 1, mid, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulBinarySearch() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 6},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 3},
                {new Array<>(-4.5,-2.3,-2.2,-0.1,0.6,2.2,9.5), 2.2},
                {new Array<>("aaa","aaa","bbb","ccc"), "aaa"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulBinarySearch")
    public <T extends Comparable> void shouldFindKeyUsingRecursiveBinarySearch(Array<T> array, T key) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertTrue(original.at(actualIndex).equals(key));
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulBinarySearch() {
        return new Object[][]{
                {new Array<>(34), 35},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 4},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 0},
                {new Array<>(-4.5,-2.3,-2.2,-0.1,0.6,2.2,9.5), 0.0},
                {new Array<>("aaa","aaa","bbb","ccc"), "abc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulBinarySearch")
    public <T extends Comparable> void shouldNotFindKeyUsingRecursiveBinarySearch(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNull(actualIndex);
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulBinarySearch")
    public <T extends Comparable> void shouldFindKeyUsingIterativeBinarySearch(Array<T> array, T key) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertTrue(original.at(actualIndex).equals(key));
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulBinarySearch")
    public <T extends Comparable> void shouldNotFindKeyUsingIterativeBinarySearch(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulSumSearch() {
        return new Object[][]{
                {new Array<>(34,43), 77},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 2},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 10},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 12},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 17}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulSumSearch")
    public void shouldFindSum(Array<Integer> array, Integer sum) {
        // given

        // when
        boolean sumFound = Chapter2.sumSearch(array, sum);

        // then
        assertTrue(sumFound);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulSumSearch() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 1},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 18},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 35}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulSumSearch")
    public void shouldNotFindSum(Array<Integer> array, Integer sum) {
        // given

        // when
        boolean sumFound = Chapter2.sumSearch(array, sum);

        // then
        assertFalse(sumFound);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingBubbleSort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter2.bubbleSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    private static final double DELTA = 1e-15;

    @DataProvider
    public static Object[][] provideDataForPolynomialEvaluation() {
        return new Object[][]{
                {new ZeroBasedIndexedArray<>(), 4.5, 0.0},
                {new ZeroBasedIndexedArray<>(3.14), 4.5, 3.14},
                {new ZeroBasedIndexedArray<>(-1.0,2.0), 4.5, 8.0},
                {new ZeroBasedIndexedArray<>(-1.5,3.2,1.6,3.4,-5.0,0.0,-1.0,1.0), -2.0, -300.7}
        };
    }

    @Test
    @UseDataProvider("provideDataForPolynomialEvaluation")
    public void shouldEvaluatePolynomialUsingHornersRule(ZeroBasedIndexedArray<Double> coefficients, double x, double expectedResult) {
        // given

        // when
        double actualResult = Chapter2.horner(coefficients, x);

        // then
        assertEquals(expectedResult, actualResult, DELTA);
    }

    @Test
    @UseDataProvider("provideDataForPolynomialEvaluation")
    public void shouldEvaluatePolynomialNaively(ZeroBasedIndexedArray<Double> coefficients, double x, double expectedResult) {
        // given

        // when
        double actualResult = Chapter2.naivePolynomialEvaluation(coefficients, x);

        // then
        assertEquals(expectedResult, actualResult, DELTA);
    }

    @DataProvider
    public static Object[][] provideDataForCountingInversions() {
        return new Object[][]{
                {new Array<>(3)},
                {new Array<>(1,2)},
                {new Array<>(2,1)},
                {new Array<>(2,3,8,6,1)},
                {new Array<>(5,4,3,2,1)},
                {new Array<>(1,2,3,5,6,7,9,13,14)},
                {new Array<>(-5.6,2.3,4.0,-2.0,0.0,0.1,6.6,3.1,-3.0,-5.5)},
                {new Array<>("ccc","aaa","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingInversions")
    public <T extends Comparable> void shouldCountInversions(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        int actualInversions = Chapter2.countInversions(array, 1, array.length);

        // then
        int expectedInversions = 0;
        for (int i = 1; i <= original.length - 1; i++) {
            for (int j = i + 1; j <= original.length; j++) {
                if (greater(original.at(i), original.at(j))) {
                    expectedInversions++;
                }
            }
        }
        assertEquals(expectedInversions, actualInversions);
    }

}
