package pl.kwojtas.cormenimpl.util;

/**
 * Implements a singly linked list with tail.
 *
 * @param <T> the type of elements in the list
 */
public class SinglyLinkedListWithTail<T> extends SinglyLinkedList<T> {

    /**
     * The tail of the list.
     */
    public SinglyLinkedListWithTail.Node<T> tail;

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public SinglyLinkedListWithTail(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = tail = new Node<>(elements[0]);
        Node<T> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<T> y = new Node<>(elements[i]);
            x.next = tail = y;
            x = y;
        }
    }

    /**
     * Creates a singly linked list with tail by copying an existing singly linked list with tail.
     *
     * @param otherList the list to be copied
     */
    public SinglyLinkedListWithTail(SinglyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = tail = new Node<>(otherList.head.key);
        Node<T> x = head;
        Node<T> z = otherList.head.next;
        while (z != null) {
            Node<T> y = new Node<>(z.key);
            x.next = tail = y;
            x = y;
            z = z.next;
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int getLength() {
        int length = 0;
        Node<T> x = head;
        while (x != null) {
            length++;
            x = x.next;
        }
        return length;
    }

    /**
     * Transforms the list to the array.
     *
     * @return the array containing all the elements in the list
     */
    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
        Node<T> x = head;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
