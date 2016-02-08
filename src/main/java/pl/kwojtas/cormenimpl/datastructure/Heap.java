package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a heap.
 *
 * @param <E> the type of elements in the heap
 */
public class Heap<E> extends Array<E> {

    /**
     * The number of elements in the heap.
     */
    public int heapSize;

    /**
     * Creates a heap from the existing array and initial length of an underlying array.
     *
     * @param array         the array of initial elements
     * @param initialLength the length of the heap's underlying array
     * @throws IllegalStateException if the {@code array} is longer than {@code initialLength}
     */
    public Heap(Array<E> array, int initialLength) {
        super(Array.withLength(initialLength));
        if (array.length > initialLength) {
            throw new IllegalStateException("Array is larger than initial length");
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
    public Heap(Array<E> array) {
        this(array, array.length);
    }

    /**
     * Creates an empty heap with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @param <E>    the type of elements in the new heap
     * @return the empty heap with an underlying array of length {@code length}
     */
    public static <E> Heap<E> withLength(int length) {
        return new Heap<>(new Array<>(), length);
    }

}
