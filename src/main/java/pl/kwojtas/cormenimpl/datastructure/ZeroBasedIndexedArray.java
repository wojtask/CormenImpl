package pl.kwojtas.cormenimpl.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Implements a 0-based indexed array.
 *
 * @param <E> the type of elements in the array
 */
public class ZeroBasedIndexedArray<E> {

    private java.util.List<E> data;

    /**
     * The number of elements in the array.
     */
    public int length;

    private ZeroBasedIndexedArray(java.util.List<E> initialData) {
        this.data = initialData;
        this.length = initialData.size();
    }

    protected ZeroBasedIndexedArray(ZeroBasedIndexedArray<E> otherArray) {
        set(otherArray);
    }

    /**
     * Creates an array of given elements.
     *
     * @param elements the initial contents of the array
     * @return the array containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> ZeroBasedIndexedArray<E> of(E... elements) {
        java.util.List<E> initialData = Arrays.asList(elements);
        return new ZeroBasedIndexedArray<>(initialData);
    }

    /**
     * Returns an array of a given length.
     *
     * @param length the length of the new array
     * @return the array of length {@code length} filled with {@code null}s
     */
    public static <E> ZeroBasedIndexedArray<E> ofLength(int length) {
        java.util.List<E> initialData = new ArrayList<>(Collections.nCopies(length, null));
        return new ZeroBasedIndexedArray<>(initialData);
    }

    /**
     * Returns a copy of an existing array.
     *
     * @param otherArray the array to be copied
     * @return the copy of {@code otherArray}
     */
    public static <E> ZeroBasedIndexedArray<E> copyOf(ZeroBasedIndexedArray<E> otherArray) {
        return new ZeroBasedIndexedArray<>(otherArray);
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
        return data.get(position);
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
        data.set(position, element);
    }

    /**
     * Sets the array contents by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    public void set(ZeroBasedIndexedArray<E> otherArray) {
        if (this == otherArray) {
            return;
        }
        this.data = new ArrayList<>(otherArray.data);
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
