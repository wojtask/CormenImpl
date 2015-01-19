package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Interval;

import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Chapter5.random;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.util.Util.geq;
import static pl.kwojtas.cormenimpl.util.Util.leq;

@RunWith(DataProviderRunner.class)
public class Chapter7Test {

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
    public <T extends Comparable> void shouldSortArrayUsingQuicksort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.quicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingRandomizedQuicksort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.randomizedQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingQuicksortWithInsertionSortForSmallArrays(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.sortNearlySorted(array, 1, array.length, random(1, array.length));

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingHoareQuicksort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.hoareQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingStoogeSort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.stoogeSort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingQuicksort_(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.quicksort_(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingQuicksort__(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter7.quicksort__(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForPartitioning() {
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
    @UseDataProvider("provideDataForPartitioning")
    public <T extends Comparable> void shouldPartitionArrayUsingMedianOf3Partition(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        int pivotIndex = Chapter7.medianOf3Partition(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertTrue(1 <= pivotIndex && pivotIndex <= array.length);
        assertArrayPartitioned(array, pivotIndex);
    }

    private static <T extends Comparable> void assertArrayPartitioned(Array<T> expected, int pivotIndex) {
        for (int i = 1; i < pivotIndex; i++) {
            assertTrue(leq(expected.at(i), expected.at(pivotIndex)));
        }
        for (int i = pivotIndex + 1; i <= expected.length; i++) {
            assertTrue(geq(expected.at(i), expected.at(pivotIndex)));
        }
    }

    @DataProvider
    public static Object[][] provideDataForFuzzySorting() {
        return new Object[][]{
                {new Array<>(
                        new Interval(34.0,34.0)
                )},
                {new Array<>(
                        new Interval(3.0,5.0),
                        new Interval(2.0,2.5),
                        new Interval(1.9,5.1)
                )},
                {new Array<>(
                        new Interval(5.0,7.0),
                        new Interval(2.0,9.0),
                        new Interval(6.0,8.0),
                        new Interval(6.0,6.0),
                        new Interval(1.0,3.0),
                        new Interval(7.0,8.0)
                )},
                {new Array<>(
                        new Interval(1.0,2.0),
                        new Interval(3.0,5.0),
                        new Interval(6.0,6.0),
                        new Interval(6.0,7.0),
                        new Interval(7.0,8.0),
                        new Interval(8.0,9.0)
                )}
        };
    }

    @Test
    @UseDataProvider("provideDataForFuzzySorting")
    public void shouldSortUsingFuzzySort(Array<Interval> intervals) {
        // given
        Array<Interval> original = new Array<>(intervals);

        // when
        Chapter7.fuzzySort(intervals, 1, intervals.length);

        // then
        assertShuffled(original, intervals);
        assertArrayFuzzySorted(intervals);
    }

    private static void assertArrayFuzzySorted(Array<Interval> array) {
        for (int i = 2; i <= array.length; i++) {
            if (array.at(i).a < array.at(i - 1).a) {
                assertTrue(array.at(i).b >= array.at(i - 1).a);
            }
        }
    }
}
