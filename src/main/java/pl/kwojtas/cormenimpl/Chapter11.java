package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.ChainedHashTable;
import pl.kwojtas.cormenimpl.util.Element;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.util.HashTableWithProbing;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.Stack;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

/**
 * Implements algorithms and data structures from Chapter 11.
 */
public final class Chapter11 {

    private Chapter11() { }

    /**
     *
     * <p><span style="font-variant:small-caps;">Direct-Address-Search</span> from subchapter 11.1.</p>
     *
     * @param T
     * @param k
     * @param <T>
     * @return
     */
    public static <T> Element<T> directAddressSearch(ZeroBasedIndexedArray<Element<T>> T, int k) {
        return T.at(k);
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Direct-Address-Insert</span> from subchapter 11.1.</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void directAddressInsert(ZeroBasedIndexedArray<Element<T>> T, Element<T> x) {
        T.set(x.key, x);
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Direct-Address-Delete</span> from subchapter 11.1</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void directAddressDelete(ZeroBasedIndexedArray<Element<T>> T, Element<T> x) {
        T.set(x.key, null);
    }

    /**
     *
     * <p>Solution to exercise 11.1-1.</p>
     *
     * @param T
     * @param <T>
     * @return
     */
    public static <T> Element<T> directAddressMaximum(ZeroBasedIndexedArray<Element<T>> T) {
        int m = T.length;
        for (int i = m - 1; i >= 0; i--) {
            if (T.at(i) != null) {
                return T.at(i);
            }
        }
        return null;
    }

    /**
     *
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V
     * @param k
     * @return
     */
    public static int bitVectorSearch(ZeroBasedIndexedArray<Integer> V, int k) {
        return V.at(k);
    }

    /**
     *
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V
     * @param k
     */
    public static void bitVectorInsert(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 1);
    }

    /**
     *
     * <p>Solution to exercise 11.1-2.</p>
     *
     * @param V
     * @param k
     */
    public static void bitVectorDelete(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 0);
    }

    /**
     *
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T
     * @param k
     * @param <T>
     * @return
     */
    public static <T> Element<T> directAddressSearch_(ZeroBasedIndexedArray<List<Element<T>>> T, int k) {
        List<Element<T>> list = T.at(k);
        if (list.head != null) {
            return list.head.key;
        }
        return null;
    }

    /**
     *
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void directAddressInsert_(ZeroBasedIndexedArray<List<Element<T>>> T, Element<T> x) {
        List<Element<T>> list = T.at(x.key);
        Chapter10.listInsert(list, new List.Node<>(x));
    }

    /**
     *
     * <p>Solution to exercise 11.1-3.</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void directAddressDelete_(ZeroBasedIndexedArray<List<Element<T>>> T, Element<T> x) {
        List<Element<T>> list = T.at(x.key);
        List.Node<Element<T>> node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
    }

    public static class HugeArray<T> {
        public ZeroBasedIndexedArray<Integer> T;
        public Stack<Element<T>> S;

        public HugeArray(int size, int capacity) {
            T = ZeroBasedIndexedArray.withLength(size);
            for (int i = 0; i <= size - 1; i++) {
                T.set(i, 0); // initializing T as it contains nulls; the initializer can be anything nonnull
            }
            S = Stack.withLength(capacity);
        }
    }

    /**
     *
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H
     * @param k
     * @param <T>
     * @return
     */
    public static <T> Element<T> hugeArraySearch(HugeArray<T> H, int k) {
        if (1 <= H.T.at(k) && H.T.at(k) <= H.S.top && H.S.at(H.T.at(k)).key == k) {
            return new Element<>(H.S.at(H.T.at(k)));
        }
        return null;
    }

    /**
     *
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H
     * @param x
     * @param <T>
     */
    public static <T> void hugeArrayInsert(HugeArray<T> H, Element<T> x) {
        Chapter10.push(H.S, x);
        H.T.set(x.key, H.S.top);
    }

