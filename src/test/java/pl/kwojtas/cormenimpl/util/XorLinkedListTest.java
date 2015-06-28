package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class XorLinkedListTest {

    @Test
    public void shouldCreateEmptyXorLinkedList() {
        // given

        // when
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>();

        // then
        assertNull(xorLinkedList.head);
        assertNull(xorLinkedList.tail);
    }

    @Test
    public void shouldCreateXorLinkedListByCopyingExistingXorLinkedList() {
        // given
        XorLinkedList<String> otherXorLinkedList = getExemplaryXorLinkedList();

        // when
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>(otherXorLinkedList);

        // then
        XorLinkedList.Node first = xorLinkedList.head;
        XorLinkedList.Node second = xorLinkedList.byAddress(first.np);
        XorLinkedList.Node third = xorLinkedList.byAddress(second.np ^ first.address);
        assertEquals("aaa", first.key);
        assertEquals("bbb", second.key);
        assertEquals("ccc", third.key);
        assertEquals(third, xorLinkedList.tail);
        assertEquals(second, xorLinkedList.byAddress(third.np));
        assertEquals(first, xorLinkedList.byAddress(second.np ^ third.address));
    }

    private XorLinkedList<String> getExemplaryXorLinkedList() {
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>();
        XorLinkedList.Node<String> x1 = xorLinkedList.registerNode("aaa");
        XorLinkedList.Node<String> x2 = xorLinkedList.registerNode("bbb");
        XorLinkedList.Node<String> x3 = xorLinkedList.registerNode("ccc");
        x1.np = x2.address;
        x2.np = x1.address ^ x3.address;
        x3.np = x2.address;
        xorLinkedList.head = x1;
        xorLinkedList.tail = x3;
        return xorLinkedList;
    }

    @Test
    public void shouldCreateEmptyXorLinkedListByCopyingExistingEmptyXorLinkedList() {
        // given
        XorLinkedList<String> otherXorLinkedList = new XorLinkedList<>();

        // when
        XorLinkedList<String> xorLinkedList = new XorLinkedList<>(otherXorLinkedList);

        // then
        assertNull(xorLinkedList.head);
        assertNull(xorLinkedList.tail);
    }

    @Test
    public void shouldGetXorLinkedListLength() {
        // given
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();

        // when
        int actualLength = xorLinkedList.getLength();

        // then
        assertEquals(3, actualLength);
    }

    @Test
    public void shouldTransformXorLinkedListToArray() {
        // given
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        Array<String> expectedArray = new Array<>("aaa", "bbb", "ccc");

        // when
        Array<String> actualArray = xorLinkedList.toArray();

        // then
        assertArrayEquals(expectedArray, actualArray);
    }

}
