package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table with a doubly linked list of free positions.
 *
 * @param <E> the type of elements in the hash table
 */
public class HashTableWithFreeList<E> extends ZeroBasedIndexedArray<HashTableWithFreeList.Element<E>> {

    /**
     * Implements a hash table's element.
     *
     * @param <F> the type of satellite data
     */
    public static class Element<F> {

        /**
         * The key.
         */
        public int key;

        /**
         * The satellite data.
         */
        public F data;

        /**
         * The index of the previous element.
         */
        public Integer prev;

        /**
         * The index of the next element.
         */
        public Integer next;

        /**
         * Creates an element from a given key and satellite data with no previous nor next element.
         *
         * @param key  the key of the new element
         * @param data the satellite data of the new element
         */
        public Element(int key, F data) {
            this.key = key;
            this.data = data;
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
            set(i, new HashTableWithFreeList.Element<>(i, null));
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
