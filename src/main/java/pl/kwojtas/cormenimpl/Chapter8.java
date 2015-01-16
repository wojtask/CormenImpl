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

}
