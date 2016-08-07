package pl.kwojtas.cormenimpl.util;

/**
 * Implements a 0-based indexed array.
 *
 * @param <E> the type of elements in the array
 */
public class ZeroBasedIndexedArray<E> {

    private E[] data;

    /**
     * The number of elements in the array.
     */
    public int length;

    /**
     * Creates an array from given elements.
     *
     * @param elements the initial contents of the array
     */
    @SafeVarargs
    public ZeroBasedIndexedArray(E... elements) {
        this.data = elements;
        this.length = elements.length;
    }

    /**
     * Creates an array of a given length.
     *
     * @param length the length of the new array
     * @param <E>    the type of elements in the new array
     * @return the array of length {@code length} filled with {@code null}s
     */
    @SuppressWarnings("unchecked")
    public static <E> ZeroBasedIndexedArray<E> withLength(int length) {
        return new ZeroBasedIndexedArray<>((E[]) new Object[length]);
    }

    /**
     * Creates an array by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    public ZeroBasedIndexedArray(ZeroBasedIndexedArray<E> otherArray) {
        set(otherArray);
    }

    /**
     * Returns an element at a given position.
     *
     * @param position the position of the element to return
     * @return the element at position {@code position}
     * @throws IllegalStateException if {@code position < 1} or {@code position > length}
     */
    public E at(int position) {
        if (position < 0 || position > length - 1) {
            throw new IllegalStateException("Array index out of bound");
        }
        return data[position];
    }

    /**
     * Sets an element at a given position.
     *
     * @param position the position of the element to set
     * @param element  the new element
     * @throws IllegalStateException if {@code position < 1} or {@code position > length}
     */
    public void set(int position, E element) {
        if (position < 0 || position > length - 1) {
            throw new IllegalStateException("Array index out of bound");
        }
        data[position] = element;
    }

    /**
     * Sets the array contents by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    @SuppressWarnings("unchecked")
    public void set(ZeroBasedIndexedArray<E> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = (E[]) new Object[otherArray.length];
        System.arraycopy(otherArray.data, 0, data, 0, otherArray.length);
        this.length = otherArray.length;
    }

    /**
     * Exchanges two elements in the array.
     *
     * @param position1 the position of the first element
     * @param position2 the position of the second element
     */
    public void exch(int position1, int position2) {
        E swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }

}
