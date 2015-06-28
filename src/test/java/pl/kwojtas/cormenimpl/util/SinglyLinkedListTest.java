package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class SinglyLinkedListTest {

    @Test
    public void shouldCreateSinglyLinkedListWithInitialContents() {
        // given

        // when
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>("aaa", "bbb", "ccc");

        // then
        assertEquals("aaa", singlyLinkedList.head.key);
        assertEquals("bbb", singlyLinkedList.head.next.key);
        assertEquals("ccc", singlyLinkedList.head.next.next.key);
        assertNull(singlyLinkedList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedList() {
        // given

        // when
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>();

        // then
        assertNull(singlyLinkedList.head);
    }

    @Test
    public void shouldCreateSinglyLinkedListFromExistingSinglyLinkedList() {
        // given
        SinglyLinkedList<String> otherSinglyLinkedList = new SinglyLinkedList<>("aaa", "bbb", "ccc");

        // when
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>(otherSinglyLinkedList);

        // then
        assertEquals("aaa", singlyLinkedList.head.key);
        assertEquals("bbb", singlyLinkedList.head.next.key);
        assertEquals("ccc", singlyLinkedList.head.next.next.key);
        assertNull(singlyLinkedList.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListFromExistingEmptySinglyLinkedList() {
        // given
        SinglyLinkedList<String> otherSinglyLinkedList = new SinglyLinkedList<>();

        // when
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>(otherSinglyLinkedList);

        // then
        assertNull(singlyLinkedList.head);
    }

    @Test
    public void shouldGetSinglyLinkedListLength() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>(contents);

        // when
        int actualLength = singlyLinkedList.getLength();

        // then
        assertEquals(contents.length, actualLength);
    }

    @Test
    public void shouldTransformSinglyLinkedListToArray() {
        // given
        String[] contents = new String[]{"aaa", "bbb", "ccc"};
        SinglyLinkedList<String> singlyLinkedList = new SinglyLinkedList<>(contents);

        // when
        Array<String> actualArray = singlyLinkedList.toArray();

        // then
        assertArrayEquals(new Array<>(contents), actualArray);
    }

}
