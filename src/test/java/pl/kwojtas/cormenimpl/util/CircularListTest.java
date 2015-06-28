package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class CircularListTest {

    @Test
    public void shouldCreateCircularListWithInitialContents() {
        // given

        // when
        CircularList<String> circularList = new CircularList<>("aaa", "bbb", "ccc");

        // then
        assertEquals("aaa", circularList.head.key);
        assertEquals("bbb", circularList.head.next.key);
        assertEquals("ccc", circularList.head.next.next.key);
        assertEquals(circularList.head, circularList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptyCircularList() {
        // given

        // when
        CircularList<String> circularList = new CircularList<>();

        // then
        assertNull(circularList.head);
    }

    @Test
    public void shouldCreateCircularListFromExistingCircularList() {
        // given
        CircularList<String> otherCircularList = new CircularList<>("aaa", "bbb", "ccc");

        // when
        CircularList<String> circularList = new CircularList<>(otherCircularList);

        // then
        assertEquals("aaa", circularList.head.key);
        assertEquals("bbb", circularList.head.next.key);
        assertEquals("ccc", circularList.head.next.next.key);
        assertEquals(circularList.head, circularList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptyCircularListFromExistingEmptyCircularList() {
        // given
        CircularList<String> otherCircularList = new CircularList<>();

        // when
        CircularList<String> circularList = new CircularList<>(otherCircularList);

        // then
        assertNull(circularList.head);
    }

    @Test
    public void shouldGetCircularListLength() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        CircularList<String> circularList = new CircularList<>(contents);

        // when
        int actualLength = circularList.getLength();

        // then
        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldGetEmptyCircularListLength() {
        // given
        CircularList<String> circularList = new CircularList<>();

        // when
        int actualLength = circularList.getLength();

        // then
        assertEquals(0, actualLength);
    }

    @Test
    public void shouldTransformCircularListToArray() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        CircularList<String> circularList = new CircularList<>(contents);

        // when
        Array<String> actualArray = circularList.toArray();

        // then
        assertArrayEquals(new Array<>(contents), actualArray);
    }

    @Test
    public void shouldTransformEmptyCircularListToArray() {
        // given
        CircularList<String> circularList = new CircularList<>();

        // when
        Array<String> actualArray = circularList.toArray();

        // then
        assertArrayEquals(new Array<>(), actualArray);
    }

}
