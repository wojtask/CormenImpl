package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.List;
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
    }

    // subchapter 11.1
    public static <T> ElementWithKey<T> directAccessSearch(ZeroBasedIndexedArray<ElementWithKey<T>> T, int k) {
        return T.at(k);
    }

    // subchapter 11.1
    public static <T> void directAccessInsert(ZeroBasedIndexedArray<ElementWithKey<T>> T, ElementWithKey<T> x) {
        T.set(x.key, x);
    }

    // subchapter 11.1
    public static <T> void directAccessDelete(ZeroBasedIndexedArray<ElementWithKey<T>> T, ElementWithKey<T> x) {
        T.set(x.key, null);
    }

    // solution of 11.1-1
    public static <T> ElementWithKey<T> directAccessMaximum(ZeroBasedIndexedArray<ElementWithKey<T>> T) {
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
    public static <T> ElementWithKey<T> directAccessSearch_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, int k) {
        List<ElementWithKey<T>> list = T.at(k);
        if (list.head != null) {
            return list.head.key;
        }
        return null;
    }

    // solution of 11.1-3
    public static <T> void directAccessInsert_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x) {
        List<ElementWithKey<T>> list = T.at(x.key);
        Chapter10.listInsert(list, list.new Node(x));
    }

    // solution of 11.1-3
    public static <T> void directAccessDelete_(ZeroBasedIndexedArray<List<ElementWithKey<T>>> T, ElementWithKey<T> x) {
        List<ElementWithKey<T>> list = T.at(x.key);
        List<ElementWithKey<T>>.Node node = Chapter10.listSearch(list, x);
        Chapter10.listDelete(list, node);
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

    public static final int DELETED = Integer.MAX_VALUE;

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

}
