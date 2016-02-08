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

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public ListWithSentinel(E... elements) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        if (elements.length == 0) {
            return;
        }
        Node<E> x = nil;
        for (E element : elements) {
            Node<E> y = new Node<>(element);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
            x = y;
        }
    }

    /**
     * Creates a doubly linked list with a sentinel by copying an existing doubly linked list with a sentinel.
     *
     * @param otherList the list to be copied
     */
    public ListWithSentinel(ListWithSentinel<E> otherList) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        Node<E> x = nil;
        Node<E> z = otherList.nil.next;
        while (z != otherList.nil) {
            Node<E> y = new Node<>(z.key);
            y.prev = x;
            x.next = y;
            y.next = nil;
            nil.prev = y;
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
