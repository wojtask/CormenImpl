package pl.kwojtas.cormenimpl.util;

/**
 * Implements a doubly linked list.
 *
 * @param <T> the type of elements in the list
 */
public class List<T> {

    /**
     * Implements a doubly linked list's node.
     *
     * @param <U> the type of key in the node
     */
    public static class Node<U> {
        /**
         * The key.
         */
        public U key;

        /**
         * The previous node.
         */
        public Node<U> prev;

        /**
         * The next node.
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
     * The head of the list.
     */
    public Node<T> head;

    /**
     * Creates a list from given elements.
     *
     * @param elements the initial contents of the list
     */
    @SafeVarargs
    public List(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node<>(elements[0]);
        Node<T> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<T> y = new Node<>(elements[i]);
            y.prev = x;
            x.next = y;
            x = y;
        }
    }

    /**
     * Creates a doubly linked list by copying an existing doubly linked list.
     *
     * @param otherList the list to be copied
     */
    public List(List<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node<>(otherList.head.key);
        Node<T> x = head;
        Node<T> z = otherList.head.next;
        while (z != null) {
            Node<T> y = new Node<>(z.key);
            y.prev = x;
            x.next = y;
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
