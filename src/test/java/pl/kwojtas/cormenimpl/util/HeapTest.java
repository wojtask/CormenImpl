package pl.kwojtas.cormenimpl.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class HeapTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateHeapFromExistingArrayAndGivenLength() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        int length = 9;

        Heap<String> heap = new Heap<>(array, length);

        assertEquals(array.length, heap.heapSize);
        assertEquals(length, heap.length);
        for (int i = 1; i <= array.length; i++) {
            assertEquals(array.at(i), heap.at(i));
        }
    }

    @Test
    public void shouldThrowExceptionWhenCreatingHeapFromExistingArrayLargerThanHeapLength() {
        Array<String> array = new Array<>("aaa", "bbb", "ccc");
        int capacity = 2;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Array is larger than initial length");
        new Heap<>(array, capacity);
    }

    @Test
    public void shouldCreateHeapFromExistingArray() {
        Array<String> otherArray = new Array<>("aaa", "bbb", "ccc");

        Heap<String> heap = new Heap<>(otherArray);

        assertEquals(otherArray.length, heap.heapSize);
        assertArrayEquals(otherArray, heap);
    }

    @Test
    public void shouldCreateEmptyHeapOfGivenLength() {
        int length = 5;

        Heap<String> heap = Heap.withLength(length);

        assertEquals(length, heap.length);
        assertEquals(0, heap.heapSize);
    }

}
