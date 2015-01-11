package pl.kwojtas.cormenimpl;

import static pl.kwojtas.cormenimpl.Util.less;

public class Chapter2 {

    private Chapter2() { }

    // subchapter 2.1
    public static <T> void insertionSort(Array<T> A) {
        for (int j = 2; j <= A.length; j++) {
            T key = A.at(j);
            int i = j - 1;
            while (i > 0 && less(key, A.at(i))) {
                A.set(i + 1, A.at(i));
                i--;
            }
            A.set(i + 1, key);
        }
    }

    // solution of 2.1-3
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

    // solution of 2.1-4
    public static Array<Integer> binaryAdd(Array<Integer> A, Array<Integer> B) {
        int n = A.length;
        Array<Integer> C = new Array<>();
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

}
