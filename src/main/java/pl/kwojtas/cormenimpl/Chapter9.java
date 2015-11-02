package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Point2D;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;
import static pl.kwojtas.cormenimpl.Chapter2.insertionSort;
import static pl.kwojtas.cormenimpl.Chapter7.randomizedPartition;
import static pl.kwojtas.cormenimpl.util.Util.ceil;
import static pl.kwojtas.cormenimpl.util.Util.greater;
import static pl.kwojtas.cormenimpl.util.Util.less;
import static pl.kwojtas.cormenimpl.util.Util.max;
import static pl.kwojtas.cormenimpl.util.Util.min;

/**
 * Implements algorithms from Chapter 9.
 */
public final class Chapter9 {

    private Chapter9() {
    }

    /**
     * Finds the smallest element in an array.
     * <p><span style="font-variant:small-caps;">Minimum</span> from subchapter 9.1.</p>
     *
     * @param A   the array to scan
     * @param <E> the type of elements in {@code A}
     * @return the smallest element in {@code A}
     */
    public static <E extends Comparable<? super E>> E minimum(Array<E> A) {
        E min = A.at(1);
        for (int i = 2; i <= A.length; i++) {
            if (greater(min, A.at(i))) {
                min = A.at(i);
            }
        }
        return min;
    }

    /**
     * Simultaneously finds the smallest element and the largest element in an array.
     * <p>Subchapter 9.1.</p>
     *
     * @param A   the array to scan
     * @param <E> the type of elements in {@code A}
     * @return the pair <tt>(a<sub>1</sub>, a<sub>2</sub>)</tt> such that <tt>a<sub>1</sub></tt>
     * is the smallest element in {@code A} and <tt>a<sub>2</sub></tt> is the largest element in {@code A}
     */
    public static <E extends Comparable<? super E>> Pair<E, E> minimumMaximum(Array<E> A) {
        int n = A.length;
        Pair<E, E> p;
        int i;
        if (n % 2 == 1) {
            p = new Pair<>(A.at(1), A.at(1));
            i = 2;
        } else {
            if (less(A.at(1), A.at(2))) {
                p = new Pair<>(A.at(1), A.at(2));
            } else {
                p = new Pair<>(A.at(2), A.at(1));
            }
            i = 3;
        }
        while (i + 1 <= A.length) {
            if (less(A.at(i), A.at(i + 1))) {
                if (less(A.at(i), p.first)) {
                    p.first = A.at(i);
                }
                if (greater(A.at(i + 1), p.second)) {
                    p.second = A.at(i + 1);
                }
            } else {
                if (less(A.at(i + 1), p.first)) {
                    p.first = A.at(i + 1);
                }
                if (greater(A.at(i), p.second)) {
                    p.second = A.at(i);
                }
            }
            i += 2;
        }
        return p;
    }

