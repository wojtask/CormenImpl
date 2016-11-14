package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ListWithSentinelTest {

    @Test
    public void shouldCreateListWithSentinelWithInitialContents() {

        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>("aaa", "bbb", "ccc");

        assertEquals("aaa", listWithSentinel.nil.next.key);
        assertEquals("bbb", listWithSentinel.nil.next.next.key);
        assertEquals("ccc", listWithSentinel.nil.next.next.next.key);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next.next.next.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next.prev);
        assertEquals(listWithSentinel.nil.next, listWithSentinel.nil.next.next.prev);
        assertEquals(listWithSentinel.nil.next.next, listWithSentinel.nil.next.next.next.prev);
    }

    @Test
    public void shouldCreateEmptyListWithSentinel() {

        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>();

        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldCreateListWithSentinelFromExistingListWithSentinel() {
        ListWithSentinel<String> otherListWithSentinel = new ListWithSentinel<>("aaa", "bbb", "ccc");

        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(otherListWithSentinel);

        assertEquals("aaa", listWithSentinel.nil.next.key);
        assertEquals("bbb", listWithSentinel.nil.next.next.key);
        assertEquals("ccc", listWithSentinel.nil.next.next.next.key);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next.next.next.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next.prev);
        assertEquals(listWithSentinel.nil.next, listWithSentinel.nil.next.next.prev);
        assertEquals(listWithSentinel.nil.next.next, listWithSentinel.nil.next.next.next.prev);
    }

    @Test
    public void shouldCreateEmptyListWithSentinelFromExistingEmptyListWithSentinel() {
        ListWithSentinel<String> otherListWithSentinel = new ListWithSentinel<>();

        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(otherListWithSentinel);

        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldGetListWithSentinelLength() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(contents);

        int actualLength = listWithSentinel.getLength();

        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformListWithSentinelToArray() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(contents);

        Array<String> actualArray = listWithSentinel.toArray();

        assertArrayEquals(Array.of(contents), actualArray);
    }

}
