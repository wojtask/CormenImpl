package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SinglyLinkedListWithTailTest {

    @Test
    public void shouldCreateSinglyLinkedListWithTailWithInitialContents() {

        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>("aaa", "bbb", "ccc");

        assertEquals("aaa", singlyLinkedListWithTail.head.key);
        assertEquals("bbb", singlyLinkedListWithTail.head.next.key);
        assertEquals("ccc", singlyLinkedListWithTail.head.next.next.key);
        assertEquals(singlyLinkedListWithTail.head.next.next, singlyLinkedListWithTail.tail);
        assertNull(singlyLinkedListWithTail.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListWithTail() {

        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>();

        assertNull(singlyLinkedListWithTail.head);
        assertNull(singlyLinkedListWithTail.tail);
    }

    @Test
    public void shouldCreateSinglyLinkedListWithTailFromExistingSinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<String> otherSinglyLinkedListWithTail = new SinglyLinkedListWithTail<>("aaa", "bbb", "ccc");

        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>(otherSinglyLinkedListWithTail);

        assertEquals("aaa", singlyLinkedListWithTail.head.key);
        assertEquals("bbb", singlyLinkedListWithTail.head.next.key);
        assertEquals("ccc", singlyLinkedListWithTail.head.next.next.key);
        assertEquals(singlyLinkedListWithTail.head.next.next, singlyLinkedListWithTail.tail);
        assertNull(singlyLinkedListWithTail.head.next.next.next);
    }

    @Test
    public void shouldCreateEmptySinglyLinkedListWithTailFromExistingEmptySinglyLinkedListWithTail() {
        SinglyLinkedListWithTail<String> otherSinglyLinkedListWithTail = new SinglyLinkedListWithTail<>();

        SinglyLinkedListWithTail<String> singlyLinkedListWithTail = new SinglyLinkedListWithTail<>(otherSinglyLinkedListWithTail);

        assertNull(singlyLinkedListWithTail.head);
        assertNull(singlyLinkedListWithTail.tail);
    }

}
