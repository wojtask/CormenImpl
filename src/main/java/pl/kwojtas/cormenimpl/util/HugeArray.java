package pl.kwojtas.cormenimpl.util;

/**
 * Implements a huge array.
 *
 * @param <E> the type of elements in the huge array
 */
public class HugeArray<E> {

    /**
     * The array containing indices of elements in stack {@code S}.
     */
    public ZeroBasedIndexedArray<Integer> T;

    /**
     * The auxiliary stack containing the elements of the huge array.
     */
    public Stack<Element<E>> S;

    /**
     * Creates a huge array with a given size and stack capacity.
     *
     * @param size     the size of the new huge array
     * @param capacity the capacity of the auxiliary stack of the new huge array
     */
    public HugeArray(int size, int capacity) {
        T = ZeroBasedIndexedArray.withLength(size);
        for (int i = 0; i <= size - 1; i++) {
            T.set(i, 0); // initializing T as it contains nulls; the initializer can be anything nonnull
        }
        S = Stack.withLength(capacity);
    }

}
