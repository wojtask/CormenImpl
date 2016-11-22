package pl.kwojtas.cormenimpl.datastructure;

import java.util.HashMap;
import java.util.Map;

import static pl.kwojtas.cormenimpl.Chapter5.random;

/**
 * Implements a XOR linked list.
 *
 * @param <E> the type of elements in the list
 */
public class XorLinkedList<E> {

    /**
     * Implements a XOR linked list's node.
     *
     * @param <F> the type of the node's key
     */
    public static class Node<F> {

        /**
         * The key.
         */
        public F key;

        /**
         * The (simulated) address of the node.
         */
        public final int address;

        /**
         * The value being XOR of the previous node's address and the next node's address.
         */
        public int np;

        private Node(F key, int address) {
            this.key = key;
            this.address = address;
        }
    }

    private Map<Integer, Node<E>> addressToNode;

    /**
     * Creates a new XOR list's node and registers it in the address-to-node map.
     * The address of the new node is a unique random number from <tt>1..2<sup>31</sup> - 1</tt>
     *
     * @param key the key of the new node
     * @return the new node
     */
    public Node<E> addNode(E key) {
        int address;
        do {
            address = random(1, Integer.MAX_VALUE);
        } while (addressToNode.containsKey(address));
        Node<E> x = new Node<>(key, address);
        addressToNode.put(address, x);
        return x;
    }

    /**
     * The head of the list.
     */
    public Node<E> head;

    /**
     * The tail of the list.
     */
    public Node<E> tail;

    private XorLinkedList() {
        addressToNode = new HashMap<>();
    }

    /**
     * Returns an empty list (a list containing 0 elements).
     *
     * @return the empty XOR linked list
     */
    public static <E> XorLinkedList<E> emptyList() {
        return new XorLinkedList<>();
    }

    /**
     * Returns a copy of an existing XOR linked list.
     *
     * @param otherList the list to be copied
     * @return the copy of {@code otherList}
     */
    public static <E> XorLinkedList<E> copyOf(XorLinkedList<E> otherList) {
        XorLinkedList<E> list = emptyList();
        if (otherList.head == null) {
            return list;
        }
        list.head = list.addNode(otherList.head.key);
        Node<E> x = list.head;
        Node<E> y = null;
        Node<E> x_ = otherList.byAddress(otherList.head.np);
        Node<E> y_ = otherList.head;
        while (x_ != null) {
            Node<E> z = list.addNode(x_.key);
            z.np = x.address;
            x.np = z.address ^ (y != null ? y.address : 0);
            y = x;
            x = z;
            Node<E> z_ = otherList.byAddress(x_.np ^ (y_ != null ? y_.address : 0));
            y_ = x_;
            x_ = z_;
        }
        list.tail = x;
        return list;
    }

    /**
     * Retrieves a node from the list by its address.
     *
     * @param address the address of the node
     * @return the node of address {@code address} in the list, or {@code null} if the list does not contain such node
     */
    public Node<E> byAddress(int address) {
        return addressToNode.get(address);
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int getLength() {
        int length = 0;
        Node<E> x = head;
        Node<E> y = null;
        while (x != null) {
            length++;
            XorLinkedList.Node<E> z = byAddress(x.np ^ (y != null ? y.address : 0));
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
    public Array<E> toArray() {
        Array<E> array = Array.ofLength(getLength());
        Node<E> x = head;
        Node<E> y = null;
        int i = 1;
        while (x != null) {
            array.set(i, x.key);
            i++;
            XorLinkedList.Node<E> z = byAddress(x.np ^ (y != null ? y.address : 0));
            y = x;
            x = z;
        }
        return array;
    }

}
