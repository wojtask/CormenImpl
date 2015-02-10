package pl.kwojtas.cormenimpl.util;

public class ZeroBasedIndexedArray<T> {

    private T[] data;
    public int length;

    @SafeVarargs
    public ZeroBasedIndexedArray(T... elements) {
        this.data = elements;
        this.length = elements.length;
    }

    public static <T> ZeroBasedIndexedArray<T> withLength(int length) {
        return new ZeroBasedIndexedArray<>((T[]) new Object[length]);
    }

    public ZeroBasedIndexedArray(ZeroBasedIndexedArray<T> otherArray) {
        set(otherArray);
    }

    public T at(int position) {
        if (position < 0 || position > length - 1) {
            throw new RuntimeException("Array index out of bound");
        }
        return data[position];
    }

    public void set(int position, T element) {
        if (position < 0) {
            throw new RuntimeException("Array index out of bound");
        }
        data[position] = element;
    }

    public void set(ZeroBasedIndexedArray<T> otherArray) {
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
