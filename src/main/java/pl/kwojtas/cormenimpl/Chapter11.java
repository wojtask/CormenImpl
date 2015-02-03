package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.Stack;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

public final class Chapter11 {

    private Chapter11() { }

    static class ElementWithKey<T> {
        public int key;
        public T data;

        public ElementWithKey(int key, T data) {
            this.key = key;
            this.data = data;
        }

        public ElementWithKey(ElementWithKey<T> otherElementWithKey) {
            this.key = otherElementWithKey.key;
            this.data = otherElementWithKey.data;
        }
    }

    // subchapter 11.1
    public static <T> ElementWithKey<T> directAddressSearch(ZeroBasedIndexedArray<ElementWithKey<T>> T, int k) {
        return T.at(k);
    }

    // subchapter 11.1
    public static <T> void directAddressInsert(ZeroBasedIndexedArray<ElementWithKey<T>> T, ElementWithKey<T> x) {
        T.set(x.key, x);
    }

    // subchapter 11.1
    public static <T> void directAddressDelete(ZeroBasedIndexedArray<ElementWithKey<T>> T, ElementWithKey<T> x) {
        T.set(x.key, null);
    }

    // solution of 11.1-1
    public static <T> ElementWithKey<T> directAddressMaximum(ZeroBasedIndexedArray<ElementWithKey<T>> T) {
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
    public static <T> ElementWithKey<T> directAddressSearch_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, int k) {
        List<ElementWithKey<T>> list = T.at(k);
        if (list.head != null) {
            return list.head.key;
        }
        return null;
    }

    // solution of 11.1-3
    public static <T> void directAddressInsert_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x) {
        List<ElementWithKey<T>> list = T.at(x.key);
        Chapter10.listInsert(list, list.new Node(x));
    }

    // solution of 11.1-3
    public static <T> void directAddressDelete_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x) {
        List<ElementWithKey<T>> list = T.at(x.key);
        List<ElementWithKey<T>>.Node node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
    }

    static class HugeArray<T> {
        public ZeroBasedIndexedArray<Integer> T;
        public Stack<ElementWithKey<T>> S;

        public HugeArray(int size, int capacity) {
            T = ZeroBasedIndexedArray.withLength(size);
            for (int i = 0; i <= size - 1; i++) {
                T.set(i, 0); // initializing T as it contains nulls, initialized could be anything nonnull
            }
            S = Stack.withLength(capacity);
        }
    }

    // solution of 11.1-4
    public static <T> ElementWithKey<T> hugeArraySearch(HugeArray<T> H, int k) {
        if (1 <= H.T.at(k) && H.T.at(k) <= H.S.top && H.S.at(H.T.at(k)).key == k) {
            return new ElementWithKey<>(H.S.at(H.T.at(k)));
        }
        return null;
    }

    // solution of 11.1-4
    public static <T> void hugeArrayInsert(HugeArray<T> H, ElementWithKey<T> x) {
        Chapter10.push(H.S, x);
        H.T.set(x.key, H.S.top);
    }

    // solution of 11.1-4
    public static <T> void hugeArrayDelete(HugeArray<T> H, ElementWithKey<T> x) {
        int k = x.key;
        ElementWithKey<T> y = Chapter10.pop(H.S);
        H.S.set(H.T.at(k), y);
        H.T.set(y.key, H.T.at(k));
    }

    // subchapter 11.2
    public static <T> void chainedHashInsert(
            ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x, HashFunction h) {
        List<ElementWithKey<T>> list = T.at(h.compute(x.key));
        Chapter10.listInsert(list, list.new Node(x));
    }

    // subchapter 11.2
    public static <T> ElementWithKey<T> chainedHashSearch(
            ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, int k, HashFunction h) {
        List<ElementWithKey<T>> list = T.at(h.compute(k));
        List<ElementWithKey<T>>.Node x = list.head;
        while (x != null) {
            if (x.key.key == k) {
                return x.key;
            }
            x = x.next;
        }
        return null;
    }

    // subchapter 11.2
    public static <T> void chainedHashDelete(
            ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x, HashFunction h) {
        List<ElementWithKey<T>> list = T.at(h.compute(x.key));
        List<ElementWithKey<T>>.Node node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
    }

    // subchapter 11.4
    public static int hashInsert(ZeroBasedIndexedArray<Integer> T, int k, HashProbingFunction h) {
        int m = T.length;
        int i = 0;
        do {
            int j = h.compute(k, i);
            if (T.at(j) == null) {
                T.set(j, k);
                return j;
            } else {
                i++;
            }
        } while (i != m);
        throw new RuntimeException("overflow");
    }

    // subchapter 11.4
    public static Integer hashSearch(ZeroBasedIndexedArray<Integer> T, int k, HashProbingFunction h) {
        int m = T.length;
        int i = 0;
        int j;
        do {
            j = h.compute(k, i);
            if (T.at(j) != null && T.at(j) == k) {
                return j;
            }
            i++;
        } while (T.at(j) != null && i != m);
        return null;
    }

    public static final Integer DELETED = Integer.MAX_VALUE;

    // solution of 11.4-2
    public static void hashDelete(ZeroBasedIndexedArray<Integer> T, int k, HashProbingFunction h) {
        int m = T.length;
        int i = 0;
        int j;
        do {
            j = h.compute(k, i);
            if (T.at(j) == k) {
                T.set(j, DELETED);
                return;
            }
            i++;
        } while (T.at(j) != null && i != m);
    }

    // solution of 11.4-2
    public static int hashInsert_(ZeroBasedIndexedArray<Integer> T, int k, HashProbingFunction h) {
        int m = T.length;
        int i = 0;
        do {
            int j = h.compute(k, i);
            if (T.at(j) == null || T.at(j) == DELETED) {
                T.set(j, k);
                return j;
            } else {
                i++;
            }
        } while (i != m);
        throw new RuntimeException("overflow");
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
