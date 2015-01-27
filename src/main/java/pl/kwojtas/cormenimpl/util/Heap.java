package pl.kwojtas.cormenimpl.util;

public class Heap<T> extends Array<T> {

    public int heapSize;

    public Heap(Array<T> array, int initialLength) {
        super(Array.withLength(initialLength));
        if (array.length > initialLength) {
            throw new RuntimeException("Array is larger than initial length");
        }
        for (int i = 1; i <= array.length; i++) {
            set(i, array.at(i));
        }
        this.heapSize = array.length;
    }

    public Heap(Array<T> array) {
        this(array, array.length);
    }

    public static <T> Heap<T> withLength(int length) {
        return new Heap<>(new Array<>(), length);
    }

}
