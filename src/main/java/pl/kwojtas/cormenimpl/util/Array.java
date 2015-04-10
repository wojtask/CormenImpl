package pl.kwojtas.cormenimpl.util;

/**
 * Implements a 1-based indexed array.
 *
 * @param <T> the type of elements in the array
 */
public class Array<T> {

    private T[] data;

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
    public Array(T... elements) {
        this.data = elements;
        this.length = elements.length;
    }

    /**
     * Creates an array of a given length.
     *
     * @param length the length of the new array
     * @param <T> the type of elements in the new array
     * @return the array of length {@code length} filled with {@code null}s
     */
    public static <T> Array<T> withLength(int length) {
        return new Array<>((T[]) new Object[length]);
    }

    /**
     * Creates an array by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    public Array(Array<T> otherArray) {
        set(otherArray);
    }

    /**
     * Returns an element at a given position.
     *
     * @param position the position of the element to return
     * @return the element at position {@code position}
     * @throws RuntimeException if {@code position < 1} or {@code position > length}
     */
    public T at(int position) {
        if (position < 1 || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        return data[position - 1];
    }

    /**
     * Sets an element at a given position.
     *
     * @param position the position of the element to set
     * @param element the new element
     * @throws RuntimeException if {@code position < 1} or {@code position > length}
     */
    public void set(int position, T element) {
        if (position < 1 || position > length) {
            throw new RuntimeException("Array index out of bound");
        }
        data[position - 1] = element;
    }

    /**
     * Sets the array contents by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    public void set(Array<T> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = (T[]) new Object[otherArray.length];
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
        T swap = at(position1);
        set(position1, at(position2));
        set(position2, swap);
    }

}
