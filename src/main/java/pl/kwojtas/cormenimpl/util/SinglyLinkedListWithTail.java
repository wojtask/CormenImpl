package pl.kwojtas.cormenimpl.util;

/**
 * Implements a singly linked list with tail.
 *
 * @param <E> the type of elements in the list
 */
public class SinglyLinkedListWithTail<E> extends SinglyLinkedList<E> {

    /**
     * The tail of the list.
     */
    public SinglyLinkedListWithTail.Node<E> tail;

    /**
     * Creates a list with tail from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public SinglyLinkedListWithTail(E... elements) {
        if (elements.length == 0) {
            return;
        }
        head = tail = new Node<>(elements[0]);
        Node<E> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<E> y = new Node<>(elements[i]);
            x.next = tail = y;
            x = y;
        }
    }

    /**
     * Creates a singly linked list with tail by copying an existing singly linked list with tail.
     *
     * @param otherList the list to be copied
     */
    public SinglyLinkedListWithTail(SinglyLinkedListWithTail<E> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = tail = new Node<>(otherList.head.key);
        Node<E> x = head;
        Node<E> z = otherList.head.next;
        while (z != null) {
            Node<E> y = new Node<>(z.key);
            x.next = tail = y;
            x = y;
            z = z.next;
        }
    }

}
