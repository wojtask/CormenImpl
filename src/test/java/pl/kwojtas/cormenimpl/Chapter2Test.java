package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter2Test {

    @DataProvider
    public static Object[][] provideDataForSorting() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(3,2,1), new Array<>(1,2,3)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(-2.75,-0.53,2.23,2.55,3.14)},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), new Array<>("aaa","bbb","ccc","ddd","eee")}
        };
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T> void shouldSortArrayUsingInsertionSort(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter2.insertionSort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulLinearSearch() {
        return new Object[][]{
                {new Array<>(34), 34, 1},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 6, 5},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 8, 12},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3, 2},
                {new Array<>("aaa","bbb","aaa","ccc"), "ccc", 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulLinearSearch")
    public <T> void shouldFindKeyUsingLinearSearch(Array<T> array, T key, Integer expectedIndex) {
        // given

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertEquals(expectedIndex, actualIndex);
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
    public <T> void shouldNotFindKeyUsingLinearSeach(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @DataProvider
    public static Object[][] provideDataForBinaryAdd() {
        return new Object[][]{
                {new Array<>(0), new Array<>(0), new Array<>(0,0)},
                {new Array<>(1), new Array<>(0), new Array<>(1,0)},
                {new Array<>(0), new Array<>(1), new Array<>(1,0)},
                {new Array<>(1), new Array<>(1), new Array<>(0,1)},
                {new Array<>(0,0,1,1,0,1,0,0,0,0,1), new Array<>(0,1,1,1,1,0,0,1,0,1,1), new Array<>(0,1,0,1,0,0,1,1,0,1,0,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForBinaryAdd")
    public void shouldAddTwoNumbersInBinary(Array<Integer> firstNumberBits, Array<Integer> secondNumberBits, Array<Integer> expectedSumBits) {
        // given

        // when
        Array<Integer> actualSumBits = Chapter2.binaryAdd(firstNumberBits, secondNumberBits);

        // then
        assertArrayEquals(expectedSumBits, actualSumBits);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T> void shouldSortArrayUsingSelectionSort(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter2.selectionSort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForMergeSort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(3,2,1), new Array<>(1,2,3)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(9,8,8,7,7,6,6,6,5,3,2,1), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForMergeSort")
    public void shouldSortArrayUsingMergeSort(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter2.mergeSort(array, 1, array.length);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T> void shouldSortArrayUsingMergeSort_(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter2.mergeSort_(array, 1, array.length);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulBinarySearch() {
        return new Object[][]{
                {new Array<>(34), 34, 1, 1},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 6, 5, 7},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), 3, 3, 3},
                {new Array<>(-4.5,-2.3,-2.2,-0.1,0.6,2.2,9.5), 2.2, 6, 6},
                {new Array<>("aaa","aaa","bbb","ccc"), "aaa", 1, 2}
        };
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
    @UseDataProvider("provideDataForSuccessfulBinarySearch")
    public <T> void shouldFindKeyUsingRecursiveBinarySearch(Array<T> array, T key, Integer lowestExpectedIndex, Integer highestExpectedIndex) {
        // given

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNotNull(actualIndex);
        assertTrue(lowestExpectedIndex <= actualIndex && actualIndex <= highestExpectedIndex);
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulBinarySearch")
    public <T> void shouldNotFindKeyUsingRecursiveBinarySearch(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.recursiveBinarySearch(array, key, 1, array.length);

        // then
        assertNull(actualIndex);
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulBinarySearch")
    public <T> void shouldFindKeyUsingIterativeBinarySearch(Array<T> array, T key, Integer lowestExpectedIndex, Integer highestExpectedIndex) {
        // given

        // when
        Integer actualIndex = Chapter2.iterativeBinarySearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(lowestExpectedIndex <= actualIndex && actualIndex <= highestExpectedIndex);
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulBinarySearch")
    public <T> void shouldNotFindKeyUsingIterativeBinarySearch(Array<T> array, T key) {
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
}
