package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.datastructure.Array;

import java.util.Random;

/**
 * Implements algorithms from Chapter 5.
 */
public final class Chapter5 {

    private static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final double UNFAIR_COIN_PROBABILITY = RANDOM.nextDouble();

    private Chapter5() {
    }

    /**
     * Returns random number from interval {@code a..b}.
     * <p><span style="font-variant:small-caps;">Random</span> from solution to exercise 5.1-2.</p>
     *
     * @param a the lower bound of the interval
     * @param b the upper bound of the interval
     * @return random number from {@code a..b}
     */
    public static int random(int a, int b) {
        while (a < b) {
            int mid = (a + b) / 2;
            if (Fundamental.random() == 0) {
                a = mid + 1;
            } else {
                b = mid;
            }
        }
        return a;
    }

    /**
     * Flips a fair coin using subroutine that flips an unfair coin.
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
     * <p><span style="font-variant:small-caps;">Biased-Random</span> from exercise 5.1-3.</p>
     *
     * @return {@code 0} with probability {@code 1 - p} and {@code 1} with probability {@code p}
     */
    static int biasedRandom() {
        return RANDOM.nextDouble() <= UNFAIR_COIN_PROBABILITY ? 1 : 0;
    }

    /**
     * Permutes an array by sorting it.
     * <p><span style="font-variant:small-caps;">Permute-By-Sorting</span> from subchapter 5.3.</p>
     *
     * @param A   the array of elements to permute
     * @param <E> the type of elements in {@code A}
     * @return a permutation of {@code A}
     */
    public static <E> Array<E> permuteBySorting(Array<E> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUsingExternalKeys(A, P, 1, n);
        return A;
    }

    private static <E> void sortUsingExternalKeys(Array<E> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUsingExternalKeys(A, keys, p, q);
            sortUsingExternalKeys(A, keys, q + 1, r);
            mergeUsingExternalKeys(A, keys, p, q, r);
        }
    }

    private static <E> void mergeUsingExternalKeys(Array<E> A, Array<Integer> keys, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<E> L = Array.withLength(n1 + 1);
        Array<E> R = Array.withLength(n2 + 1);
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
     * @param A   the array of elements to permute
     * @param <E> the type of elements in {@code A}
     */
    public static <E> void randomizeInPlace(Array<E> A) {
        int n = A.length;
        for (int i = 1; i <= n; i++) {
            A.exch(i, random(i, n));
        }
    }

    /**
     * Permutes an array in place - an alternative version.
     * <p><span style="font-variant:small-caps;">Randomize-In-Place'</span> from solution to exercise 5.3-1.</p>
     *
     * @param A   the array of elements to permute
     * @param <E> the type of elements in {@code A}
     */
    public static <E> void randomizeInPlace_(Array<E> A) {
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
     * @param A   the array of elements to permute
     * @param <E> the type of elements in {@code A}
     * @return a permutation of {@code A}
     */
    public static <E> Array<E> permuteUniformlyBySorting(Array<E> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUniformlyUsingExternalKeys(A, P, 1, n);
        return A;
    }

    private static <E> void sortUniformlyUsingExternalKeys(Array<E> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUniformlyUsingExternalKeys(A, keys, p, q);
            sortUniformlyUsingExternalKeys(A, keys, q + 1, r);
            mergeUniformlyUsingExternalKeys(A, keys, p, q, r);
        }
    }

    private static <E> void mergeUniformlyUsingExternalKeys(Array<E> A, Array<Integer> keys, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        Array<E> L = Array.withLength(n1 + 1);
        Array<E> R = Array.withLength(n2 + 1);
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
            if (keysL.at(i) < keysR.at(j) || (keysL.at(i).equals(keysR.at(j)) && Fundamental.random() == 0)) {
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
     * Searches for an element in an array by picking elements at random.
     * <p><span style="font-variant:small-caps;">Random-Search</span> from solution to problem 5-2(a).</p>
     *
     * @param A   the array to scan
     * @param x   the element to find
     * @param <E> the type of elements in {@code A}
     * @return index {@code i} such that {@code A[i] = v}, or {@code null} if {@code v} does not appear in {@code A}
     */
    public static <E> Integer randomSearch(Array<E> A, E x) {
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
