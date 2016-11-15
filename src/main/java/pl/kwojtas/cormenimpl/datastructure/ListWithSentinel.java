package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a doubly linked list with a sentinel.
 *
 * @param <E> the type of elements in a list
 */
public class ListWithSentinel<E> {

    /**
     * Implements a doubly linked list's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

        /**
         * The previous node in a doubly linked list.
         */
        public Node<F> prev;

        /**
         * The next node in a doubly linked list.
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
     * The sentinel node.
     */
    public Node<E> nil;

    private ListWithSentinel() {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
    }

    /**
     * Returns an empty list (a list containing 0 elements).
     *
     * @return the empty doubly linked list with a sentinel
     */
    public static <E> ListWithSentinel<E> emptyList() {
        return new ListWithSentinel<>();
    }

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     * @return the doubly linked list with a sentinel containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> ListWithSentinel<E> of(E... elements) {
        ListWithSentinel<E> list = emptyList();
        if (elements.length == 0) {
            return list;
        }
        Node<E> x = list.nil;
        for (E element : elements) {
            Node<E> y = new Node<>(element);
            y.prev = x;
            x.next = y;
            y.next = list.nil;
            list.nil.prev = y;
            x = y;
        }
        return list;
    }

    /**
     * Returns a copy of an existing doubly linked list with a sentinel.
     *
     * @param otherList the list to be copied
     * @return the copy of {@code otherList}
     */
    public static <E> ListWithSentinel<E> copyOf(ListWithSentinel<E> otherList) {
        ListWithSentinel<E> list = emptyList();
        if (otherList.nil.next == otherList.nil) {
            return list;
        }
        Node<E> x = list.nil;
        Node<E> z = otherList.nil.next;
        while (z != otherList.nil) {
            Node<E> y = new Node<>(z.key);
            y.prev = x;
            x.next = y;
            y.next = list.nil;
            list.nil.prev = y;
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
        Node<E> x = nil.next;
        while (x != nil) {
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
        Array<E> array = Array.withLength(getLength());
        Node<E> x = nil.next;
        int i = 1;
        while (x != nil) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
