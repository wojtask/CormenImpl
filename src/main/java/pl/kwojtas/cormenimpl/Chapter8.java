package pl.kwojtas.cormenimpl;

import pl.kwojtas.cormenimpl.util.Array;

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
            stableSortOnDigit(A, i);
        }
    }

    // subchapter 8.3
    private static void stableSortOnDigit(Array<Integer> A, int digit) {
        Array<Integer> C = new Array<Integer>().withFirstPosition(0);
        for (int i = 0; i <= 9; i++) {
            C.set(i, 0);
        }
        for (int j = 1; j <= A.length; j++) {
            C.set(getDigit(A.at(j), digit), C.at(getDigit(A.at(j), digit)) + 1);
        }
        for (int i = 1; i <= 9; i++) {
            C.set(i, C.at(i) + C.at(i - 1));
        }
        Array<Integer> B = new Array<>(A);
        for (int j = A.length; j >= 1; j--) {
            B.set(C.at(getDigit(A.at(j), digit)), A.at(j));
            C.set(getDigit(A.at(j), digit), C.at(getDigit(A.at(j), digit)) - 1);
        }
        for (int j = 1; j <= A.length; j++) {
            A.set(j, B.at(j));
        }
    }

    // subchapter 8.3
    private static int getDigit(int number, int digit) {
        while (digit > 1) {
            number /= 10;
            digit--;
        }
        return number % 10;
    }


}
