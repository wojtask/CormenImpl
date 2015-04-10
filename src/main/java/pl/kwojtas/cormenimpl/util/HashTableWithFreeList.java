package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table with a doubly linked list of free positions.
 *
 * @param <T> the type of elements in the hash table
 */
public class HashTableWithFreeList<T> extends ZeroBasedIndexedArray<HashTableWithFreeList.Node<T>> {

    /**
     * Implements a doubly linked list's node.
     *
     * @param <U> the type of satellite data in the node's element
     */
    public static class Node<U> {

        /**
         * The element containing a key and a satellite data.
         */
        public Element<U> element;

        /**
         * The previous node.
         */
        public Integer prev;

        /**
         * The next node.
         */
        public Integer next;

        /**
         * Creates an empty node with no previous node and no next node.
         */
        public Node() { }
    }

    /**
     * The index of the head of the free list.
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
     * Creates an empty hash table of a given length of the underlying array and with a given hash function.
     *
     * @param length the length of the underlying array
     * @param h the hash function of the new hash table
     * @param <T> the type of elements in the new hash table
     * @return the empty hash table with underlying array of length {@code length} and with hash function {@code h}
     */
    public static <T> HashTableWithFreeList<T> withLengthAndHashFunction(int length, HashFunction h) {
        return new HashTableWithFreeList<>(length, h);
    }

}
