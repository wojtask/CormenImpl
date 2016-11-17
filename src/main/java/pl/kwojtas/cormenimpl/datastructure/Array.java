package pl.kwojtas.cormenimpl.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Implements a 1-based indexed array.
 *
 * @param <E> the type of elements in the array
 */
public class Array<E> {

    private java.util.List<E> data;

    /**
     * The number of elements in the array.
     */
    public int length;

    private Array(java.util.List<E> initialData) {
        this.data = initialData;
        this.length = initialData.size();
    }

    protected Array(Array<E> otherArray) {
        set(otherArray);
    }

    /**
     * Returns an empty array (an array containing 0 elements).
     *
     * @return the empty array
     */
    public static <E> Array<E> emptyArray() {
        return new Array<>(Collections.emptyList());
    }

    /**
     * Creates an array from given elements.
     *
     * @param elements the initial contents of the array
     * @return the array containing elements from {@code elements}
     */
    @SafeVarargs
    public static <E> Array<E> of(E... elements) {
        java.util.List<E> initialData = Arrays.asList(elements);
        return new Array<>(initialData);
    }

    /**
     * Returns an array of a given length.
     *
     * @param length the length of the new array
     * @return the array of length {@code length} filled with {@code null}s
     */
    public static <E> Array<E> withLength(int length) {
        java.util.List<E> initialData = new ArrayList<>(Collections.nCopies(length, null));
        return new Array<>(initialData);
    }

    /**
     * Returns a copy of an existing array.
     *
     * @param otherArray the array to be copied
     * @return the copy of {@code otherArray}
     */
    public static <E> Array<E> copyOf(Array<E> otherArray) {
        return new Array<>(otherArray);
    }

    /**
     * Returns an element at a given position.
     *
     * @param position the position of the element to return
     * @return the element at position {@code position}
     * @throws IllegalStateException if {@code position < 1} or {@code position > length}
     */
    public E at(int position) {
        if (position < 1 || position > length) {
            throw new IllegalStateException("Array index out of bound");
        }
        return data.get(position - 1);
    }

    /**
     * Sets an element at a given position.
     *
     * @param position the position of the element to set
     * @param element  the new element
     * @throws IllegalStateException if {@code position < 1} or {@code position > length}
     */
    public void set(int position, E element) {
        if (position < 1 || position > data.size()) {
            throw new IllegalStateException("Array index out of bound");
        }
        data.set(position - 1, element);
    }

    /**
     * Sets the array contents by copying an existing array.
     *
     * @param otherArray the array to be copied
     */
    public void set(Array<E> otherArray) {
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
