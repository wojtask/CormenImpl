package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;

import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;

public class Chapter2 {

    private Chapter2() { }

    // subchapter 2.1
    public static <T extends Comparable> void insertionSort(Array<T> A) {
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

    // solution of 2.1-2
    public static <T extends Comparable> void nonincreasingInsertionSort(Array<T> A) {
        for (int j = 2; j <= A.length; j++) {
            T key = A.at(j);
            int i = j - 1;
            while (i > 0 && less(A.at(i), key)) {
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
    public static <T extends Comparable> void selectionSort(Array<T> A) {
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
    public static <T extends Comparable> void merge_(Array<T> A, int p, int q, int r) {
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

    // solution of 2.3-5
    public static <T extends Comparable> Integer recursiveBinarySearch(Array<T> A, T v, int low, int high) {
        if (low > high) {
            return null;
        }
        int mid = (low + high) / 2;
        if (v.equals(A.at(mid))) {
            return mid;
        }
        if (less(v, A.at(mid))) {
            return recursiveBinarySearch(A, v, low, mid - 1);
        }
        return recursiveBinarySearch(A, v, mid + 1, high);
    }

    // solution of 2.3-5
    public static <T extends Comparable> Integer iterativeBinarySearch(Array<T> A, T v) {
        int low = 1;
        int high = A.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (v.equals(A.at(mid))) {
                return mid;
            }
            if (less(v, A.at(mid))) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }

    // solution of 2.3-7
    public static boolean sumSearch(Array<Integer> S, Integer x) {
        int n = S.length;
        mergeSort(S, 1, n);
        for (int i = 1; i <= n - 1; i++) {
            if (recursiveBinarySearch(S, x - S.at(i), i + 1, n) != null) {
                return true;
            }
        }
        return false;
    }

    // problem 2-2
    public static <T extends Comparable> void bubbleSort(Array<T> A) {
        for (int i = 1; i <= A.length; i++) {
            for (int j = A.length; j >= i + 1; j--) {
                if (less(A.at(j), A.at(j-1))) {
                    A.exch(j, j - 1);
                }
            }
        }
    }

    // problem 2-3
    public static double horner(Array<Double> a, double x) {
        double y = 0.0;
        int n = a.length - 1;
        int i = n;
        while (i >= 0) {
            y = a.at(i) + x * y;
            i--;
        }
        return y;
    }

    // solution of 2-3(b)
    public static double naivePolynomialEvaluation(Array<Double> A, double x) {
        double y = 0.0;
        int n = A.length - 1;
        for (int i = 0; i <= n; i++) {
            double s = A.at(i);
            for (int j = 1; j <= i; j++) {
                s *= x;
            }
            y += s;
        }
        return y;
    }

    // solution of 2-4(d)
    public static <T extends Comparable> int countInversions(Array<T> A, int p, int r) {
        int inversions = 0;
        if (p < r) {
            int q = (p + r) / 2;
            inversions += countInversions(A, p, q);
            inversions += countInversions(A, q + 1, r);
            inversions += mergeInversions(A, p, q, r);
        }
        return inversions;
    }

    // solution of 2-4(d)
    private static <T extends Comparable> int mergeInversions(Array<T> A, int p, int q, int r) {
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
        int inversions = 0;
        while (i <= n1 && j <= n2) {
            if (leq(L.at(i), R.at(j))) {
                A.set(k, L.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                j++;
                inversions += n1 - i + 1;
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
        return inversions;
    }

}
