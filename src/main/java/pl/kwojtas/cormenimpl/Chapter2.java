package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.less;

/**
 * Implements algorithms from Chapter 2.
 */
public final class Chapter2 {

    private Chapter2() { }

    /**
     * Sorts elements using insertion sort algorithm.
     * <p><span style="font-variant:small-caps;">Insertion-Sort</span> from subchapter 2.1.</p>
     *
     * @param A the {@link Array} of elements to sort
     * @param <T> the type of elements in {@code A}
     */
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

    /**
     * Sorts elements in non-increasing order using insertion sort algorithm.
     * <p>Solution to exercise 2.1-2.</p>
     *
     * @param A the {@link Array} of elements to sort
     * @param <T> the type of elements in {@code A}
     */
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

    /**
     * Searches for element in an array using linear search.
     * <p><span style="font-variant:small-caps;">Linear-Search</span> from solution to exercise 2.1-3.</p>
     *
     * @param A the {@link Array} of elements
     * @param v the element to find
     * @param <T> the type of elements in {@code A}
     * @return index {@code i} such that {@code A[i] = v}, or {@code null} if {@code v} does not appear in {@code A}
     */
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

    /**
     * Adds two {@code n}-bit non-negative integers.
     * <p><span style="font-variant:small-caps;">Binary-Add</span> from solution to exercise 2.1-4.</p>
     *
     * @param A the {@code n}-element {@link Array} containing bits of integer {@code a} from the least to the most significant
     * @param B the {@code n}-element {@link Array} containing bits of integer {@code b} from the least to the most significant
     * @return the {@code (n + 1)}-element {@link Array} containing bits of {@code a + b} from the least to the most significant
     */
    public static Array<Integer> binaryAdd(Array<Integer> A, Array<Integer> B) {
        int n = A.length;
        Array<Integer> C = Array.withLength(n + 1);
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

    /**
     * Sorts elements using selection sort algorithm.
     * <p><span style="font-variant:small-caps;">Selection-Sort</span> from solution to exercise 2.2-2.</p>
     *
     * @param A the {@link Array} of elements to sort
     * @param <T> the type of elements in {@code A}
     */
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

    /**
     * Merges two sorted subarrays {@code A[p..q]} and {@code A[q + 1..r]} into one sorted array {@code A[p..r]}.
     * <p><span style="font-variant:small-caps;">Merge</span> from subchapter 2.3.</p>
     *
     * @param A the {@link Array} of elements containing subarrays to merge
     * @param p the index of the beginning of the first subarray in {@code A} to merge
     * @param q the index of the end of the first subarray in {@code A} to merge
     * @param r the index of end of the second subarray in {@code A} to merge
     */
    private static void merge(Array<Integer> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<Integer> L = Array.withLength(n1 + 1);
        Array<Integer> R = Array.withLength(n2 + 1);
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

    /**
     * Sorts elements using merge sort algorithm.
     * <p><span style="font-variant:small-caps;">Merge-Sort</span> from subchapter 2.3.</p>
     *
     * @param A the {@link Array} of elements to sort
     * @param p the index of the beginning of subarray in {@code A} to sort
     * @param r the index of the end of subarray in {@code A} to sort
     */
    public static void mergeSort(Array<Integer> A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    /**
     * Merges two sorted subarrays {@code A[p..q]} and {@code A[q + 1..r]} into one sorted array {@code A[p..r]}
     * without using sentinels.
     * <p><span style="font-variant:small-caps;">Merge'</span> from solution to exercise 2.3-2.</p>
     *
     * @param A the {@link Array} of elements containing subarrays to merge
     * @param p the index of the beginning of the first subarray in {@code A} to merge
     * @param q the index of the end of the first subarray in {@code A} to merge
     * @param r the index of end of the second subarray in {@code A} to merge
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void merge_(Array<T> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<T> L = Array.withLength(n1);
        Array<T> R = Array.withLength(n2);
        for (int i = 1; i <= n1; i++) {
            L.set(i, A.at(p + i - 1));
        }
        for (int j = 1; j <= n2; j++) {
            R.set(j, A.at(q + j));
        }
        int i = 1;
        int j = 1;
        for (int k = p; k <= r; k++) {
            if (j > n2 || less(L.at(i), R.at(j))) {
                A.set(k, L.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                j++;
            }
        }
    }

    /**
     * Searches for element in an array using recursive binary search.
     * <p><span style="font-variant:small-caps;">Recursive-Binary-Search</span> from solution to exercise 2.3-5.</p>
     *
     * @param A the {@link Array} of elements
     * @param v the element to find
     * @param low the index of the beginning of the subarray in {@code A} to search in
     * @param high the index of the end of the subarray in {@code A} to search in
     * @param <T> the type of elements in {@code A}
     * @return index {@code i} such that {@code A[i] = v}, or {@code null} if {@code v} does not appear in {@code A}
     */
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

    /**
     * Searches for element in an array using iterative binary search.
     * <p><span style="font-variant:small-caps;">Iterative-Binary-Search</span> from solution to exercise 2.3-5.</p>
     *
     * @param A the {@link Array} of elements
     * @param v the element to find
     * @param <T> the type of elements in {@code A}
     * @return index {@code i} such that {@code A[i] = v}, or {@code null} if {@code v} does not appear in {@code A}
     */
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

    /**
     * Searches for a sum of two elements in a set.
     * <p><span style="font-variant:small-caps;">Sum-Search</span> from solution to exercise 2.3-7.</p>
     *
     * @param S the {@link Array} of elements containing elements from the set
     * @param x the sum to find
     * @return {@code true} if the set contains two elements that sum up to {@code x}
     */
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

    /**
     * Sorts elements using bubble sort algorithm.
     * <p><span style="font-variant:small-caps;">Bubble-Sort</span> from problem 2-2.</p>
     *
     * @param A the {@link Array} of elements to sort
     * @param <T> the type of elements in {@code A}
     */
    public static <T extends Comparable> void bubbleSort(Array<T> A) {
        for (int i = 1; i <= A.length; i++) {
            for (int j = A.length; j >= i + 1; j--) {
                if (less(A.at(j), A.at(j-1))) {
                    A.exch(j, j - 1);
                }
            }
        }
    }

    /**
     * Evaluates the value of a polynomial for a given argument using Horner's rule.
     * <p>Problem 2-3.</p>
     *
     * @param A the {@link ZeroBasedIndexedArray} containing coefficients of the polynomial from the least to the most significant
     * @param x the argument
     * @return the value of the polynomial at {@code x}
     */
    public static double horner(ZeroBasedIndexedArray<Double> A, double x) {
        double y = 0.0;
        int i = A.length - 1;
        while (i >= 0) {
            y = A.at(i) + x * y;
            i--;
        }
        return y;
    }

    /**
     * Evaluates the value of a polynomial for a given argument using naive method.
     * <p>Solution to problem 2-3(b).</p>
     *
     * @param A the {@link ZeroBasedIndexedArray} containing coefficients of the polynomial from the least to the most significant
     * @param x the argument
     * @return the value of the polynomial at {@code x}
     */
    public static double naivePolynomialEvaluation(ZeroBasedIndexedArray<Double> A, double x) {
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

    /**
     * Counts inversions in an array.
     * <p><span style="font-variant:small-caps;">Count-Inversions</span> from solution to problem 2-4(d).</p>
     *
     * @param A the {@link Array} of elements
     * @param p the index of the beginning of subarray in {@code A} to count inversions in
     * @param r the index of the end of subarray in {@code A} to count inversions in
     * @return the number of inversions in {@code A}
     */
    public static int countInversions(Array<Integer> A, int p, int r) {
        int inversions = 0;
        if (p < r) {
            int q = (p + r) / 2;
            inversions += countInversions(A, p, q);
            inversions += countInversions(A, q + 1, r);
            inversions += mergeInversions(A, p, q, r);
        }
        return inversions;
    }

    /**
     * Counts inversions in {@code A[p..r]} while merging two sorted subarrays {@code A[p..q]} and {@code A[q + 1..r]}
     * into one sorted array {@code A[p..r]}.
     * <p><span style="font-variant:small-caps;">MergeInversions</span> from solution to problem 2-4(d).</p>
     *
     * @param A the {@link Array} of elements containing subarrays to merge
     * @param p the index of the beginning of the first subarray in {@code A} to merge
     * @param q the index of the end of the first subarray in {@code A} to merge
     * @param r the index of end of the second subarray in {@code A} to merge
     */
    private static int mergeInversions(Array<Integer> A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<Integer> L = Array.withLength(n1 + 1);
        Array<Integer> R = Array.withLength(n2 + 1);
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
        int inversions = 0;
        for (int k = p; k <= r; k++) {
            if (L.at(i) <= R.at(j)) {
                A.set(k, L.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                j++;
                inversions += n1 - i + 1;
            }
        }
        return inversions;
    }

}
