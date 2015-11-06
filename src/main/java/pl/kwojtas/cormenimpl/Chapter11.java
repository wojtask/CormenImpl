package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.ChainedHashTable;
import pl.kwojtas.cormenimpl.util.DirectAddressTable;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.util.HashTableWithOpenAddressing;
import pl.kwojtas.cormenimpl.util.HugeArray;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static pl.kwojtas.cormenimpl.Chapter10.pop;
import static pl.kwojtas.cormenimpl.Chapter10.push;

/**
 * Implements algorithms and data structures from Chapter 11.
 */
public final class Chapter11 {

    private Chapter11() {
    }

    /**
     * Searches for an element in a direct-address table.
     * <p><span style="font-variant:small-caps;">Direct-Address-Search</span> from subchapter 11.1.</p>
     *
     * @param T   the direct-address table to scan
     * @param k   the key of the element to find
     * @param <E> the type of elements' values in {@code T}
     * @return the element of key {@code k} in table {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static <E> DirectAddressTable.Element<E> directAddressSearch(DirectAddressTable<E> T, int k) {
        return T.at(k);
    }

    /**
     * Inserts an element into a direct-address table.
     * <p><span style="font-variant:small-caps;">Direct-Address-Insert</span> from subchapter 11.1.</p>
     *
     * @param T   the direct-address table
     * @param x   the element to insert
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void directAddressInsert(DirectAddressTable<E> T, DirectAddressTable.Element<E> x) {
        T.set(x.key, x);
    }

    /**
     * Deletes an element from a direct-address table.
     * <p><span style="font-variant:small-caps;">Direct-Address-Delete</span> from subchapter 11.1</p>
     *
     * @param T   the direct-address table
     * @param x   the element from {@code T} to delete
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void directAddressDelete(DirectAddressTable<E> T, DirectAddressTable.Element<E> x) {
        T.set(x.key, null);
    }

    /**
     * Searches for an element with the smallest key in a direct-address table.
     * <p>Solution to exercise 11.1-1.</p>
     *
     * @param T   the direct-address table to scan
     * @param <E> the type of elements' values in {@code T}
     * @return the element with the smallest key in {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static <E> DirectAddressTable.Element<E> directAddressMaximum(DirectAddressTable<E> T) {
        int m = T.length;
        for (int i = m - 1; i >= 0; i--) {
            if (T.at(i) != null) {
                return T.at(i);
            }
        }
        return null;
    }

    /**
     * Searches for an element in a bit vector.
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V the 0-based indexed array of bits representing the bit vector
     * @param k the key of the element to find
     * @return 1 if {@code V} contains element of key {@code k}, 0 otherwise
     */
    public static int bitVectorSearch(ZeroBasedIndexedArray<Integer> V, int k) {
        return V.at(k);
    }

