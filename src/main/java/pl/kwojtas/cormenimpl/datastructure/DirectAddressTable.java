package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a direct-address table that does not require keys of elements to be distinct.
 *
 * @param <E> the type of elements in the table
 */
public class DirectAddressTable<E> extends ZeroBasedIndexedArray<DirectAddressTable.Element<E>> {

    /**
     * Implements a direct address table's element.
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
         * The index of the previous element.
         */
        public Element<F> prev;

        /**
         * The index of the next element.
         */
        public Element<F> next;

        /**
         * Creates an element with given key and satellite data and with no previous nor next element.
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
     * Creates a direct-address table of a given length.
     *
     * @param length the length of the new direct-address table
     */
    public DirectAddressTable(int length) {
        super(ZeroBasedIndexedArray.ofLength(length));
    }

}
