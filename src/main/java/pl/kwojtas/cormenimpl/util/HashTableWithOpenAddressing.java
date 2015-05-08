package pl.kwojtas.cormenimpl.util;

/**
 * Implements a hash table using open addressing.
 */
public class HashTableWithOpenAddressing extends ZeroBasedIndexedArray<Integer> {

    /**
     * The hash function used in the hash table.
     */
    public HashProbingFunction h;

    private HashTableWithOpenAddressing(int length, HashProbingFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        this.h = h;
    }

    /**
     * Creates an empty hash table with an underlying array of a given length and with a given hash function.
     *
     * @param length the length of the underlying array
     * @param h the hash function of the new hash table
     * @return the empty hash table with an underlying array of length {@code length} and with hash function {@code h}
     */
    public static HashTableWithOpenAddressing withLengthAndHashFunction(int length, HashProbingFunction h) {
        return new HashTableWithOpenAddressing(length, h);
    }

}
