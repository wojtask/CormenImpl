package pl.kwojtas.cormenimpl.util;

/**
 * Implements a doubly linked list using the multiple-array representation.
 *
 * @param <T> the type of elements in the list
 */
public class MultipleArrayList<T> {

    /**
     * The array of indexes of next elements.
     */
    public Array<Integer> next;

    /**
     * The array of keys.
     */
    public Array<T> key;

    /**
     * The array of indexes of previous elements.
     */
    public Array<Integer> prev;

    /**
     * The index of the head of the list.
     */
    public Integer L;

    /**
     * The index of the head of the free list.
     */
    public Integer free;

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of elements in the list
     */
    public int getLength() {
        int length = 0;
        Integer x = L;
        while (x != null) {
            length++;
            x = next.at(x);
        }
        return length;
    }

}
