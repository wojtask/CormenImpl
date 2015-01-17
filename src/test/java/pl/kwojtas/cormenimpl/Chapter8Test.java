package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

@RunWith(DataProviderRunner.class)
public class Chapter8Test {

    @DataProvider
    public static Object[][] provideDataForCountingSort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34), 60},
                {new Array<>(3,2,1,0), new Array<>(0,1,2,3), 3},
                {new Array<>(2,5,3,0,2,3,0,3), new Array<>(0,0,2,2,3,3,3,5), 5},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), new Array<>(0,0,1,1,2,2,3,3,4,6,6), 6}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingCountingSort(Array<Integer> array, Array<Integer> expectedSorted, int boundary) {
        // given
        Array<Integer> actualSorted = new Array<>();

        // when
        Chapter8.countingSort(array, actualSorted, boundary);

        // then
        assertArrayEquals(expectedSorted, actualSorted);
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingNonStableCountingSort(Array<Integer> array, Array<Integer> expectedSorted, int boundary) {
        // given
        Array<Integer> actualSorted = new Array<>();

        // when
        Chapter8.nonStableCountingSort(array, actualSorted, boundary);

        // then
        assertArrayEquals(expectedSorted, actualSorted);
    }

    @DataProvider
    public static Object[][] provideDataForCountingNumbersInRange() {
        return new Object[][]{
                {new Array<>(34), 34, 34, 34, 1},
                {new Array<>(34), 66, 34, 34, 1},
                {new Array<>(34), 34, 2, 35, 1},
                {new Array<>(34), 34, 35, 35, 0},
                {new Array<>(3,2,1,0), 3, 0, 1, 2},
                {new Array<>(3,2,1,0), 3, 0, 3, 4},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, -1, 0},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, 3, 8},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 5, 8, 2},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 10, 20, 0}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingNumbersInRange")
    public void shouldCountNumbersInRange(Array<Integer> array, int boundary, int a, int b, int expectedInRange) {
        // given

        // when
        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        // then
        assertEquals(expectedInRange, actualInRange);
    }

    @DataProvider
    public static Object[][] provideDataForRadixSort() {
        return new Object[][]{
                {new Array<>(34), 2, new Array<>(34)},
                {new Array<>(3,2,1,0), 1, new Array<>(0,1,2,3)},
                {new Array<>(345,765,439,89,123,417,688), 3, new Array<>(89,123,345,417,439,688,765)},
                {new Array<>(24015,44036,14014,62027,55033,19012,63032), 5, new Array<>(14014,19012,24015,44036,55033,62027,63032)}
        };
    }

    @Test
    @UseDataProvider("provideDataForRadixSort")
    public void shouldSortArrayUsingRadixSort(Array<Integer> array, int digits, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter8.radixSort(array, digits);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForSortingNSquaredNumbers() {
        return new Object[][]{
                {new Array<>(0), new Array<>(0)},
                {new Array<>(3,2,1,0), new Array<>(0,1,2,3)},
                {new Array<>(22,48,1,0,45,26,15), new Array<>(0,1,15,22,26,45,48)},
                {new Array<>(15,56,25,66,23,92,2,45,7,39), new Array<>(2,7,15,23,25,39,45,56,66,92)}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingNSquaredNumbers")
    public void shouldSortNSquaredNumbers(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter8.sortNSquaredNumbers(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForSortingUsingBitwiseSort() {
        return new Object[][]{
                {new Array<>(0), new Array<>(0)},
                {new Array<>(0,1), new Array<>(0,1)},
                {new Array<>(1,1,0,1), new Array<>(0,1,1,1)},
                {new Array<>(1,1,1,0,1,1,1,1,1,0,0), new Array<>(0,0,0,1,1,1,1,1,1,1,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingUsingBitwiseSort")
    public void shouldSortArrayUsingBitwiseSort(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter8.bitwiseSort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingCountingSortInPlace(Array<Integer> array, Array<Integer> expectedSorted, int boundary) {
        // given

        // when
        Chapter8.countingSortInPlace(array, boundary);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForGroupingJugs() {
        return new Object[][]{
                {new Array<>(34.0), new Array<>(34.0)},
                {new Array<>(1.0,2.0,3.0), new Array<>(3.0,2.0,1.0)},
                {new Array<>(1.0,2.0,3.0), new Array<>(1.0,2.0,3.0)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(-0.53,-2.75,2.23,2.55,3.14)},
                {new Array<>(5.0,7.0,9.0,2.0,6.0,8.0,6.0,6.0,3.0,1.0,7.0,8.0), new Array<>(6.0,5.0,9.0,7.0,3.0,8.0,2.0,6.0,8.0,6.0,7.0,1.0)}
        };
    }

    @Test
    @UseDataProvider("provideDataForGroupingJugs")
    public void shouldGroupJugsUsingJugsGroup(Array<Double> jugs1, Array<Double> jugs2) {
        // given
        Array<Double> originalJugs1 = new Array<>(jugs1);

        // when
        Chapter8.jugsGroup(jugs1, jugs2);

        // then
        assertShuffled(originalJugs1, jugs1);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @Test
    @UseDataProvider("provideDataForGroupingJugs")
    public void shouldGroupJugsUsingJugsSort(Array<Double> jugs1, Array<Double> jugs2) {
        // given
        Array<Double> originalJugs1 = new Array<>(jugs1);

        // when
        Chapter8.jugsMatch(jugs1, jugs2, 1, jugs1.length);

        // then
        assertShuffled(originalJugs1, jugs1);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @DataProvider
    public static Object[][] provideDataForKSorting() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(34), 3},
                {new Array<>(3,2,1), 2},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 8},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 6},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 4},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForKSorting")
    public void shouldKSortArray(Array<Integer> array, int k) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.kSort(array, k, 1, array.length);

        // then
        assertShuffled(original, array);
        for (int i = 1; i <= array.length - k; i++) {
            assertTrue(array.at(i) <= array.at(i + k));
        }
    }

}
