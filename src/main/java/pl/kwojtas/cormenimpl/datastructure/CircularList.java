package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a singly linked circular list.
 *
 * @param <E> the type of elements in the list
 */
public class CircularList<E> extends SinglyLinkedList<E> {

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public CircularList(E... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node<>(elements[0]);
        head.next = head;
        Node<E> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<E> y = new Node<>(elements[i]);
            y.next = head;
            x.next = y;
            x = y;
        }
    }

    /**
     * Creates a singly linked circular list by copying an existing singly linked circular list.
     *
     * @param otherList the list to be copied
     */
    public CircularList(CircularList<E> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node<>(otherList.head.key);
        head.next = head;
        Node<E> x = head;
        Node<E> z = otherList.head.next;
        while (z != otherList.head) {
            Node<E> y = new Node<>(z.key);
            x.next = y;
            y.next = head;
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
        if (head == null) {
            return 0;
        }
        int length = 1;
        Node<E> x = head.next;
        while (x != head) {
            length++;
            x = x.next;
        }
        return length;
    }

    /**
     * Transforms the list to an array.
     *
     * @return an array containing all the elements in the list
     */
    public Array<E> toArray() {
        if (head == null) {
            return new Array<>();
        }
        Array<E> array = Array.withLength(getLength());
        array.set(1, head.key);
        Node<E> x = head.next;
        int i = 2;
        while (x != head) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
