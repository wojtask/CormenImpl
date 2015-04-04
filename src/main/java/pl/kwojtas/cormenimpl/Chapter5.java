package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Util;

import java.util.Random;

/**
 * Implements algorithms from Chapter 5.
 */
public final class Chapter5 {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final double UNFAIR_COIN_PROBABILITY = RANDOM.nextDouble();

    private Chapter5() { }

    /**
     * Returns random number from {@code a..b}.
     * <p><span style="font-variant:small-caps;">Random</span> from solution to exercise 5.1-2.</p>
     *
     * @param a the lower bound
     * @param b the upper bound
     * @return random number from {@code a..b}
     */
    public static int random(int a, int b) {
        while (a < b) {
            int mid = (a + b) / 2;
            if (Util.random() == 0) {
                a = mid + 1;
            } else {
                b = mid;
            }
        }
        return a;
    }

    /**
     * Flips a fair coin.
     * <p><span style="font-variant:small-caps;">Unbiased-Random</span> from solution to exercise 5.1-3.</p>
     *
     * @return {@code 0} with probability {@code 1/2} and {@code 1} with probability {@code 1/2}
     */
    public static int unbiasedRandom() {
        int x, y;
        do {
            x = biasedRandom();
            y = biasedRandom();
        } while (x == y);
        return x;
    }

    /**
     * Flips an unfair coin.
     *
     * @return {@code 0} with probability {@code 1 - p} and {@code 1} with probability {@code p}
     */
    private static int biasedRandom() {
        return RANDOM.nextDouble() <= UNFAIR_COIN_PROBABILITY ? 1 : 0;
    }

    /**
     * Permutes an array by sorting it.
     * <p><span style="font-variant:small-caps;">Permute-By-Sorting</span> from subchapter 5.3.</p>
     *
     * @param A the {@link Array} of elements to permute
     * @param <T> the type of elements in {@code A}
     * @return the permuted array
     */
    public static <T> Array<T> permuteBySorting(Array<T> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUsingExternalKeys(A, P, 1, n);
        return A;
    }

