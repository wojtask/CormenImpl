package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class XorLinkedListTest {

    @Test
    public void shouldCreateEmptyXorLinkedList() {

        XorLinkedList<String> xorLinkedList = XorLinkedList.emptyList();

        assertNull(xorLinkedList.head);
        assertNull(xorLinkedList.tail);
    }

    @Test
    public void shouldCreateXorLinkedListByCopyingExistingXorLinkedList() {
        XorLinkedList<String> otherXorLinkedList = getExemplaryXorLinkedList();

        XorLinkedList<String> xorLinkedList = XorLinkedList.copyOf(otherXorLinkedList);

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
        XorLinkedList<String> xorLinkedList = XorLinkedList.emptyList();
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
        XorLinkedList<String> otherXorLinkedList = XorLinkedList.emptyList();

        XorLinkedList<String> xorLinkedList = XorLinkedList.copyOf(otherXorLinkedList);

        assertNull(xorLinkedList.head);
        assertNull(xorLinkedList.tail);
    }

    @Test
    public void shouldGetXorLinkedListLength() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();

        int actualLength = xorLinkedList.getLength();

        assertEquals(3, actualLength);
    }

    @Test
    public void shouldTransformXorLinkedListToArray() {
        XorLinkedList<String> xorLinkedList = getExemplaryXorLinkedList();
        Array<String> expectedArray = Array.of("aaa", "bbb", "ccc");

        Array<String> actualArray = xorLinkedList.toArray();

        assertArrayEquals(expectedArray, actualArray);
    }

}
