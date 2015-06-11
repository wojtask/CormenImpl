package pl.kwojtas.cormenimpl.util;

/**
 * Implements a huge array.
 *
 * @param <T> the type of elements in the huge array
 */
public class HugeArray<T> {

    /**
     * The array containing indexes of elements in stack {@code S}.
     */
    public ZeroBasedIndexedArray<Integer> T;

    /**
     * The auxiliary stack containing the elements of the huge array.
     */
    public Stack<Element<T>> S;

    /**
     * Creates a huge array with a given size and stack capacity.
     *
     * @param size the size of the new huge array
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
