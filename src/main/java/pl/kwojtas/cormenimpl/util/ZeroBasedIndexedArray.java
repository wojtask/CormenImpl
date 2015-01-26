package pl.kwojtas.cormenimpl.util;

import java.util.ArrayList;
import java.util.Arrays;

public class ZeroBasedIndexedArray<T> {

    private ArrayList<T> data;
    public int length;

    @SafeVarargs
    public ZeroBasedIndexedArray(T... elements) {
        this.data = new ArrayList<>(Arrays.asList(elements));
        this.length = elements.length;
    }

    public T at(int position) {
        if (position < 0 || position > length - 1) {
            throw new RuntimeException("Array index out of bound");
        }
        return data.get(position);
    }

    public void set(int position, T element) {
        if (position < 0) {
            throw new RuntimeException("Array index out of bound");
        }
        if (position > length - 1) {
            for (int i = length; i <= position; i++) {
                data.add(null);
            }
            length = position + 1;
        }
        data.set(position, element);
    }

    public void exch(int position1, int position2) {
        T swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }

}
