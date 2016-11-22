package pl.kwojtas.cormenimpl.datastructure;

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
     * Returns an empty list (a list containing 0 elements).
     *
     * @return the empty singly linked list with tail
     */
    public static <E> SinglyLinkedListWithTail<E> emptyList() {
        return new SinglyLinkedListWithTail<>();
    }

    /**
     * Creates a list of given elements.
     *
     * @param elements the initial contents of the list
     * @return the singly linked list with tail containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> SinglyLinkedListWithTail<E> of(E... elements) {
        SinglyLinkedListWithTail<E> list = emptyList();
        if (elements.length == 0) {
            return list;
        }
        list.head = list.tail = new Node<>(elements[0]);
        Node<E> x = list.head;
        for (int i = 1; i < elements.length; i++) {
            Node<E> y = new Node<>(elements[i]);
            x.next = list.tail = y;
            x = y;
        }
        return list;
    }

    /**
     * Returns a copy of an existing singly linked list with tail.
     *
     * @param otherList the list to be copied
     * @return the copy of {@code otherList}
     */
    public static <E> SinglyLinkedListWithTail<E> copyOf(SinglyLinkedListWithTail<E> otherList) {
        SinglyLinkedListWithTail<E> list = emptyList();
        if (otherList.head == null) {
            return list;
        }
        list.head = list.tail = new Node<>(otherList.head.key);
        Node<E> x = list.head;
        Node<E> z = otherList.head.next;
        while (z != null) {
            Node<E> y = new Node<>(z.key);
            x.next = list.tail = y;
            x = y;
            z = z.next;
        }
        return list;
    }

}
