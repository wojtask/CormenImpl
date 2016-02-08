package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a hash table using chaining for collision resolution.
 *
 * @param <E> the type of elements in the hash table
 */
public class ChainedHashTable<E> extends ZeroBasedIndexedArray<ChainedHashTable.Element<E>> {

    /**
     * Implements a chained hash table's element.
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
        public Element<F> prev;

        /**
         * The index of the next element.
         */
        public Element<F> next;

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
     * The hash function used in the hash table.
     */
    public HashFunction h;

    private ChainedHashTable(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
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
    public static <E> ChainedHashTable<E> withLengthAndHashFunction(int length, HashFunction h) {
        return new ChainedHashTable<>(length, h);
    }

}