    /**
     * Inserts an element into a bit vector.
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V the 0-based indexed array of bits representing the bit vector
     * @param k the key of the element to insert
     */
    public static void bitVectorInsert(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 1);
    }

    /**
     * Deletes an element from a bit vector.
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V the 0-based indexed array of bits representing the bit vector
     * @param k the key of the element to delete
     */
    public static void bitVectorDelete(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 0);
    }

    /**
     * Searches for an element in a direct-address table that does not require keys of elements to be distinct.
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T   the direct-address table to scan
     * @param k   the key of the element to find
     * @param <E> the type of elements' values in {@code T}
     * @return one of the elements of key {@code k} in table {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static <E> DirectAddressTable.Element<E> directAddressSearch_(DirectAddressTable<E> T, int k) {
        return T.at(k);
    }

    /**
     * Inserts an element into a direct-address table that does not require keys of elements to be distinct.
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T   the direct-address table
     * @param x   the element to insert
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void directAddressInsert_(DirectAddressTable<E> T, DirectAddressTable.Element<E> x) {
        DirectAddressTable.Element<E> y = T.at(x.key);
        x.next = T.at(x.key);
        if (y != null) {
            T.at(x.key).prev = x;
        }
        T.set(x.key, x);
        x.prev = null;
    }

    /**
     * Deletes an element from a direct-address table that does not require keys of elements to be distinct.
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T   the direct-address table
     * @param x   the element from {@code T} to delete
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void directAddressDelete_(DirectAddressTable<E> T, DirectAddressTable.Element<E> x) {
        if (x.prev != null) {
            x.prev.next = x.next;
        } else {
            T.set(x.key, x.next);
        }
        if (x.next != null) {
            x.next.prev = x.prev;
        }
    }

    /**
     * Searches for an element in a dictionary using direct addressing on a huge array.
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H   the huge array representing the dictionary to scan
     * @param k   the key of the element to find
     * @param <E> the type of elements' values in {@code H}
     * @return the element of key {@code k} in {@code H}, or {@code null} if {@code H} does not contain such element
     */
    public static <E> HugeArray.Element<E> hugeArraySearch(HugeArray<E> H, int k) {
        if (1 <= H.T.at(k) && H.T.at(k) <= H.S.top && H.S.at(H.T.at(k)).key == k) {
            return H.S.at(H.T.at(k));
        }
        return null;
    }

    /**
     * Inserts an element into a dictionary using direct addressing on a huge array.
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H   the huge array representing the dictionary
     * @param x   the element to insert
     * @param <E> the type of elements' values in {@code H}
     */
    public static <E> void hugeArrayInsert(HugeArray<E> H, HugeArray.Element<E> x) {
        push(H.S, x);
        H.T.set(x.key, H.S.top);
    }

    /**
     * Deletes an element from a dictionary using direct addressing on a huge array.
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H   the huge array representing the dictionary
     * @param x   the element to delete
     * @param <E> the type of elements' values in {@code H}
     */
    public static <E> void hugeArrayDelete(HugeArray<E> H, HugeArray.Element<E> x) {
        int k = x.key;
        HugeArray.Element<E> y = pop(H.S);
        H.S.set(H.T.at(k), y);
        H.T.set(y.key, H.T.at(k));
    }

    /**
     * Inserts an element into a hash table using chaining for collision resolution.
     * <p><span style="font-variant:small-caps;">Chained-Hash-Insert</span> from subchapter 11.2.</p>
     *
     * @param T   the hash table using chaining for collision resolution
     * @param x   the element to insert
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void chainedHashInsert(ChainedHashTable<E> T, ChainedHashTable.Element<E> x) {
        int hash = T.h.compute(x.key);
        x.next = T.at(hash);
        if (T.at(hash) != null) {
            T.at(hash).prev = x;
        }
        x.prev = null;
        T.set(hash, x);
    }

    /**
     * Searches for an element in a hash table using chaining for collision resolution.
     * <p><span style="font-variant:small-caps;">Chained-Hash-Search</span> from subchapter 11.2.</p>
     *
     * @param T   the hash table using chaining for collision resolution
     * @param k   the key of the element to find
     * @param <E> the type of elements' values in {@code T}
     * @return the element of key {@code k} in {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static <E> ChainedHashTable.Element<E> chainedHashSearch(ChainedHashTable<E> T, int k) {
        int hash = T.h.compute(k);
        ChainedHashTable.Element<E> x = T.at(hash);
        while (x != null) {
            if (x.key == k) {
                return x;
            }
            x = x.next;
        }
        return null;
    }

    /**
     * Deletes an element from a hash table using chaining for collision resolution.
     * <p><span style="font-variant:small-caps;">Chained-Hash-Delete</span> from subchapter 11.2.</p>
     *
     * @param T   the hash table using chaining for collision resolution
     * @param x   the element to delete
     * @param <E> the type of elements' values in {@code T}
     */
    public static <E> void chainedHashDelete(ChainedHashTable<E> T, ChainedHashTable.Element<E> x) {
        int hash = T.h.compute(x.key);
        if (x.prev != null) {
            x.prev.next = x.next;
        } else {
            T.set(hash, x.next);
        }
        if (x.next != null) {
            x.next.prev = x.prev;
        }
    }

    /**
     * Inserts an element into a hash table using an in-place chaining for collision resolution.
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T   the hash table using an in-place chaining for collision resolution
     * @param x   the element to insert
     * @param <E> the type of elements' values in {@code T}
     * @return the index in {@code T} allocated for {@code x}
     */
    public static <E> int inPlaceChainedHashInsert(HashTableWithFreeList<E> T, HashTableWithFreeList.Element<E> x) {
        int position = T.h.compute(x.key);
        if (T.at(position).data == null) {
            allocateHashTableNode(T, position);
            T.at(position).next = T.at(position).prev = null;
            T.set(position, x);
            return position;
        }
        HashTableWithFreeList.Element<E> otherElement = T.at(position);
        int otherElementPosition = T.h.compute(otherElement.key);
        if (otherElementPosition == position) {
            int newPosition = allocateHashTableNode(T, T.free);
            T.set(newPosition, x);
            T.at(newPosition).prev = position;
            T.at(newPosition).next = otherElement.next;
            otherElement.next = newPosition;
            return newPosition;
        } else {
            int newPosition = allocateHashTableNode(T, T.free);
            T.set(newPosition, otherElement);
            if (otherElement.prev != null) {
                T.at(otherElement.prev).next = newPosition;
            }
            if (otherElement.next != null) {
                T.at(otherElement.next).prev = newPosition;
            }
            T.set(position, x);
            return position;
        }
    }

    private static <E> int allocateHashTableNode(HashTableWithFreeList<E> T, Integer position) {
        if (T.free == null) {
            throw new RuntimeException("overflow");
        }
        HashTableWithFreeList.Element<E> element = T.at(position);
        if (element.next != null) {
            T.at(element.next).prev = element.prev;
        }
        if (T.free.equals(position)) {
            T.free = element.next;
        }
        return position;
    }

    /**
     * Deletes an element from a hash table using an in-place chaining for collision resolution.
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T        the hash table using an in-place chaining for collision resolution
     * @param position the position of the element to delete
     * @param <E>      the type of elements' values in {@code T}
     */
    public static <E> void inPlaceChainedHashDelete(HashTableWithFreeList<E> T, int position) {
        HashTableWithFreeList.Element<E> element = T.at(position);
        if (element.prev != null) {
            T.at(element.prev).next = element.next;
        }
        if (element.next != null) {
            T.at(element.next).prev = element.prev;
        }
        element.data = null;
        element.prev = null;
        element.next = T.free;
        if (T.free != null) {
            T.at(T.free).prev = position;
        }
        T.free = position;
    }

    /**
     * Searches for an element in a hash table using an in-place chaining for collision resolution.
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T   the hash table using an in-place chaining for collision resolution
     * @param k   the key of the element to find
     * @param <E> the type of elements' values in {@code T}
     * @return the element of key {@code k} in {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static <E> Integer inPlaceChainedHashSearch(HashTableWithFreeList<E> T, int k) {
        Integer position = T.h.compute(k);
        while (position != null && T.at(position) != null) {
            if (T.at(position).key == k) {
                return position;
            }
            position = T.at(position).next;
        }
        return null;
    }

    /**
     * Inserts an element into a hash table using open addressing.
     * <p><span style="font-variant:small-caps;">Hash-Insert</span> from subchapter 11.4.</p>
     *
     * @param T the hash table using open addressing
     * @param k the key of the element to insert
     * @return the position in {@code T} allocated for the inserted element
     * @throws RuntimeException if {@code T} is full
     */
    public static int hashInsert(HashTableWithOpenAddressing T, int k) {
        int m = T.length;
        int i = 0;
        do {
            int j = T.h.compute(k, i);
            if (T.at(j) == null) {
                T.set(j, k);
                return j;
            } else {
                i++;
            }
        } while (i != m);
        throw new RuntimeException("hash table overflow");
    }

    /**
     * Searches for an element in a hash table using open addressing.
     * <p><span style="font-variant:small-caps;">Hash-Search</span> from subchapter 11.4.</p>
     *
     * @param T the hash table using open addressing
     * @param k the key of the element to find
     * @return the element of key {@code k}, or {@code null} if {@code T} does not contain such element
     */
    public static Integer hashSearch(HashTableWithOpenAddressing T, int k) {
        int m = T.length;
        int i = 0;
        int j;
        do {
            j = T.h.compute(k, i);
            if (T.at(j) != null && T.at(j) == k) {
                return j;
            }
            i++;
        } while (T.at(j) != null && i != m);
        return null;
    }

    public static final Integer DELETED = Integer.MAX_VALUE;

    /**
     * Deletes an element from a hash table using open addressing.
     * <p><span style="font-variant:small-caps;">Hash-Delete</span> from solution to exercise 11.4-2.</p>
     *
     * @param T the hash table using open addressing
     * @param k the key of the element to delete
     */
    public static void hashDelete(HashTableWithOpenAddressing T, int k) {
        int m = T.length;
        int i = 0;
        int j;
        do {
            j = T.h.compute(k, i);
            if (T.at(j) == k) {
                T.set(j, DELETED);
                return;
            }
            i++;
        } while (T.at(j) != null && i != m);
    }

    /**
     * Inserts an element into a hash table using open addressing - an alternative version.
     * <p><span style="font-variant:small-caps;">Hash-Insert'</span> from solution to exercise 11.4-2.</p>
     *
     * @param T the hash table using open addressing
     * @param k the key of the element to insert
     * @return the position in {@code T} allocated for the inserted element
     * @throws RuntimeException if {@code T} is full
     */
    public static int hashInsert_(HashTableWithOpenAddressing T, int k) {
        int m = T.length;
        int i = 0;
        do {
            int j = T.h.compute(k, i);
            if (T.at(j) == null || T.at(j).equals(DELETED)) {
                T.set(j, k);
                return j;
            } else {
                i++;
            }
        } while (i != m);
        throw new RuntimeException("hash table overflow");
    }

    /**
     * Searches for an element in a hash table using open addressing with quadratic probing.
     * <p>Problem 11-3.</p>
     *
     * @param T the hash table using open addressing with quadratic probing
     * @param k the key of the element to find
     * @param h the quadratic probing hash function
     * @return the element of key {@code k} in {@code T}, or {@code null} if {@code T} does not contain such element
     */
    public static Integer quadraticProbingSearch(ZeroBasedIndexedArray<Integer> T, int k, HashFunction h) {
        int m = T.length;
        int i = h.compute(k);
        int j = 0;
        while (true) {
            if (T.at(i) == null) {
                return null;
            }
            if (T.at(i) == k) {
                return i;
            }
            j++;
            if (j == m) {
                return null;
            } else {
                i = (i + j) % m;
            }
        }
    }

}
