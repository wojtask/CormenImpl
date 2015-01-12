package pl.kwojtas.cormenimpl;

import static pl.kwojtas.cormenimpl.Util.greater;
import static pl.kwojtas.cormenimpl.Util.leq;
import static pl.kwojtas.cormenimpl.Util.less;

public class Chapter2 {

    private Chapter2() { }

    // subchapter 2.1
    public static <T> void insertionSort(Array<T> A) {
        for (int j = 2; j <= A.length; j++) {
            T key = A.at(j);
            int i = j - 1;
            while (i > 0 && greater(A.at(i), key)) {
                A.set(i + 1, A.at(i));
                i--;
            }
            A.set(i + 1, key);
        }
    }

    // solution of 2.1-3
    public static <T> Integer linearSearch(Array<T> A, T v) {
        int i = 1;
        while (i <= A.length && !A.at(i).equals(v)) {
            i++;
        }
        if (i <= A.length) {
            return i;
        }
        return null;
    }

    // solution of 2.1-4
    public static Array<Integer> binaryAdd(Array<Integer> A, Array<Integer> B) {
        int n = A.length;
        Array<Integer> C = new Array<>();
        for (int i = 1; i <= n + 1; i++) {
            C.set(i, 0);
        }
        for (int i = 1; i <= n; i++) {
            Integer sum = A.at(i) + B.at(i) + C.at(i);
            C.set(i, sum % 2);
            C.set(i + 1, sum / 2);
        }
        return C;
    }

    // solution of 2.2-2
    public static <T> void selectionSort(Array<T> A) {
        int n = A.length;
        for (int j = 1; j <= n - 1; j++) {
            int min = j;
            for (int i = j + 1; i <= n; i++) {
                if (less(A.at(i), A.at(min))) {
                    min = i;
                }
            }
            A.exch(min, j);
        }
    }

    // subchapter 2.3
    private static void merge(Array<Integer> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<Integer> L = new Array<>();
        Array<Integer> R = new Array<>();
        for (int i = 1; i <= n1; i++) {
            L.set(i, A.at(p + i - 1));
        }
        for (int j = 1; j <= n2; j++) {
            R.set(j, A.at(q + j));
        }
        L.set(n1 + 1, Integer.MAX_VALUE);
        R.set(n2 + 1, Integer.MAX_VALUE);
        int i = 1;
        int j = 1;
        for (int k = p; k <= r; k++) {
            if (L.at(i) <= R.at(j)) {
                A.set(k, L.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                j++;
            }
        }
    }

    // subchapter 2.3
    public static void mergeSort(Array<Integer> A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    // solution of 2.3-2
    private static <T> void merge_(Array<T> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<T> L = new Array<>();
        Array<T> R = new Array<>();
        for (int i = 1; i <= n1; i++) {
            L.set(i, A.at(p + i - 1));
        }
        for (int j = 1; j <= n2; j++) {
            R.set(j, A.at(q + j));
        }
        int i = 1, j = 1;
        int k = p;
        while (i <= n1 && j <= n2) {
            if (leq(L.at(i), R.at(j))) {
                A.set(k, L.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                j++;
            }
            k++;
        }
        while (i <= n1) {
            A.set(k, L.at(i));
            i++;
            k++;
        }
        while (j <= n2) {
            A.set(k, R.at(j));
            j++;
            k++;
        }
    }

    // for testing merge_
    public static <T> void mergeSort_(Array<T> A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort_(A, p, q);
            mergeSort_(A, q + 1, r);
            merge_(A, p, q, r);
        }
    }

}
