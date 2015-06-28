package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class ListWithSentinelTest {

    @Test
    public void shouldCreateListWithSentinelWithInitialContents() {
        // given

        // when
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>("aaa", "bbb", "ccc");

        // then
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
        // given

        // when
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>();

        // then
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldCreateListWithSentinelFromExistingListWithSentinel() {
        // given
        ListWithSentinel<String> otherListWithSentinel = new ListWithSentinel<>("aaa", "bbb", "ccc");

        // when
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(otherListWithSentinel);

        // then
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
        // given
        ListWithSentinel<String> otherListWithSentinel = new ListWithSentinel<>();

        // when
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(otherListWithSentinel);

        // then
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.next);
        assertEquals(listWithSentinel.nil, listWithSentinel.nil.prev);
    }

    @Test
    public void shouldGetListWithSentinelLength() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(contents);

        // when
        int actualLength = listWithSentinel.getLength();

        // then
        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformListWithSentinelToArray() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        ListWithSentinel<String> listWithSentinel = new ListWithSentinel<>(contents);

        // when
        Array<String> actualArray = listWithSentinel.toArray();

        // then
        assertArrayEquals(new Array<>(contents), actualArray);
    }

}
