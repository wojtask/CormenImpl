package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ListTest {

    @Test
    public void shouldCreateListWithInitialContents() {

        List<String> list = new List<>("aaa", "bbb", "ccc");

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

        List<String> list = new List<>();

        assertNull(list.head);
    }

    @Test
    public void shouldCreateListFromExistingList() {
        List<String> otherList = new List<>("aaa", "bbb", "ccc");

        List<String> list = new List<>(otherList);

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
        List<String> otherList = new List<>();

        List<String> list = new List<>(otherList);

        assertNull(list.head);
    }

    @Test
    public void shouldGetListLength() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = new List<>(contents);

        int actualLength = list.getLength();

        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformListToArray() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        List<String> list = new List<>(contents);

        Array<String> actualArray = list.toArray();

        assertArrayEquals(new Array<>(contents), actualArray);
    }

}
