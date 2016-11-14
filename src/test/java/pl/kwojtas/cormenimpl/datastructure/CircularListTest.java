package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class CircularListTest {

    @Test
    public void shouldCreateCircularListWithInitialContents() {

        CircularList<String> circularList = new CircularList<>("aaa", "bbb", "ccc");

        assertEquals("aaa", circularList.head.key);
        assertEquals("bbb", circularList.head.next.key);
        assertEquals("ccc", circularList.head.next.next.key);
        assertEquals(circularList.head, circularList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptyCircularList() {

        CircularList<String> circularList = new CircularList<>();

        assertNull(circularList.head);
    }

    @Test
    public void shouldCreateCircularListFromExistingCircularList() {
        CircularList<String> otherCircularList = new CircularList<>("aaa", "bbb", "ccc");

        CircularList<String> circularList = new CircularList<>(otherCircularList);

        assertEquals("aaa", circularList.head.key);
        assertEquals("bbb", circularList.head.next.key);
        assertEquals("ccc", circularList.head.next.next.key);
        assertEquals(circularList.head, circularList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptyCircularListFromExistingEmptyCircularList() {
        CircularList<String> otherCircularList = new CircularList<>();

        CircularList<String> circularList = new CircularList<>(otherCircularList);

        assertNull(circularList.head);
    }

    @Test
    public void shouldGetCircularListLength() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        CircularList<String> circularList = new CircularList<>(contents);

        int actualLength = circularList.getLength();

        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldGetEmptyCircularListLength() {
        CircularList<String> circularList = new CircularList<>();

        int actualLength = circularList.getLength();

        assertEquals(0, actualLength);
    }

    @Test
    public void shouldTransformCircularListToArray() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        CircularList<String> circularList = new CircularList<>(contents);

        Array<String> actualArray = circularList.toArray();

        assertArrayEquals(Array.of(contents), actualArray);
    }

    @Test
    public void shouldTransformEmptyCircularListToArray() {
        CircularList<String> circularList = new CircularList<>();

        Array<String> actualArray = circularList.toArray();

        assertArrayEquals(Array.emptyArray(), actualArray);
    }

}
