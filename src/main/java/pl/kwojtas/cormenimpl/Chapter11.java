package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.ChainedHashTable;
import pl.kwojtas.cormenimpl.util.Element;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.util.HashTableWithProbing;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.Stack;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

public final class Chapter11 {

    private Chapter11() { }

    // subchapter 11.1
    public static <T> Element<T> directAddressSearch(ZeroBasedIndexedArray<Element<T>> T, int k) {
        return T.at(k);
    }

    // subchapter 11.1
    public static <T> void directAddressInsert(ZeroBasedIndexedArray<Element<T>> T, Element<T> x) {
        T.set(x.key, x);
    }

    // subchapter 11.1
    public static <T> void directAddressDelete(ZeroBasedIndexedArray<Element<T>> T, Element<T> x) {
        T.set(x.key, null);
    }

    // solution of 11.1-1
    public static <T> Element<T> directAddressMaximum(ZeroBasedIndexedArray<Element<T>> T) {
        int m = T.length;
        for (int i = m - 1; i >= 0; i--) {
            if (T.at(i) != null) {
                return T.at(i);
            }
        }
        return null;
    }

    // solution of 11.1-2
    public static int bitVectorSearch(ZeroBasedIndexedArray<Integer> V, int k) {
        return V.at(k);
    }

    // solution of 11.1-2
    public static void bitVectorInsert(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 1);
    }

    // solution of 11.1-2
    public static void bitVectorDelete(ZeroBasedIndexedArray<Integer> V, int k) {
        V.set(k, 0);
    }

    // solution of 11.1-3
    public static <T> Element<T> directAddressSearch_(ZeroBasedIndexedArray<List<Element<T>>> T, int k) {
        List<Element<T>> list = T.at(k);
        if (list.head != null) {
            return list.head.key;
        }
        return null;
    }

    // solution of 11.1-3
    public static <T> void directAddressInsert_(ZeroBasedIndexedArray<List<Element<T>>> T, Element<T> x) {
        List<Element<T>> list = T.at(x.key);
        Chapter10.listInsert(list, new List.Node<>(x));
    }

    // solution of 11.1-3
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

    // solution of 11.1-4
    public static <T> Element<T> hugeArraySearch(HugeArray<T> H, int k) {
        if (1 <= H.T.at(k) && H.T.at(k) <= H.S.top && H.S.at(H.T.at(k)).key == k) {
            return new Element<>(H.S.at(H.T.at(k)));
        }
        return null;
    }

    // solution of 11.1-4
    public static <T> void hugeArrayInsert(HugeArray<T> H, Element<T> x) {
        Chapter10.push(H.S, x);
        H.T.set(x.key, H.S.top);
    }

    // solution of 11.1-4
    public static <T> void hugeArrayDelete(HugeArray<T> H, Element<T> x) {
        int k = x.key;
        Element<T> y = Chapter10.pop(H.S);
        H.S.set(H.T.at(k), y);
        H.T.set(y.key, H.T.at(k));
    }

    // subchapter 11.2
    public static <T> void chainedHashInsert(ChainedHashTable<T> T, Element<T> x) {
        List<Element<T>> list = T.at(T.h.compute(x.key));
        Chapter10.listInsert(list, new List.Node<>(x));
    }

    // subchapter 11.2
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

    // subchapter 11.2
    public static <T> void chainedHashDelete(ChainedHashTable<T> T, Element<T> x) {
        List<Element<T>> list = T.at(T.h.compute(x.key));
        List.Node<Element<T>> node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
    }

    // solution of 11.2-4
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

    // solution of 11.2-4
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

    // solution of 11.2-4
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

    // subchapter 11.4
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

    // subchapter 11.4
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

    // solution of 11.4-2
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

    // solution of 11.4-2
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

    // problem 11-3
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