    /**
     *
     * <p>Solution to exercise 11.1-4.</p>
     *
     * @param H
     * @param x
     * @param <T>
     */
    public static <T> void hugeArrayDelete(HugeArray<T> H, Element<T> x) {
        int k = x.key;
        Element<T> y = Chapter10.pop(H.S);
        H.S.set(H.T.at(k), y);
        H.T.set(y.key, H.T.at(k));
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Chained-Hash-Insert</span> from subchapter 11.2.</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void chainedHashInsert(ChainedHashTable<T> T, Element<T> x) {
        List<Element<T>> list = T.at(T.h.compute(x.key));
        Chapter10.listInsert(list, new List.Node<>(x));
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Chained-Hash-Search</span> from subchapter 11.2.</p>
     *
     * @param T
     * @param k
     * @param <T>
     * @return
     */
    public static <T> Element<T> chainedHashSearch(ChainedHashTable<T> T, int k) {
        List<Element<T>> list = T.at(T.h.compute(k));
        List.Node<Element<T>> x = list.head;
        while (x != null) {
            if (x.key.key == k) {
                return x.key;
            }
            x = x.next;
        }
        return null;
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Chained-Hash-Delete</span> from subchapter 11.2.</p>
     *
     * @param T
     * @param x
     * @param <T>
     */
    public static <T> void chainedHashDelete(ChainedHashTable<T> T, Element<T> x) {
        List<Element<T>> list = T.at(T.h.compute(x.key));
        List.Node<Element<T>> node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
    }

    /**
     *
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T
     * @param x
     * @param <T>
     * @return
     */
    public static <T> int inPlaceChainedHashInsert(HashTableWithFreeList<T> T, Element<T> x) {
        int position = T.h.compute(x.key);
        if (T.at(position).element == null) {
            allocateHashTableNode(T, position);
            T.at(position).next = T.at(position).prev = null;
            T.at(position).element = x;
            return position;
        }
        HashTableWithFreeList.Node<T> otherNode = T.at(position);
        int otherElementPosition = T.h.compute(otherNode.element.key);
        if (otherElementPosition == position) {
            int newPosition = allocateHashTableNode(T, T.free);
            T.at(newPosition).element = x;
            T.at(newPosition).prev = position;
            T.at(newPosition).next = otherNode.next;
            otherNode.next = newPosition;
            return newPosition;
        } else {
            int newPosition = allocateHashTableNode(T, T.free);
            T.at(newPosition).element = otherNode.element;
            if (otherNode.prev != null) {
                T.at(otherNode.prev).next = newPosition;
            }
            if (otherNode.next != null) {
                T.at(otherNode.next).prev = newPosition;
            }
            T.at(position).element = x;
            return position;
        }
    }

    private static <T> int allocateHashTableNode(HashTableWithFreeList<T> T, int position) {
        if (T.free == null) {
            throw new RuntimeException("overflow");
        }
        HashTableWithFreeList.Node<T> node = T.at(position);
        if (node.prev != null) {
            T.at(node.prev).next = node.next;
        }
        if (node.next != null) {
            T.at(node.next).prev = node.prev;
        }
        if (T.free == position) {
            T.free = node.next;
        }
        return position;
    }

    /**
     *
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T
     * @param k
     * @param <T>
     * @return
     */
    public static <T> Integer inPlaceChainedHashSearch(HashTableWithFreeList<T> T, int k) {
        Integer position = T.h.compute(k);
        while (position != null && T.at(position).element != null) {
            if (T.at(position).element.key == k) {
                return position;
            }
            position = T.at(position).next;
        }
        return null;
    }

    /**
     *
     * <p>Solution to exercise 11.2-4.</p>
     *
     * @param T
     * @param position
     * @param <T>
     */
    public static <T> void inPlaceChainedHashDelete(HashTableWithFreeList<T> T, int position) {
        HashTableWithFreeList.Node node = T.at(position);
        if (node.prev != null) {
            T.at(node.prev).next = node.next;
        }
        if (node.next != null) {
            T.at(node.next).prev = node.prev;
        }
        node.element = null;
        node.prev = null;
        node.next = T.free;
        if (T.free != null) {
            T.at(T.free).prev = position;
        }
        T.free = position;
    }

    /**
     *
     * <p><span style="font-variant:small-caps;">Hash-Insert</span> from subchapter 11.4.</p>
     *
     * @param T
     * @param k
     * @return
     */
    public static int hashInsert(HashTableWithProbing T, int k) {
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
     *
     * <p><span style="font-variant:small-caps;">Hash-Search</span> from subchapter 11.4.</p>
     *
     * @param T
     * @param k
     * @return
     */
    public static Integer hashSearch(HashTableWithProbing T, int k) {
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
     *
     * <p><span style="font-variant:small-caps;">Hash-Delete</span> from solution to exercise 11.4-2.</p>
     *
     * @param T
     * @param k
     */
    public static void hashDelete(HashTableWithProbing T, int k) {
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
     *
     * <p><span style="font-variant:small-caps;">Hash-Insert'</span> from solution to exercise 11.4-2.</p>
     *
     * @param T
     * @param k
     * @return
     */
    public static int hashInsert_(HashTableWithProbing T, int k) {
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
     *
     * <p>Problem 11-3.</p>
     *
     * @param T
     * @param k
     * @param h
     * @return
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