    /**
     * Finds the {@code i}-th order statistic (the {@code i}-th smallest element) in an array.
     * <p><span style="font-variant:small-caps;">Randomized-Select</span> from subchapter 9.2.</p>
     *
     * @param A   the array to scan
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param i   the number of order statistic to find (1 - the smallest element)
     * @param <E> the type of elements in {@code A}
     * @return the {@code i}-th order statistic ({@code i}-th smallest element) in {@code A}
     */
    public static <E extends Comparable<? super E>> E randomizedSelect(Array<E> A, int p, int r, int i) {
        if (p == r) {
            return A.at(p);
        }
        int q = randomizedPartition(A, p, r);
        int k = q - p + 1;
        if (i == k) {
            return A.at(q);
        } else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        } else {
            return randomizedSelect(A, q + 1, r, i - k);
        }
    }

    /**
     * Finds the {@code i}-th order statistic (the {@code i}-th smallest element) in an array - an iterative version.
     * <p><span style="font-variant:small-caps;">Iterative-Randomized-Select</span> from solution to exercise 9.2-3.</p>
     *
     * @param A   the array to scan
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param i   the number of order statistic to find (1 - the smallest element)
     * @param <E> the type of elements in {@code A}
     * @return the {@code i}-th order statistic (the {@code i}-th smallest element) in {@code A}
     */
    public static <E extends Comparable<? super E>> E iterativeRandomizedSelect(Array<E> A, int p, int r, int i) {
        while (p < r) {
            int q = randomizedPartition(A, p, r);
            int k = q - p + 1;
            if (i == k) {
                return A.at(q);
            } else if (i < k) {
                r = q - 1;
            } else {
                p = q + 1;
                i -= k;
            }
        }
        return A.at(p);
    }

    /**
     * Finds the {@code i}-th order statistic (the {@code i}-th smallest element) in an array - an efficient version.
     * <p>Subchapter 9.3.</p>
     *
     * @param A   the array to scan
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param i   the number of order statistic to find (1 - the smallest element)
     * @param <E> the type of elements in {@code A}
     * @return the {@code i}-th order statistic (the {@code i}-th smallest element) in {@code A}
     */
    public static <E extends Comparable<? super E>> E select(Array<E> A, int p, int r, int i) {
        int n = r - p + 1;
        if (n == 1) {
            return A.at(p);
        }
        ZeroBasedIndexedArray<Array<E>> groups = ZeroBasedIndexedArray.withLength(ceil(n, 5));
        for (int j = 0; j < groups.length - 1; j++) {
            groups.set(j, Array.withLength(5));
        }
        groups.set(groups.length - 1, Array.withLength(n % 5 > 0 ? n % 5 : 5));
        for (int j = p; j <= r; j++) {
            groups.at((j - p) / 5).set((j - p) % 5 + 1, A.at(j));
        }
        Array<E> medians = Array.withLength(groups.length);
        for (int j = 0; j < groups.length; j++) {
            insertionSort(groups.at(j));
            medians.set(j + 1, groups.at(j).at((groups.at(j).length + 1) / 2));
        }
        E x = select(medians, 1, medians.length, (medians.length + 1) / 2);
        int k = partitionAround(A, p, r, x) - p + 1;
        if (i == k) {
            return x;
        }
        if (i < k) {
            return select(A, p, p + k - 2, i);
        }
        return select(A, p + k, r, i - k);
    }

    private static <E extends Comparable<? super E>> int partitionAround(Array<E> A, int p, int r, E x) {
        int q = p;
        while (!A.at(q).equals(x)) {
            q++;
        }
        A.exch(q, r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(A.at(j), x)) {
                i++;
                A.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        return i + 1;
    }

    /**
     * Sorts elements using the optimal version of quicksort.
     * <p>Solution to exercise 9.3-3.</p>
     *
     * @param A   the array of elements to sort
     * @param p   the index of the beginning of subarray in {@code A} being sorted
     * @param r   the index of the end of subarray in {@code A} being sorted
     * @param <E> the type of elements in {@code A}
     */
    public static <E extends Comparable<? super E>> void bestCaseQuicksort(Array<E> A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            select(A, p, r, q);
            bestCaseQuicksort(A, p, q - 1);
            bestCaseQuicksort(A, q + 1, r);
        }
    }

    /**
     * Finds the {@code i}-th order statistic (the {@code i}-th smallest element) in an array
     * using a subroutine for finding the median of the array.
     * <p>Solution to exercise 9.3-5.</p>
     *
     * @param A   the array to scan
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param i   the number of order statistic to find (1 - the smallest element)
     * @param <E> the type of elements in {@code A}
     * @return the {@code i}-th order statistic (the {@code i}-th smallest element) in {@code A}
     */
    public static <E extends Comparable<? super E>> E selectUsingMedianSubroutine(Array<E> A, int p, int r, int i) {
        if (p == r) {
            return A.at(p);
        }
        int q = (p + r) / 2;
        E x = select(A, p, r, q); // black-box median subroutine
        partitionAround(A, p, r, x); // we need to explicitly partition around the median because the used subroutine might not do it
        int k = q - p + 1;
        if (i == k) {
            return A.at(q);
        } else if (i < k) {
            return randomizedSelect(A, p, q - 1, i);
        } else {
            return randomizedSelect(A, q + 1, r, i - k);
        }
    }

    /**
     * Finds quantiles of a set.
     * <p><span style="font-variant:small-caps;">Quantiles</span> from solution to exercise 9.3-6.</p>
     *
     * @param A   the array containing elements of the set
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param k   the order of quantiles to find
     * @param <E> the type of elements in {@code A}
     * @return the set of {@code k}-th quantiles of the set represented by {@code A}
     */
    public static <E extends Comparable<? super E>> Set<E> quantiles(Array<E> A, int p, int r, int k) {
        int n = r - p + 1;
        if (k == 1) {
            return new HashSet<>();
        }
        int q1 = (int) (p + (k / 2) * (1.0 * n / k));
        int q2 = (int) (p + ceil(k, 2) * (1.0 * n / k));
        select(A, p, r, q1 - p + 1);
        if (q1 != q2) {
            select(A, q1 + 1, r, q2 - q1);
        }
        Set<E> L = quantiles(A, p, q1 - 1, k / 2);
        Set<E> R = quantiles(A, q2 + 1, r, k / 2);
        L.add(A.at(q1));
        L.add(A.at(q2));
        L.addAll(R);
        return L;
    }

    /**
     * Finds elements closest to the median of a set.
     * <p><span style="font-variant:small-caps;">Median-Proximity</span> from solution to exercise 9.3-7.</p>
     *
     * @param A the array containing elements of the set
     * @param k the proximity of the median
     * @return the set of {@code k} elements closest to the median of {@code A}
     */
    public static Set<Integer> medianProximity(Array<Integer> A, int k) {
        int n = A.length;
        int x = select(A, 1, n, (n + 1) / 2);
        Array<Integer> dist = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            dist.set(i, abs(A.at(i) - x));
        }
        int y = select(dist, 1, n, k);
        Set<Integer> M = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            if (abs(A.at(i) - x) <= y) {
                M.add(A.at(i));
            }
        }
        if (M.size() == k + 1) {
            M.remove(x + y);
        }
        return M;
    }

    /**
     * Finds the median of elements in two arrays of equal lengths.
     * <p><span style="font-variant:small-caps;">Two-Arrays-Median</span> from solution to exercise 9.3-8.</p>
     *
     * @param X   the first array to scan
     * @param pX  the index of the beginning of subarray in {@code X} being scanned
     * @param rX  the index of the end of subarray in {@code X} being scanned
     * @param Y   the second array to scan having the equal length as {@code X}
     * @param pY  the index of the beginning of subarray in {@code Y} being scanned
     * @param rY  the index of the end of subarray in {@code Y} being scanned
     * @param <E> the type of elements in {@code X} and {@code Y}
     * @return the median of elements in {@code X} and {@code Y}
     */
    public static <E extends Comparable<? super E>> E twoArraysMedian(Array<E> X, int pX, int rX, Array<E> Y, int pY, int rY) {
        if (rX - pX <= 1) {
            return min(max(X.at(pX), Y.at(pY)), min(X.at(rX), Y.at(rY)));
        }
        int qX = (pX + rX) / 2;
        int qX_ = ceil(pX + rX, 2);
        int qY = (pY + rY) / 2;
        int qY_ = ceil(pY + rY, 2);
        if (X.at(qX).equals(Y.at(qY))) {
            return X.at(qX);
        }
        if (less(X.at(qX), Y.at(qY))) {
            return twoArraysMedian(X, qX, rX, Y, pY, qY_);
        } else {
            return twoArraysMedian(X, pX, qX_, Y, qY, rY);
        }
    }

    /**
     * Finds the weighted median of an array.
     * <p>Solution to problem 9-2(b).</p>
     *
     * @param A   the array of elements to scan
     * @param w   the array of weights of elements from {@code A}, such that {@code w[i]} is the weight of {@code A[i]}
     * @param <E> the type of elements in {@code A}
     * @return the weighted median of {@code A} based on weights from {@code w}
     */
    public static <E extends Comparable<? super E>> E weightedMedianUsingSorting(Array<E> A, Array<Double> w) {
        sortWithWeights(A, w, 1, A.length);
        double weightSum = 0.0;
        int i = 1;
        while (i <= A.length && weightSum < 0.5) {
            weightSum += w.at(i);
            i++;
        }
        return A.at(i - 1);
    }

    private static <E extends Comparable<? super E>> void sortWithWeights(Array<E> A, Array<Double> w, int p, int r) {
        if (p < r) {
            int q = partitionWithWeights(A, w, p, r);
            sortWithWeights(A, w, p, q - 1);
            sortWithWeights(A, w, q + 1, r);
        }
    }

    private static <E extends Comparable<? super E>> int partitionWithWeights(Array<E> A, Array<Double> w, int p, int r) {
        E x = A.at(r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(A.at(j), x)) {
                i++;
                A.exch(i, j);
                w.exch(i, j);
            }
        }
        A.exch(i + 1, r);
        w.exch(i + 1, r);
        return i + 1;
    }

    /**
     * Finds the weighted median of an array - an efficient version.
     * <p><span style="font-variant:small-caps;">Weighted-Median</span> from solution to problem 9-2(c).</p>
     *
     * @param A   the array of elements to scan
     * @param w   the array of weights of elements from {@code A}, such that {@code w[i]} is the weight of {@code A[i]}
     * @param p   the index of the beginning of subarray in {@code A} being scanned
     * @param r   the index of the end of subarray in {@code A} being scanned
     * @param <E> the type of elements in {@code A}
     * @return the weighted median of {@code A} based on weights from {@code w}
     */
    public static <E extends Comparable<? super E>> E weightedMedian(Array<E> A, Array<Double> w, int p, int r) {
        if (r - p + 1 <= 2) {
            return w.at(p) >= w.at(r) ? A.at(p) : A.at(r);
        }
        partitionAroundMedianWithWeights(A, w, p, r);
        int q = (p + r) / 2;
        double WL = 0.0;
        for (int i = p; i <= q - 1; i++) {
            WL += w.at(i);
        }
        double WR = 1.0 - WL - w.at(q);
        if (WL < 0.5 && WR < 0.5) {
            return A.at(q);
        }
        if (WL >= 0.5) {
            w.set(q, w.at(q) + WR);
            return weightedMedian(A, w, p, q);
        } else {
            w.set(q, w.at(q) + WL);
            return weightedMedian(A, w, q, r);
        }
    }

    private static <E extends Comparable<? super E>> void partitionAroundMedianWithWeights(Array<E> A, Array<Double> w, int p, int r) {
        E x = select(new Array<>(A), p, r, (p + r) / 2);
        int q = p;
        while (!A.at(q).equals(x)) {
            q++;
        }
        A.exch(q, r);
        w.exch(q, r);
        partitionWithWeights(A, w, p, r);
    }

    /**
     * Finds the solution to the 2-dimensional post-office location problem with the Manhattan distance.
     * <p>Solution to problem 9-2(e).</p>
     *
     * @param A the array of points in a 2-dimensional space
     * @param w the array of weights of points from {@code A}, such that {@code w[i]} is the weight of {@code A[i]}
     * @return the point in the 2-dimensional space that minimizes weighted Manhattan distance from points from {@code A}
     * using weights from {@code w}
     */
    public static Point2D postOfficeLocation2D(Array<Point2D> A, Array<Double> w) {
        int n = A.length;
        Array<Double> X = Array.withLength(n);
        Array<Double> Y = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            X.set(i, A.at(i).x);
            Y.set(i, A.at(i).y);
        }
        double xp = weightedMedian(X, new Array<>(w), 1, n);
        double yp = weightedMedian(Y, new Array<>(w), 1, n);
        return new Point2D(xp, yp);
    }

    /**
     * Finds the {@code i}-th order statistic (the {@code i}-th smallest element) in an array
     * - a version optimized for small {@code i}'s.
     * <p>Solution to problem 9-3(a).</p>
     *
     * @param A   the array to scan
     * @param i   the number of order statistic to find (1 - the smallest element)
     * @param <E> the type of elements in {@code A}
     * @return the {@code i}-th order statistic (the {@code i}-th smallest element) in {@code A}
     */
    public static <E extends Comparable<? super E>> E smallOrderSelect(Array<E> A, int i) {
        smallOrderSelect(A, 1, A.length, i);
        return A.at(i);
    }

    private static <E extends Comparable<? super E>> ZeroBasedIndexedArray<Integer> smallOrderSelect(Array<E> A, int p, int r, int i) {
        int n = r - p + 1;
        int m = n / 2;
        if (i >= m) {
            return permutationProducingSelect(A, p, r, i);
        }
        ZeroBasedIndexedArray<Integer> permutation = ZeroBasedIndexedArray.withLength(n);
        for (int j = 0; j <= n - 1; j++) {
            permutation.set(j, j);
        }
        for (int j = 0; j <= m - 1; j++) {
            if (less(A.at(p + j), A.at(p + m + j))) {
                A.exch(p + j, p + m + j);
                permutation.exch(j, m + j);
            }
        }
        ZeroBasedIndexedArray<Integer> permutationChanges = smallOrderSelect(A, p + m, r, i);
        applyPermutationChanges(permutation, permutationChanges, m);
        applyPermutationChangesWithArrayUpdate(permutation, permutationChanges, A, p, p + m - 1);
        for (int j = 0; j <= i - 1; j++) {
            A.exch(p + i + j, p + m + j);
            permutation.exch(i + j, m + j);
        }
        permutationChanges = permutationProducingSelect(A, p, p + 2 * i - 1, i);
        applyPermutationChanges(permutation, permutationChanges, 0);
        return permutation;
    }

    private static <E extends Comparable<? super E>> ZeroBasedIndexedArray<Integer> permutationProducingSelect(
            Array<E> A, int p, int r, int i) {
        int n = r - p + 1;
        ZeroBasedIndexedArray<Integer> permutation = ZeroBasedIndexedArray.withLength(n);
        for (int j = 0; j <= n - 1; j++) {
            permutation.set(j, j);
        }
        if (n == 1) {
            return permutation;
        }
        ZeroBasedIndexedArray<Array<E>> groups = ZeroBasedIndexedArray.withLength(ceil(n, 5));
        for (int j = 0; j < groups.length - 1; j++) {
            groups.set(j, Array.withLength(5));
        }
        groups.set(groups.length - 1, Array.withLength(n % 5 > 0 ? n % 5 : 5));
        for (int j = p; j <= r; j++) {
            groups.at((j - p) / 5).set((j - p) % 5 + 1, A.at(j));
        }
        Array<E> medians = Array.withLength(groups.length);
        for (int j = 0; j < groups.length; j++) {
            insertionSort(groups.at(j));
            medians.set(j + 1, groups.at(j).at((groups.at(j).length + 1) / 2));
        }
        E x = select(medians, 1, medians.length, (medians.length + 1) / 2);
        int k = permutationChangingPartitionAround(A, p, r, x, permutation) - p + 1;
        if (i < k) {
            ZeroBasedIndexedArray<Integer> permutationChanges = permutationProducingSelect(A, p, p + k - 2, i);
            applyPermutationChanges(permutation, permutationChanges, 0);
        } else if (i > k) {
            ZeroBasedIndexedArray<Integer> permutationChanges = permutationProducingSelect(A, p + k, r, i - k);
            applyPermutationChanges(permutation, permutationChanges, k);
        }
        return permutation;
    }

    private static void applyPermutationChanges(
            ZeroBasedIndexedArray<Integer> permutation, ZeroBasedIndexedArray<Integer> permutationChanges, int offset) {
        ZeroBasedIndexedArray<Integer> appliedChanges = ZeroBasedIndexedArray.withLength(permutationChanges.length);
        for (int j = 0; j <= permutationChanges.length - 1; j++) {
            appliedChanges.set(j, permutation.at(offset + permutationChanges.at(j)));
        }
        for (int j = 0; j <= permutationChanges.length - 1; j++) {
            permutation.set(offset + j, appliedChanges.at(j));
        }
    }

    private static <E extends Comparable<? super E>> void applyPermutationChangesWithArrayUpdate(
            ZeroBasedIndexedArray<Integer> permutation,
            ZeroBasedIndexedArray<Integer> permutationChanges,
            Array<E> A,
            int p,
            int r) {
        int changesLength = r - p + 1;
        ZeroBasedIndexedArray<Integer> appliedChanges = ZeroBasedIndexedArray.withLength(changesLength);
        Array<E> permutedArrayFragment = Array.withLength(changesLength);
        for (int j = 0; j <= changesLength - 1; j++) {
            if (permutationChanges.at(j) < changesLength - 1) {
                appliedChanges.set(j, permutation.at(permutationChanges.at(j)));
                permutedArrayFragment.set(j + 1, A.at(p + permutationChanges.at(j)));
            } else {
                appliedChanges.set(j, permutation.at(permutationChanges.at(changesLength - 1)));
                permutedArrayFragment.set(j + 1, A.at(p + permutationChanges.at(changesLength - 1)));
            }
        }
        for (int j = 0; j <= changesLength - 1; j++) {
            permutation.set(j, appliedChanges.at(j));
            A.set(p + j, permutedArrayFragment.at(j + 1));
        }
    }

    private static <E extends Comparable<? super E>> int permutationChangingPartitionAround(
            Array<E> A, int p, int r, E x, ZeroBasedIndexedArray<Integer> permutation) {
        int q = p;
        while (!A.at(q).equals(x)) {
            q++;
        }
        A.exch(q, r);
        permutation.exch(q - p, r - p);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (less(A.at(j), x)) {
                i++;
                A.exch(i, j);
                permutation.exch(i - p, j - p);
            }
        }
        A.exch(i + 1, r);
        permutation.exch(i + 1 - p, r - p);
        return i + 1;
    }

}
