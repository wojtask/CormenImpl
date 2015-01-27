package pl.kwojtas.cormenimpl.util;

public class Array<T> {

    private T[] data;
    public int length;

    @SafeVarargs
    public Array(T... elements) {
        this.data = elements;
        this.length = elements.length;
    }

    public static <T> Array<T> withLength(int length) {
        return new Array<>((T[]) new Object[length]);
    }

    public Array(Array<T> otherArray) {
        set(otherArray);
    }

    public T at(int position) {
        if (position < 1 || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        return data[position - 1];
    }

    public void set(int position, T element) {
        if (position < 1 || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        data[position - 1] = element;
    }

    public void set(Array<T> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = (T[]) new Object[otherArray.length];
        System.arraycopy(otherArray.data, 0, data, 0, otherArray.length);
        this.length = otherArray.length;
    }

    public void exch(int position1, int position2) {
        T swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }

}
