package pl.kwojtas.cormenimpl;

import java.util.ArrayList;
import java.util.Arrays;

public class Array<T> {

    private ArrayList<T> data;
    private int firstPosition;
    public int length;

    @SafeVarargs
    public Array(T... elements) {
        this.data = new ArrayList<>(Arrays.asList(elements));
        this.firstPosition = 1;
        this.length = data.size();
    }

    public Array(Array<T> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = new ArrayList<>(otherArray.data);
        this.firstPosition = otherArray.firstPosition;
        this.length = otherArray.length;
    }

    public static <T> Array<T> create(int firstPosition, int initialLength) {
        Array<T> array = new Array<>();
        for (int i = 1; i <= initialLength; i++) {
            array.data.add(null);
        }
        array.firstPosition = firstPosition;
        array.length = initialLength;
        return array;
    }

    private int toZeroBasedPosition(int arrayPosition) {
        return arrayPosition - firstPosition;
    }

    public T at(int position) {
        if (position < firstPosition || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        return data.get(toZeroBasedPosition(position));
    }

    public void set(int position, T element) {
        if (position < firstPosition) {
            throw new RuntimeException("Array index out of bound");
        }
        int lastPosition = firstPosition + length - 1;
        if (position > lastPosition) {
            for (int i = lastPosition + 1; i <= position; i++) {
                data.add(null);
            }
            length = position;
        }
        data.set(toZeroBasedPosition(position), element);
    }

    public void exch(int position1, int position2) {
        T swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }
}
