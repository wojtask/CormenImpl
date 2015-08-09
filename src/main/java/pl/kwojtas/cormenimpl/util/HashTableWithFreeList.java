package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table with a doubly linked list of free positions.
 *
 * @param <E> the type of elements in the hash table
 */
public class HashTableWithFreeList<E> extends ZeroBasedIndexedArray<HashTableWithFreeList.Node<E>> {

    /**
     * Implements a doubly linked list's node.
     *
     * @param <F> the type of satellite data in the node's element
     */
    public static class Node<F> {

        /**
         * The element containing a key and satellite data.
         */
        public Element<F> element;

        /**
         * The index of the previous node.
         */
        public Integer prev;

        /**
         * The index of the next node.
         */
        public Integer next;

        /**
         * Creates an empty node with no previous node and no next node.
         */
        public Node() {
        }
    }

    /**
     * The index of the the free list's head.
     */
    public Integer free;

    /**
     * The hash function used in the hash table.
     */
    public HashFunction h;

    private HashTableWithFreeList(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        for (int i = 0; i <= length - 1; i++) {
            set(i, new Node<>());
        }
        free = 0;
        this.length = length;
        this.h = h;
    }

    /**
     * Creates an empty hash table with an underlying array of a given length and with a given hash function.
     *
     * @param length the length of the underlying array
     * @param h      the hash function of the new hash table
     * @param <E>    the type of elements in the new hash table
     * @return the empty hash table with an underlying array of length {@code length} and with hash function {@code h}
     */
    public static <E> HashTableWithFreeList<E> withLengthAndHashFunction(int length, HashFunction h) {
        return new HashTableWithFreeList<>(length, h);
    }

}
