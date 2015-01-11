package pl.kwojtas.cormenimpl;

import static pl.kwojtas.cormenimpl.Util.less;

public class Chapter2 {

    private Chapter2() { }

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
}
