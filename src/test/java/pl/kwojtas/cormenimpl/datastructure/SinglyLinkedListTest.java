package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class SinglyLinkedListTest {

    @Test
    public void shouldCreateSinglyLinkedListWithInitialContents() {

        SinglyLinkedList<String> singlyLinkedList = SinglyLinkedList.of("aaa", "bbb", "ccc");

        assertEquals("aaa", singlyLinkedList.head.key);
        assertEquals("bbb", singlyLinkedList.head.next.key);
        assertEquals("ccc", singlyLinkedList.head.next.next.key);
        assertNull(singlyLinkedList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedList() {

        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>();

        assertNull(singlyLinkedList.head);
    }

    @Test
    public void shouldCreateSinglyLinkedListFromExistingSinglyLinkedList() {
        SinglyLinkedList<String> otherSinglyLinkedList = SinglyLinkedList.of("aaa", "bbb", "ccc");

        SinglyLinkedList<String> singlyLinkedList = SinglyLinkedList.copyOf(otherSinglyLinkedList);

        assertEquals("aaa", singlyLinkedList.head.key);
        assertEquals("bbb", singlyLinkedList.head.next.key);
        assertEquals("ccc", singlyLinkedList.head.next.next.key);
        assertNull(singlyLinkedList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListFromExistingEmptySinglyLinkedList() {
        SinglyLinkedList<String> otherSinglyLinkedList = new SinglyLinkedList<>();

        SinglyLinkedList<String> singlyLinkedList = SinglyLinkedList.copyOf(otherSinglyLinkedList);

        assertNull(singlyLinkedList.head);
    }

    @Test
    public void shouldGetSinglyLinkedListLength() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        SinglyLinkedList<String> singlyLinkedList = SinglyLinkedList.of(contents);

        int actualLength = singlyLinkedList.getLength();

        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformSinglyLinkedListToArray() {
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        SinglyLinkedList<String> singlyLinkedList = SinglyLinkedList.of(contents);

        Array<String> actualArray = singlyLinkedList.toArray();

        assertArrayEquals(Array.of(contents), actualArray);
    }

}
