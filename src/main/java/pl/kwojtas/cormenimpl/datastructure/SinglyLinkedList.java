package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a singly linked list.
 *
 * @param <E> the type of elements in the list
 */
public class SinglyLinkedList<E> {

    /**
     * Implements a singly linked list's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

        /**
         * The next node.
         */
        public Node<F> next;

        /**
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(F key) {
            this.key = key;
        }
    }

    /**
     * The head of the list.
     */
    public Node<E> head;

    protected SinglyLinkedList() {
    }

    /**
     * Returns an empty list (a list containing 0 elements).
     *
     * @return the empty singly linked list
     */
    public static <E> SinglyLinkedList<E> emptyList() {
        return new SinglyLinkedList<>();
    }

    /**
     * Creates a list of given elements.
     *
     * @param elements the initial contents of the list
     * @return the singly linked list containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> SinglyLinkedList<E> of(E... elements) {
        SinglyLinkedList<E> list = emptyList();
        if (elements.length == 0) {
            return list;
        }
        list.head = new Node<>(elements[0]);
        Node<E> x = list.head;
        for (int i = 1; i < elements.length; i++) {
            Node<E> y = new Node<>(elements[i]);
            x.next = y;
            x = y;
        }
        return list;
    }

    /**
     * Returns a copy of an existing singly linked list.
     *
     * @param otherList the list to be copied
     * @return the copy of {@code otherList}
     */
    public static <E> SinglyLinkedList<E> copyOf(SinglyLinkedList<E> otherList) {
        SinglyLinkedList<E> list = emptyList();
        if (otherList.head == null) {
            return list;
        }
        list.head = new Node<>(otherList.head.key);
        Node<E> x = list.head;
        Node<E> z = otherList.head.next;
        while (z != null) {
            Node<E> y = new Node<>(z.key);
            x.next = y;
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
        int length = 0;
        Node<E> x = head;
        while (x != null) {
            length++;
            x = x.next;
        }
        return length;
    }

    /**
     * Transforms the list to an array.
     *
     * @return the array containing all the elements in the list
     */
    public Array<E> toArray() {
        Array<E> array = Array.ofLength(getLength());
        Node<E> x = head;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
