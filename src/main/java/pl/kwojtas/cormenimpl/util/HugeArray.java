package pl.kwojtas.cormenimpl.util;

/**
 * Implements a huge array.
 *
 * @param <E> the type of elements in the huge array
 */
public class HugeArray<E> {

    /**
     * Implements a huge array's element.
     *
     * @param <F> the type of satellite data
     */
    public static class Element<F> {

        /**
         * The key.
         */
        public int key;

        /**
         * The satellite data.
         */
        public F data;

        /**
         * Creates an element from a given key and satellite data.
         *
         * @param key  the key of the new element
         * @param data the satellite data of the new element
         */
        public Element(int key, F data) {
            this.key = key;
            this.data = data;
        }
    }

    /**
     * The array containing indices of elements in stack {@code S}.
     */
    public ZeroBasedIndexedArray<Integer> T;

    /**
     * The auxiliary stack containing the elements of the huge array.
     */
    public Stack<HugeArray.Element<E>> S;

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
