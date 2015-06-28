package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SinglyLinkedListWithTailTest {

    @Test
    public void shouldCreateSinglyLinkedListWithTailWithInitialContents() {
        // given

        // when
        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>("aaa", "bbb", "ccc");

        // then
        assertEquals("aaa", singlyLinkedListWithTail.head.key);
        assertEquals("bbb", singlyLinkedListWithTail.head.next.key);
        assertEquals("ccc", singlyLinkedListWithTail.head.next.next.key);
        assertEquals(singlyLinkedListWithTail.head.next.next, singlyLinkedListWithTail.tail);
        assertNull(singlyLinkedListWithTail.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListWithTail() {
        // given

        // when
        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>();

        // then
        assertNull(singlyLinkedListWithTail.head);
        assertNull(singlyLinkedListWithTail.tail);
    }

    @Test
    public void shouldCreateSinglyLinkedListWithTailFromExistingSinglyLinkedListWithTail() {
        // given
        SinglyLinkedListWithTail<String> otherSinglyLinkedListWithTail = new SinglyLinkedListWithTail<>("aaa", "bbb", "ccc");

        // when
        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>(otherSinglyLinkedListWithTail);

        // then
        assertEquals("aaa", singlyLinkedListWithTail.head.key);
        assertEquals("bbb", singlyLinkedListWithTail.head.next.key);
        assertEquals("ccc", singlyLinkedListWithTail.head.next.next.key);
        assertEquals(singlyLinkedListWithTail.head.next.next, singlyLinkedListWithTail.tail);
        assertNull(singlyLinkedListWithTail.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListWithTailFromExistingEmptySinglyLinkedListWithTail() {
        // given
        SinglyLinkedListWithTail<String> otherSinglyLinkedListWithTail = new SinglyLinkedListWithTail<>();

        // when
        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>(otherSinglyLinkedListWithTail);

        // then
        assertNull(singlyLinkedListWithTail.head);
        assertNull(singlyLinkedListWithTail.tail);
    }

}
