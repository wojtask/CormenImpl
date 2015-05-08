package pl.kwojtas.cormenimpl.util;

/**
 * Implements a heap.
 *
 * @param <T> the type of elements in the heap
 */
public class Heap<T> extends Array<T> {

    /**
     * The number of elements in the heap.
     */
    public int heapSize;

    /**
     * Creates a heap from the existing array and initial array length.
     *
     * @param array the array of initial elements
     * @param initialLength the length of the heap's underlying array
     * @throws RuntimeException if the {@code array} is longer than {@code initialLength}
     */
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

    /**
     * Creates a heap by copying an existing array.
     *
     * @param array the array to be copied
     */
    public Heap(Array<T> array) {
        this(array, array.length);
    }

    /**
     * Creates an empty heap with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @param <T> the type of elements in the new heap
     * @return the empty heap with an underlying array of length {@code length}
     */
    public static <T> Heap<T> withLength(int length) {
        return new Heap<>(new Array<>(), length);
    }

}
