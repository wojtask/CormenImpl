package pl.kwojtas.cormenimpl.util;

/**
 * Implements a singly linked list.
 *
 * @param <T> the type of elements in the list
 */
public class SinglyLinkedList<T> {

    /**
     * Implements a singly linked list's node.
     *
     * @param <U> the type of the node's key
     */
    public static class Node<U> {

        /**
         * The key.
         */
        public U key;

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
    public SinglyLinkedList(T... elements) {
        if (elements.length == 0) {
            return;
        }
        head = new Node<>(elements[0]);
        Node<T> x = head;
        for (int i = 1; i < elements.length; i++) {
            Node<T> y = new Node<>(elements[i]);
            x.next = y;
            x = y;
        }
    }

    /**
     * Creates a singly linked list by copying an existing singly linked list.
     *
     * @param otherList the list to be copied
     */
    public SinglyLinkedList(SinglyLinkedList<T> otherList) {
        if (otherList.head == null) {
            return;
        }
        head = new Node<>(otherList.head.key);
        Node<T> x = head;
        Node<T> z = otherList.head.next;
        while (z != null) {
            Node<T> y = new Node<>(z.key);
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
     * Transforms the list to an array.
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
