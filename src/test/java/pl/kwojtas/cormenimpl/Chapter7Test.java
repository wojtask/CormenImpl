package pl.kwojtas.cormenimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.Interval;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Chapter5.class})
public class Chapter7Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter7> constructor = Chapter7.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldSortArrayUsingQuicksort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.quicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingRandomizedQuicksort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.randomizedQuicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksortWithInsertionSortForSmallArrays() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int threshold = 2;

        Chapter7.sortNearlySorted(array, 1, array.length, threshold);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingHoareQuicksort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.hoareQuicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingStoogeSort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.stoogeSort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksort_() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.quicksort_(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldSortArrayUsingQuicksort__() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter7.quicksort__(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldPartitionArrayUsingMedianOf3PartitionAsFirstPickedElement() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(7, 2, 10);

        int pivotIndex = Chapter7.medianOf3Partition(array, 1, array.length);

        assertShuffled(original, array);
        assertTrue(2 <= pivotIndex && pivotIndex <= array.length - 1);
        assertArrayPartitioned(array, pivotIndex);
    }

    @Test
    public void shouldPartitionArrayUsingMedianOf3PartitionAsSecondPickedElement() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(2, 7, 10);

        int pivotIndex = Chapter7.medianOf3Partition(array, 1, array.length);

        assertShuffled(original, array);
        assertTrue(2 <= pivotIndex && pivotIndex <= array.length - 1);
        assertArrayPartitioned(array, pivotIndex);
    }

    @Test
    public void shouldPartitionArrayUsingMedianOf3PartitionAsThirdPickedElement() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(10, 2, 7);

        int pivotIndex = Chapter7.medianOf3Partition(array, 1, array.length);

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
        Array<Interval> intervals = Array.of(
                new Interval(5.0, 7.0),
                new Interval(2.0, 9.0),
                new Interval(6.0, 8.0),
                new Interval(6.0, 6.0),
                new Interval(1.0, 3.0),
                new Interval(7.0, 8.0)
        );
        Array<Interval> original = Array.copyOf(intervals);

        Chapter7.fuzzySort(intervals, 1, intervals.length);

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
