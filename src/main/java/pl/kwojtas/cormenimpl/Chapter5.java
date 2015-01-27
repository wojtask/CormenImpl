package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Util;

import java.util.Random;

public class Chapter5 {

    private static Random rand = new Random(System.currentTimeMillis());
    private static double probabilityForBiasedRandom = rand.nextDouble();

    private Chapter5() { }

    // solution of 5.1-2
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

    // solution of 5.1-3
    public static int unbiasedRandom() {
        int x, y;
        do {
            x = biasedRandom();
            y = biasedRandom();
        } while (x == y);
        return x;
    }

    // solution of 5.1-3
    private static int biasedRandom() {
        return rand.nextDouble() <= probabilityForBiasedRandom ? 1 : 0;
    }

    // subchapter 5.3
    public static <T> Array<T> permuteBySorting(Array<T> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUsingExternalKeys(A, P, 1, n);
        return A;
    }

    // subchapter 5.3
    private static <T> void sortUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUsingExternalKeys(A, keys, p, q);
            sortUsingExternalKeys(A, keys, q + 1, r);
            mergeUsingExternalKeys(A, keys, p, q, r);
        }
    }

    // subchapter 5.3
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

    // subchapter 5.3
    public static <T> void randomizeInPlace(Array<T> A) {
        int n = A.length;
        for (int i = 1; i <= n; i++) {
            A.exch(i, random(i, n));
        }
    }

    // solution of 5.3-1
    public static <T> void randomizeInPlace_(Array<T> A) {
        int n = A.length;
        A.exch(1, random(1, n));
        for (int i = 2; i <= n; i++) {
            A.exch(i, random(i, n));
        }
    }

    // solution of 5.3-6
    public static <T> Array<T> permuteUniformlyBySorting(Array<T> A) {
        int n = A.length;
        Array<Integer> P = Array.withLength(n);
        for (int i = 1; i <= n; i++) {
            P.set(i, random(1, n * n * n));
        }
        sortUniformlyUsingExternalKeys(A, P, 1, n);
        return A;
    }

    // solution of 5.3-6
    private static <T> void sortUniformlyUsingExternalKeys(Array<T> A, Array<Integer> keys, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortUniformlyUsingExternalKeys(A, keys, p, q);
            sortUniformlyUsingExternalKeys(A, keys, q + 1, r);
            mergeUniformlyUsingExternalKeys(A, keys, p, q, r);
        }
    }

    // solution of 5.3-6
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

    // solution of 5-2(a)
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
