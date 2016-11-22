package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a singly linked circular list.
 *
 * @param <E> the type of elements in the list
 */
public class CircularList<E> extends SinglyLinkedList<E> {

    private CircularList() {
    }

    /**
     * Returns an empty list (a list containing 0 elements).
     *
     * @return the empty singly linked circular list
     */
    public static <E> CircularList<E> emptyList() {
        return new CircularList<>();
    }

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     * @return the singly linked circular list containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> CircularList<E> of(E... elements) {
        CircularList<E> list = emptyList();
        if (elements.length == 0) {
            return list;
        }
        list.head = new Node<>(elements[0]);
        list.head.next = list.head;
        Node<E> x = list.head;
        for (int i = 1; i < elements.length; i++) {
            Node<E> y = new Node<>(elements[i]);
            y.next = list.head;
            x.next = y;
            x = y;
        }
        return list;
    }

    /**
     * Creates a singly linked circular list by copying an existing singly linked circular list.
     *
     * @param otherList the list to be copied
     * @return the copy of {@code otherList}
     */
    public static <E> CircularList<E> copyOf(CircularList<E> otherList) {
        CircularList<E> list = emptyList();
        if (otherList.head == null) {
            return list;
        }
        list.head = new Node<>(otherList.head.key);
        list.head.next = list.head;
        Node<E> x = list.head;
        Node<E> z = otherList.head.next;
        while (z != otherList.head) {
            Node<E> y = new Node<>(z.key);
            x.next = y;
            y.next = list.head;
            x = y;
            z = z.next;
        }
        return list;
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
            return Array.emptyArray();
        }
        Array<E> array = Array.ofLength(getLength());
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
