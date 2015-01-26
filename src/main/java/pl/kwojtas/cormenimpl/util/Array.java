package pl.kwojtas.cormenimpl.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Array<T> {

    private ArrayList<T> data;
    public int length;

    @SafeVarargs
    public Array(T... elements) {
        this.data = new ArrayList<>(Arrays.asList(elements));
        this.length = elements.length;
    }

    public Array(Array<T> otherArray) {
        set(otherArray);
    }

    Array(ArrayList<T> otherArrayList) {
        this.data = new ArrayList<>(otherArrayList);
        this.length = otherArrayList.size();
    }

    public ArrayList<T> getData() {
        return data;
    }

    public T at(int position) {
        if (position < 1 || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        return data.get(position - 1);
    }

    public void set(int position, T element) {
        if (position < 1) {
            throw new RuntimeException("Array index out of bound");
        }
        if (position > length) {
            for (int i = length + 1; i <= position; i++) {
                data.add(null);
            }
            length = position;
        }
        data.set(position - 1, element);
    }

    public void set(Array<T> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = new ArrayList<>(otherArray.data);
        this.length = otherArray.length;
    }

    public void exch(int position1, int position2) {
        T swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }

}
