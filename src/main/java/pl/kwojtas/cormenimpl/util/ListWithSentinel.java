package pl.kwojtas.cormenimpl.util;

/**
 * Implements a doubly linked list with a sentinel.
 *
 * @param <T> the type of elements in a list
 */
public class ListWithSentinel<T> {

    /**
     * Implements a doubly linked list's node.
     *
     * @param <U> the type of the node's key
     */
    public static class Node<U> {

        /**
         * The key.
         */
        public U key;

        /**
         * The previous node in a doubly linked list.
         */
        public Node<U> prev;

        /**
         * The next node in a doubly linked list.
         */
        public Node<U> next;

        /**
         * Creates a node with a given key.
         *
         * @param key the key of the new node
         */
        public Node(U key) {
            this.key = key;
        }
    }

    /**
     * The sentinel node.
     */
    public Node<T> nil;

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public ListWithSentinel(T... elements) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        if (elements.length == 0) {
            return;
        }
        Node<T> x = nil;
        for (T element : elements) {
            Node<T> y = new Node<>(element);
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
    public ListWithSentinel(ListWithSentinel<T> otherList) {
        nil = new Node<>(null);
        nil.prev = nil.next = nil;
        Node<T> x = nil;
        Node<T> z = otherList.nil.next;
        while (z != otherList.nil) {
            Node<T> y = new Node<>(z.key);
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
        Node<T> x = nil.next;
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
    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
        Node<T> x = nil.next;
        int i = 1;
        while (x != nil) {
            array.set(i, x.key);
            i++;
            x = x.next;
        }
        return array;
    }

}
