package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ListTest {

    @Test
    public void shouldCreateListWithInitialContents() {
        // given

        // when
        List<String> list = new List<>("aaa", "bbb", "ccc");

        // then
        assertEquals("aaa", list.head.key);
        assertEquals("bbb", list.head.next.key);
        assertEquals("ccc", list.head.next.next.key);
        assertNull(list.head.next.next.next);
        assertNull(list.head.prev);
        assertEquals(list.head, list.head.next.prev);
        assertEquals(list.head.next, list.head.next.next.prev);
    }

    @Test
    public void shouldCreateEmptyList() {
        // given

        // when
        List<String> list = new List<>();

        // then
        assertNull(list.head);
    }

    @Test
    public void shouldCreateListFromExistingList() {
        // given
        List<String> otherList = new List<>("aaa", "bbb", "ccc");

        // when
        List<String> list = new List<>(otherList);

        // then
        assertEquals("aaa", list.head.key);
        assertEquals("bbb", list.head.next.key);
        assertEquals("ccc", list.head.next.next.key);
        assertNull(list.head.next.next.next);
        assertNull(list.head.prev);
        assertEquals(list.head, list.head.next.prev);
        assertEquals(list.head.next, list.head.next.next.prev);
    }

    @Test
    public void shouldCreateEmptyListFromExistingEmptyList() {
        // given
        List<String> otherList = new List<>();

        // when
        List<String> list = new List<>(otherList);

        // then
        assertNull(list.head);
    }

    @Test
    public void shouldGetListLength() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = new List<>(contents);

        // when
        int actualLength = list.getLength();

        // then
        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformListToArray() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = new List<>(contents);

        // when
        Array<String> actualArray = list.toArray();

        // then
        assertArrayEquals(new Array<>(contents), actualArray);
    }

}
