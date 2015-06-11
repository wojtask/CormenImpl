package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Interval;
import pl.kwojtas.cormenimpl.util.Pair;

import static pl.kwojtas.cormenimpl.Chapter2.insertionSort;
import static pl.kwojtas.cormenimpl.Chapter5.random;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.leq;
import static pl.kwojtas.cormenimpl.util.Util.less;

/**
 * Implements algorithms from Chapter 7.
 */
public final class Chapter7 {

    private Chapter7() { }

    /**
     * Sorts elements using quicksort.
     * <p><span style="font-variant:small-caps;">Quicksort</span> from subchapter 7.1.</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void quicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
    }

    /**
     * Partitions an array into two subarrays around a pivot element. The first subarray will contain elements
     * less than or equal to the pivot element, and the second subarray will contain elements greater than the pivot element.
     * <p><span style="font-variant:small-caps;">Partition</span> from subchapter 7.1</p>
     *
     * @param A the array of elements to partition
     * @param p the index of the beginning of subarray in {@code A} being partitioned
     * @param r the index of the end of subarray in {@code A} being partitioned and the index of the pivot element
     * @param <T> the type of elements in {@code A}
     * @return the index of the pivot element after partitioning
     */
    static <T extends Comparable> int partition(Array<T> A, int p, int r) {
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

    /**
     * Partitions an array into two subarrays around randomly chosen pivot element. The first subarray will contain elements
     * less than or equal to the pivot element, and the second subarray will contain elements greater than the pivot element.
     * <p><span style="font-variant:small-caps;">Randomized-Partition</span> from subchapter 7.3.</p>
     *
     * @param A the array of elements to partition
     * @param p the index of the beginning of subarray in {@code A} being partitioned
     * @param r the index of the end of subarray in {@code A} being partitioned
     * @param <T> the type of elements in {@code A}
     * @return the index of the pivot element after partitioning
     */
    static <T extends Comparable> int randomizedPartition(Array<T> A, int p, int r) {
        int i = random(p, r);
        A.exch(r, i);
        return partition(A, p, r);
    }

    /**
     * Sorts elements using quicksort with randomized partition.
     * <p><span style="font-variant:small-caps;">Randomized-Quicksort</span> from subchapter 7.3.</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void randomizedQuicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = randomizedPartition(A, p, r);
            quicksort(A, p, q - 1);
            quicksort(A, q + 1, r);
        }
    }

    /**
     * Sorts elements in a nearly sorted array. Uses quicksort to sort long subarrays and insertion sort to sort short subarrays.
     * <p>Exercise 7.4-5.</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param k the minimum size of a subarray that should be sorted using quicksort
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void sortNearlySorted(Array<T> A, int p, int r, int k) {
        quicksortNearlySorted(A, p, r, k);
        Array<T> nearlySorted = Array.withLength(r - p + 1);
        for (int i = p; i <= r; i++) {
            nearlySorted.set(i - p + 1, A.at(i));
        }
        insertionSort(nearlySorted);
        for (int i = p; i <= r; i++) {
            A.set(i, nearlySorted.at(i - p + 1));
        }
    }

    private static <T extends Comparable> void quicksortNearlySorted(Array<T> A, int p, int r, int k) {
        if (r - p + 1 >= k) {
            int q = partition(A, p, r);
            quicksortNearlySorted(A, p, q - 1, k);
            quicksortNearlySorted(A, q + 1, r, k);
        }
    }

    /**
     * The original version of partition procedure.
     * <p><span style="font-variant:small-caps;">Hoare-Partition</span> from problem 7-1.</p>
     *
     * @param A the array of elements to partition
     * @param p the index of the beginning of subarray in {@code A} being partitioned
     * @param r the index of the end of subarray in {@code A} being partitioned
     * @param <T> the type of elements in {@code A}
     * @return the index of the pivot element after partitioning
     */
    static <T extends Comparable> int hoarePartition(Array<T> A, int p, int r) {
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

    /**
     * The original version of quicksort.
     * <p><span style="font-variant:small-caps;">Hoare-Quicksort</span> from solution to problem 7-1(e).</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void hoareQuicksort(Array<T> A, int p, int r) {
        if (p < r) {
            int q = hoarePartition(A, p, r);
            quicksort(A, p, q);
            quicksort(A, q + 1, r);
        }
    }

    /**
     * Sorts elements inefficiently.
     * <p><span style="font-variant:small-caps;">Stooge-Sort</span> from problem 7-3.</p>
     *
     * @param A the array of elements to sort
     * @param i the index of the beginning of subarray in {@code A} being sorted
     * @param j the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
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

    /**
     * Sorts elements using a version of quicksort that simulates tail recursion.
     * <p><span style="font-variant:small-caps;">Quicksort'</span> from problem 7-4.</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void quicksort_(Array<T> A, int p, int r) {
        while (p < r) {
            int q = partition(A, p, r);
            quicksort_(A, p, q - 1);
            p = q + 1;
        }
    }

    /**
     * Sorts elements using a version of quicksort that uses recursion stack of at most logarithmic depth.
     * <p><span style="font-variant:small-caps;">Quicksort''</span> from solution to problem 7-4(c).</p>
     *
     * @param A the array of elements to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     * @param <T> the type of elements in {@code A}
     */
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

    /**
     * Partitions an array into two subarrays around pivot element being the median of 3 randomly chosen elements.
     * The first subarray will contain elements less than or equal to the pivot element,
     * and the second subarray will contain elements greater than the pivot element.
     * <p>Problem 7-5.</p>
     *
     * @param A the array of elements to partition
     * @param p the index of the beginning of subarray in {@code A} being partitioned
     * @param r the index of the end of subarray in {@code A} being partitioned
     * @param <T> the type of elements in {@code A}
     * @return the index of the pivot element after partitioning
     */
    public static <T extends Comparable> int medianOf3Partition(Array<T> A, int p, int r) {
        int i1 = random(p, r);
        int i2 = random(p, r);
        int i3 = random(p, r);
        Array<T> pivots = new Array<>(A.at(i1), A.at(i2), A.at(i3));
        insertionSort(pivots);
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

    /**
     * Fuzzy-sorts an array of intervals. Produces a permutation <tt>i<sub>1</sub></tt>, <tt>i<sub>2</sub></tt>, ...,
     * <tt>i<sub>n</sub></tt> of the intervals such that there exist <tt>c<sub>j</sub></tt> from
     * <tt>[a<sub>i<sub>j</sub></sub>, b<sub>i<sub>j</sub></sub>]</tt> satisfying
     * <tt>c<sub>1</sub> &lt;= c<sub>2</sub> &lt;= ... &lt;= c<sub>n</sub></tt>.
     * <p><span style="font-variant:small-caps;">Fuzzy-Sort</span> from solution to problem 7-6(a).</p>
     *
     * @param A the array of intervals to sort
     * @param p the index of the beginning of subarray in {@code A} being sorted
     * @param r the index of the end of subarray in {@code A} being sorted
     */
    public static void fuzzySort(Array<Interval> A, int p, int r) {
        if (p < r) {
            Pair<Integer, Integer> q = fuzzyPartition(A, p, r);
            fuzzySort(A, p, q.first - 1);
            fuzzySort(A, q.second + 1, r);
        }
    }

    /**
     * Partitions an array of intervals into three subarrays around randomly chosen pivot interval.
     * The first subarray will contain intervals laying entirely to the left from the pivot interval,
     * the second subarray will contain intervals overlapping with the pivot interval,
     * and the third subarray will contain intervals laying entirely to the right from the pivot interval.
     * <p><span style="font-variant:small-caps;">Fuzzy-Partition</span> from solution to problem 7-6(a).</p>
     *
     * @param A the array of intervals to partition
     * @param p the index of the beginning of subarray in {@code A} being partitioned
     * @param r the index of the end of subarray in {@code A} being partitioned
     * @return the pair of indexes bounding the part of {@code A} containing intervals overlapping with the pivot interval
     */
    static Pair<Integer, Integer> fuzzyPartition(Array<Interval> A, int p, int r) {
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
