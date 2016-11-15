package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ListWithSentinelTest {

    @Test
    public void shouldCreateListWithSentinelWithInitialContents() {

        ListWithSentinel<String> listWithSentinel = ListWithSentinel.of("aaa", "bbb", "ccc");

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

        ListWithSentinel<String> listWithSentinel = ListWithSentinel.emptyList();

        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldCreateListWithSentinelFromExistingListWithSentinel() {
        ListWithSentinel<String> otherListWithSentinel = ListWithSentinel.of("aaa", "bbb", "ccc");

        ListWithSentinel<String> listWithSentinel = ListWithSentinel.copyOf(otherListWithSentinel);

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
        ListWithSentinel<String> otherListWithSentinel = ListWithSentinel.emptyList();

        ListWithSentinel<String> listWithSentinel = ListWithSentinel.copyOf(otherListWithSentinel);

        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldGetListWithSentinelLength() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = ListWithSentinel.of(contents);

        int actualLength = listWithSentinel.getLength();

        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformListWithSentinelToArray() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = ListWithSentinel.of(contents);

        Array<String> actualArray = listWithSentinel.toArray();

        assertArrayEquals(Array.of(contents), actualArray);
    }

}
