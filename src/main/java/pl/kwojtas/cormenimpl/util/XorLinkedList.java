package pl.kwojtas.cormenimpl.util;

import java.util.HashMap;
import java.util.Map;

import static pl.kwojtas.cormenimpl.Chapter5.random;

/**
 * Implements a XOR linked list.
 *
 * @param <T> the type of elements in the list
 */
public class XorLinkedList<T> {

    /**
     * Implements a XOR linked list's node.
     *
     * @param <U> the type of the node's key
     */
    public static class Node<U> {

        /**
         * The key.
         */
        public U key;

        /**
         * The (simulated) address of the node.
         */
        public final int address;

        /**
         * The value being XOR of the previous node's address and the next node's address.
         */
        public int np;

        private Node(U key, int address) {
            this.key = key;
            this.address = address;
        }
    }

    private Map<Integer, Node<T>> addressToNode;

    /**
     * Creates a new XOR list's node and registers it in the address-to-node map.
     * The address of the new node is a unique random number from <tt>1..2<sup>31</sup> - 1</tt>
     *
     * @param key the key of the new node
     * @return the new node
     */
    public Node<T> registerNode(T key) {
        int address;
        do {
            address = random(1, Integer.MAX_VALUE);
        } while (addressToNode.containsKey(address));
        Node<T> x = new Node<>(key, address);
        addressToNode.put(address, x);
        return x;
    }

    /**
     * The head of the list.
     */
    public Node<T> head;

    /**
     * The tail of the list.
     */
    public Node<T> tail;

    /**
     * Creates an empty XOR linked list.
     */
    public XorLinkedList() {
        addressToNode = new HashMap<>();
    }

    /**
     * Creates a XOR linked list by copying an existing XOR linked list.
     *
     * @param otherList the list to be copied
     */
    public XorLinkedList(XorLinkedList<T> otherList) {
        addressToNode = new HashMap<>();
        if (otherList.head == null) {
            return;
        }
        head = registerNode(otherList.head.key);
        Node<T> x = head;
        Node<T> y = null;
        Node<T> x_ = otherList.byAddress(otherList.head.np);
        Node<T> y_ = otherList.head;
        while (x_ != null) {
            Node<T> z = registerNode(x_.key);
            z.np = x.address;
            x.np = z.address ^ (y != null ? y.address : 0);
            y = x;
            x = z;
            Node<T> z_ = otherList.byAddress(x_.np ^ (y_ != null ? y_.address : 0));
            y_ = x_;
            x_ = z_;
        }
        tail = x;
    }

    /**
     * Retrieves a node from the list by its address.
     *
     * @param address the address of the node
     * @return the node of address {@code address} in the list, or {@code null} if the list does not contain such node
     */
    public Node<T> byAddress(int address) {
        return addressToNode.get(address);
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int getLength() {
        int length = 0;
        Node<T> x = head;
        Node<T> y = null;
        while (x != null) {
            length++;
            XorLinkedList.Node<T> z = byAddress(x.np ^ (y != null ? y.address : 0));
            y = x;
            x = z;
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
        Node<T> y = null;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            XorLinkedList.Node<T> z = byAddress(x.np ^ (y != null ? y.address : 0));
            y = x;
            x = z;
        }
        return array;
    }

}
