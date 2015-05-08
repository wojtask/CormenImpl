package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table using chaining for collision resolution.
 *
 * @param <T> the type of elements in the hash table
 */
public class ChainedHashTable<T> extends ZeroBasedIndexedArray<List<Element<T>>> {

    /**
     * The hash function used in the hash table.
     */
    public HashFunction h;

    private ChainedHashTable(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        for (int i = 0; i <= length - 1; i++) {
            set(i, new List<>());
        }
        this.h = h;
    }

    /**
     * Creates an empty hash table with an underlying array of a given length and with a given hash function.
     *
     * @param length the length of the underlying array
     * @param h the hash function of the new hash table
     * @param <T> the type of elements in the new hash table
     * @return the empty hash table with an underlying array of length {@code length} and with hash function {@code h}
     */
    public static <T> ChainedHashTable<T> withLengthAndHashFunction(int length, HashFunction h) {
        return new ChainedHashTable<>(length, h);
    }

}
