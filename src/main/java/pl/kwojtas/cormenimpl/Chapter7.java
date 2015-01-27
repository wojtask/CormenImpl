package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Interval;
import pl.kwojtas.cormenimpl.util.Pair;

import static pl.kwojtas.cormenimpl.Chapter5.random;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;

public final class Chapter7 {

    private Chapter7() { }

    // subchapter 7.1
    public static <T extends Comparable> void quicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
    }

    // subchapter 7.1
    public static <T extends Comparable> int partition(Array<T> A, int p, int r) {
        T x = A.at(r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (leq(A.at(j), x)) {
                i++;
                A.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        return i + 1;
    }

    // subchapter 7.3
    public static <T extends Comparable> int randomizedPartition(Array<T> A, int p, int r) {
        int i = random(p, r);
        A.exch(r, i);
        return partition(A, p, r);
    }

    // subchapter 7.3
    public static <T extends Comparable> void randomizedQuicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = randomizedPartition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
    }

    // exercise 7.4-5
    public static <T extends Comparable> void sortNearlySorted(Array<T> A, int p, int r, int k) {
        quicksortNearlySorted(A, p, r, k);
        Array<T> nearlySorted = Array.withLength(r - p + 1);
        for (int i = p; i <= r; i++) {
            nearlySorted.set(i - p + 1, A.at(i));
        }
        Chapter2.insertionSort(nearlySorted);
        for (int i = p; i <= r; i++) {
            A.set(i, nearlySorted.at(i - p + 1));
        }
    }

    // exercise 7.4-5
    private static <T extends Comparable> void quicksortNearlySorted(Array<T> A, int p, int r, int k) {
        if (r - p + 1 >= k) {
            int q = partition(A, p, r);
            quicksortNearlySorted(A, p, q - 1, k);
            quicksortNearlySorted(A, q + 1, r, k);
        }
    }

    // problem 7-1
    private static <T extends Comparable> int hoarePartition(Array<T> A, int p, int r) {
        T x = A.at(p);
        int i = p - 1;
        int j = r + 1;
        while (true) {
            do {
                j--;
            } while (greater(A.at(j), x));
            do {
                i++;
            } while (less(A.at(i), x));
            if (i < j) {
                A.exch(i, j);
            } else {
                return j;
            }
        }
    }

    // solution of 7-1(e)
    public static <T extends Comparable> void hoareQuicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = hoarePartition(A, p, r);
            quicksort(A, p, q);
            quicksort(A, q + 1, r);
        }
    }

    // problem 7-3
    public static <T extends Comparable> void stoogeSort(Array<T> A, int i, int j) {
        if (greater(A.at(i), A.at(j))) {
            A.exch(i, j);
        }
        if (i + 1 >= j) {
            return;
        }
        int k = (j - i + 1) / 3;
        stoogeSort(A, i, j - k);
        stoogeSort(A, i + k, j);
        stoogeSort(A, i, j - k);
    }

    // problem 7-4
    public static <T extends Comparable> void quicksort_(Array<T> A, int p, int r) {
        while (p < r) {
            int q = partition(A, p, r);
            quicksort_(A, p, q - 1);
            p = q + 1;
        }
    }

    // solution of 7-4(c)
    public static <T extends Comparable> void quicksort__(Array<T> A, int p, int r) {
        while (p < r) {
            int q = partition(A, p, r);
            if (q - p < r - q) {
                quicksort__(A, p, q - 1);
                p = q + 1;
            } else {
                quicksort__(A, q + 1, r);
                r = q - 1;
            }
        }
    }

    // problem 7-5
    public static <T extends Comparable> int medianOf3Partition(Array<T> A, int p, int r) {
        int i1 = random(p, r);
        int i2 = random(p, r);
        int i3 = random(p, r);
        Array<T> pivots = new Array<>(A.at(i1), A.at(i2), A.at(i3));
        Chapter2.insertionSort(pivots);
        T median = pivots.at(2);
        if (median.equals(A.at(i1))) {
            A.exch(r, i1);
        } else if (median.equals(A.at(i2))) {
            A.exch(r, i2);
        } else {
            A.exch(r, i3);
        }
        return partition(A, p, r);
    }

    // solution of 7-6(a)
    public static void fuzzySort(Array<Interval> A, int p, int r) {
        if (p < r) {
            Pair<Integer, Integer> q = fuzzyPartition(A, p, r);
            fuzzySort(A, p, q.first - 1);
            fuzzySort(A, q.second + 1, r);
        }
    }

    // solution of 7-6(a)
    public static Pair<Integer, Integer> fuzzyPartition(Array<Interval> A, int p, int r) {
        A.exch(r, random(p, r));
        double x = A.at(r).a;
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (A.at(j).a <= x) {
                i++;
                A.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        int q = i + 1;
        for (int k = i; k >= p; k--) {
            if (A.at(k).b >= x) {
                q--;
                A.exch(q, k);
            }
        }
        return new Pair<>(q, i + 1);
    }
}
