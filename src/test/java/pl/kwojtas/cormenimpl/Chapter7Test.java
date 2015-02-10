package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Interval;

import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

public class Chapter7Test {

    @Test
    public void shouldSortArrayUsingQuicksort() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.quicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingRandomizedQuicksort() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.randomizedQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksortWithInsertionSortForSmallArrays() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int threshold = 2;

        // when
        Chapter7.sortNearlySorted(array, 1, array.length, threshold);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingHoareQuicksort() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.hoareQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingStoogeSort() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.stoogeSort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksort_() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.quicksort_(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksort__() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter7.quicksort__(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldPartitionArrayUsingMedianOf3Partition() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        int pivotIndex = Chapter7.medianOf3Partition(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertTrue(2 <= pivotIndex && pivotIndex <= array.length - 1);
        assertArrayPartitioned(array, pivotIndex);
    }

    private static void assertArrayPartitioned(Array<Integer> expected, int pivotIndex) {
        for (int i = 1; i < pivotIndex; i++) {
            assertTrue(expected.at(i) <= expected.at(pivotIndex));
        }
        for (int i = pivotIndex + 1; i <= expected.length; i++) {
            assertTrue(expected.at(i) >= expected.at(pivotIndex));
        }
    }

    @Test
    public void shouldSortIntervalsUsingFuzzySort() {
        // given
        Array<Interval> intervals = new Array<>(
                new Interval(5.0,7.0),
                new Interval(2.0,9.0),
                new Interval(6.0,8.0),
                new Interval(6.0,6.0),
                new Interval(1.0,3.0),
                new Interval(7.0,8.0)
        );
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