    /**
     * Sorts an array using another array as keys.
     *
     * @param A the {@link Array} of elements to sort
     * @param keys the {@link Array} of keys
     * @param p the index of the beginning of subarray in {@code A} to sort
     * @param r the index of the end of subarray in {@code A} to sort
     * @param <T> the type of elements in {@code A}
     */
    private static <T> void sortUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUsingExternalKeys(A, keys, p, q);
            sortUsingExternalKeys(A, keys, q + 1, r);
            mergeUsingExternalKeys(A, keys, p, q, r);
        }
    }

    /**
     * Merges two sorted subarrays {@code A[p..q]} and {@code A[q + 1..r]} into one sorted array {@code A[p..r]} using another
     * array as keys.
     *
     * @param A the {@link Array} of elements to merge
     * @param keys the {@link Array} of keys
     * @param p the index of the beginning of the first subarray in {@code A} to merge
     * @param q the index of the end of the first subarray in {@code A} to merge
     * @param r the index of end of the second subarray in {@code A} to merge
     * @param <T> the type of elements in {@code A}
     */
    private static <T> void mergeUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<T> L = Array.withLength(n1 + 1);
        Array<T> R = Array.withLength(n2 + 1);
        Array<Integer> keysL = Array.withLength(n1 + 1);
        Array<Integer> keysR = Array.withLength(n2 + 1);
        for (int i = 1; i <= n1; i++) {
            L.set(i, A.at(p + i - 1));
            keysL.set(i, keys.at(p + i - 1));
        }
        for (int j = 1; j <= n2; j++) {
            R.set(j, A.at(q + j));
            keysR.set(j, keys.at(q + j));
        }
        keysL.set(n1 + 1, Integer.MAX_VALUE);
        keysR.set(n2 + 1, Integer.MAX_VALUE);
        int i = 1;
        int j = 1;
        for (int k = p; k <= r; k++) {
            if (keysL.at(i) <= keysR.at(j)) {
                A.set(k, L.at(i));
                keys.set(k, keysL.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                keys.set(k, keysR.at(j));
                j++;
            }
        }
    }

    /**
     * Permutes an array in place.
     * <p><span style="font-variant:small-caps;">Randomize-In-Place</span> from subchapter 5.3.</p>
     *
     * @param A the {@link Array} of elements to permute
     * @param <T> the type of elements in {@code A}
     */
    public static <T> void randomizeInPlace(Array<T> A) {
        int n = A.length;
        for (int i = 1; i <= n; i++) {
            A.exch(i, random(i, n));
        }
    }

    /**
     * Permutes an array in place - alternative version.
     * <p><span style="font-variant:small-caps;">Randomize-In-Place'</span> from solution to exercise 5.3-1.</p>
     *
     * @param A the {@link Array} of elements to permute
     * @param <T> the type of elements in {@code A}
     */
    public static <T> void randomizeInPlace_(Array<T> A) {
        int n = A.length;
        A.exch(1, random(1, n));
        for (int i = 2; i <= n; i++) {
            A.exch(i, random(i, n));
        }
    }

    /**
     * Permutes an array uniformly by sorting.
     * <p>Solution to exercise 5.3-6.</p>
     *
     * @param A the {@link Array} of elements to permute
     * @param <T> the type of elements in {@code A}
     * @return the permuted array
     */
    public static <T> Array<T> permuteUniformlyBySorting(Array<T> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUniformlyUsingExternalKeys(A, P, 1, n);
        return A;
    }

    /**
     * Sorts an array uniformly using another array as keys.
     *
     * @param A the {@link Array} of elements to sort
     * @param keys the {@link Array} of keys
     * @param p the index of the beginning of subarray in {@code A} to sort
     * @param r the index of the end of subarray in {@code A} to sort
     * @param <T> the type of elements in {@code A}
     */
    private static <T> void sortUniformlyUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUniformlyUsingExternalKeys(A, keys, p, q);
            sortUniformlyUsingExternalKeys(A, keys, q + 1, r);
            mergeUniformlyUsingExternalKeys(A, keys, p, q, r);
        }
    }

    /**
     * Merges two sorted subarrays {@code A[p..q]} and {@code A[q + 1..r]} uniformly into one sorted array {@code A[p..r]}
     * using another array as keys.
     *
     * @param A the {@link Array} of elements to merge
     * @param keys the {@link Array} of keys
     * @param p the index of the beginning of the first subarray in {@code A} to merge
     * @param q the index of the end of the first subarray in {@code A} to merge
     * @param r the index of end of the second subarray in {@code A} to merge
     * @param <T> the type of elements in {@code A}
     */
    private static <T> void mergeUniformlyUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<T> L = Array.withLength(n1 + 1);
        Array<T> R = Array.withLength(n2 + 1);
        Array<Integer> keysL = Array.withLength(n1 + 1);
        Array<Integer> keysR = Array.withLength(n2 + 1);
        for (int i = 1; i <= n1; i++) {
            L.set(i, A.at(p + i - 1));
            keysL.set(i, keys.at(p + i - 1));
        }
        for (int j = 1; j <= n2; j++) {
            R.set(j, A.at(q + j));
            keysR.set(j, keys.at(q + j));
        }
        keysL.set(n1 + 1, Integer.MAX_VALUE);
        keysR.set(n2 + 1, Integer.MAX_VALUE);
        int i = 1;
        int j = 1;
        for (int k = p; k <= r; k++) {
            if (keysL.at(i) < keysR.at(j) || (keysL.at(i).equals(keysR.at(j)) && Util.random() == 0)) {
                A.set(k, L.at(i));
                keys.set(k, keysL.at(i));
                i++;
            } else {
                A.set(k, R.at(j));
                keys.set(k, keysR.at(j));
                j++;
            }
        }
    }

    /**
     * Searches for an element in an array by picking array elements at random.
     * <p><span style="font-variant:small-caps;">Random-Search</span> from solution to problem 5-2(a).</p>
     *
     * @param A the {@link Array} of elements
     * @param x the element to find
     * @param <T> the type of elements in {@code A}
     * @return index {@code i} such that {@code A[i] = v}, or {@code null} if {@code v} does not appear in {@code A}
     */
    public static <T> Integer randomSearch(Array<T> A, T x) {
        int n = A.length;
        Array<Boolean> B = Array.withLength(n);
        for (int k = 1; k <= n; k++) {
            B.set(k, false);
        }
        int checked = 0;
        while (checked < n) {
            int i = random(1, n);
            if (A.at(i).equals(x)) {
                return i;
            }
            if (!B.at(i)) {
                B.set(i, true);
                checked++;
            }
        }
        return null;
    }

}
