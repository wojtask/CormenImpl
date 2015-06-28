package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class HeapTest {

    @Test
    public void shouldCreateHeapFromExistingArrayAndGivenLength() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        int length = 9;

        // when
        Heap<String> heap = new Heap<>(array, length);

        // then
        assertEquals(array.length, heap.heapSize);
        assertEquals(length, heap.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(array.at(i), heap.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenCreatingHeapFromExistingArrayLargerThanHeapLength() {
        // given
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        int capacity = 2;

        try {
            // when
            new Heap<>(array, capacity);
        } catch (RuntimeException e) {
            // then
            assertEquals("Array is larger than initial length", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldCreateHeapFromExistingArray() {
        // given
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");

        // when
        Heap<String> heap = new Heap<>(otherArray);

        // then
        assertEquals(otherArray.length, heap.heapSize);
        assertArrayEquals(otherArray, heap);
    }

    @Test
    public void shouldCreateEmptyHeapOfGivenLength() {
        // given
        int length = 5;

        // when
        Heap<String> heap = Heap.withLength(length);

        // then
        assertEquals(length, heap.length);
        assertEquals(0, heap.heapSize);
    }

}
