package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table with a doubly linked list of free positions.
 *
 * @param <E> the type of elements in the hash table
 */
public class HashTableWithFreeList<E> extends ZeroBasedIndexedArray<HashTableWithFreeList.Position<E>> {

    /**
     * Implements a hash table's position.
     */
    public static class Position<F> {

        /**
         * The pointer to the element on an occupied position, or {@code null} on a free position.
         */
        public Element<F> element;

        /**
         * On a free position -- the index of the previous position on the free list.
         * On an occupied position -- equals to {@code length}.
         */
        public int prev;

        /**
         * On a free position -- the index of the next position on the free list.
         * On an occupied position -- the index of the next position on the list of elements with the same hash.
         */
        public int next;

        /**
         * Creates a free position from given indices to a previous and a next position.
         *
         * @param prev the index to a previous position
         * @param next the index to a next position
         */
        public Position(Integer prev, Integer next) {
            this.prev = prev;
            this.next = next;
        }
    }

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
         * Creates an element from a given key and satellite data.
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
    public int F;

    /**
     * The hash function used in the hash table.
     */
    public HashFunction h;

    private HashTableWithFreeList(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        for (int i = 0; i <= length - 1; i++) {
            set(i, new Position<>(i - 1, i + 1 < length ? i + 1 : -1));
        }
        F = 0;
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
