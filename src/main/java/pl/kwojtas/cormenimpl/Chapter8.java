package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;

import static pl.kwojtas.cormenimpl.Chapter5.random;

public class Chapter8 {

    private Chapter8() { }

    // subchapter 8.2
    public static void countingSort(Array<Integer> A, Array<Integer> B, int k) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(A.at(j)), A.at(j));
            C.set(A.at(j), C.at(A.at(j)) - 1);
        }
    }

    // exercise 8.2-3
    public static void nonStableCountingSort(Array<Integer> A, Array<Integer> B, int k) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        for (int j = 1; j <= A.length; j++) {
            B.set(C.at(A.at(j)), A.at(j));
            C.set(A.at(j), C.at(A.at(j)) - 1);
        }
    }

    // solution of 8.2-4
    public static int countNumbersInRange(Array<Integer> A, int k, int a, int b) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        if (0 < a && a <= b && b <= k) {
            return C.at(b) - C.at(a - 1);
        }
        if (0 < a && a <= k && k < b) {
            return C.at(k) - C.at(a - 1);
        }
        if (a <= 0 && 0 <= b && b <= k) {
            return C.at(b);
        }
        if (a <= 0 && 0 <= k && k < b) {
            return C.at(k);
        }
        return 0;
    }

    // subchapter 8.3
    public static void radixSort(Array<Integer> A, int d) {
        for (int i = 1; i <= d; i++) {
            stableSortOnDigit(A, i, 10);
        }
    }

    // subchapter 8.3
    private static void stableSortOnDigit(Array<Integer> A, int digit, int k) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= k - 1; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(getDigit(A.at(j), digit, k), C.at(getDigit(A.at(j), digit, k)) + 1);
        }
        for (int i = 1; i <= k - 1; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        Array<Integer> B = new Array<>(A);
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(getDigit(A.at(j), digit, k)), A.at(j));
            C.set(getDigit(A.at(j), digit, k), C.at(getDigit(A.at(j), digit, k)) - 1);
        }
        for (int j = 1; j <= A.length; j++) {
            A.set(j, B.at(j));
        }
    }

    // subchapter 8.3
    private static int getDigit(int number, int digit, int k) {
        while (digit > 1) {
            number /= k;
            digit--;
        }
        return number % k;
    }

    // solution of 8.3-4
    public static void sortNSquaredNumbers(Array<Integer> A) {
        int n = A.length;
        stableSortOnDigit(A, 1, n);
        stableSortOnDigit(A, 2, n);
    }

    // solution of 8-2(b)
    public static void bitwiseSort(Array<Integer> A) {
        int n = A.length;
        int i = 1;
        int j = n;
        while (i < j) {
            A.exch(i, j);
            while (i <= n && A.at(i).equals(0)) {
                i++;
            }
            while (j >= 1 && A.at(j).equals(1)) {
                j--;
            }
        }

    }

    // solution of 8-2(e)
    public static void countingSortInPlace(Array<Integer> A, int k) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= k; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(A.at(j), C.at(A.at(j)) + 1);
        }
        for (int i = 1; i <= k; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        for (int i = 0; i <= k - 1; i++) {
            int j = C.at(i);
            while (j > 0 && A.at(j) >= i) {
                while (A.at(j) > i) {
                    C.set(A.at(j), C.at(A.at(j)) - 1);
                    A.exch(j, C.at(A.at(j)) + 1);
                }
                j--;
            }
        }
    }

    // solution of 8-4(a)
    public static void jugsGroup(Array<Double> R, Array<Double> B) {
        int n = R.length;
        for (int i = 1; i <= n - 1; i++) {
            int j = i;
            while (!R.at(i).equals(B.at(j))) {
                j++;
            }
            B.exch(i, j);
        }
    }

    // solution of 8-4(c)
    public static void jugsMatch(Array<Double> R, Array<Double> B, int p, int r) {
        if (p < r) {
            int q = jugsPartition(R, B, p, r);
            jugsMatch(R, B, p, q - 1);
            jugsMatch(R, B, q + 1, r);
        }
    }

    // solution of 8-4(c)
    private static int jugsPartition(Array<Double> R, Array<Double> B, int p, int r) {
        R.exch(r, random(p, r));
        double x = R.at(r);
        int i = p;
        while (!B.at(i).equals(x)) {
            i++;
        }
        B.exch(i, r);
        i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (B.at(j) < x) {
                i++;
                B.exch(i, j);
            }
        }
        B.exch(i + 1, r);
        x = B.at(i + 1);
        i = p - 1;
        for (int j = p; j <= r - 1; j++) {
            if (R.at(j) < x) {
                i++;
                R.exch(i, j);
            }
        }
        R.exch(i + 1, r);
        return i + 1;
    }

    // solution of 8-5(d)
    public static void kSort(Array<Integer> A, int k, int p, int r) {
        if (p + k - 1 < r) {
            int q = Chapter7.partition(A, p, r);
            kSort(A, k, p, q - 1);
            kSort(A, k, q + 1, r);
        }
    }

}
