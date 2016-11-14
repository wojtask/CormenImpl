package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a doubly linked list using the multiple-array representation.
 *
 * @param <E> the type of elements in the list
 */
public class MultipleArrayList<E> {

    /**
     * The array of indices of next elements.
     */
    public Array<Integer> next;

    /**
     * The array of keys.
     */
    public Array<E> key;

    /**
     * The array of indices of previous elements.
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
     * Creates a doubly linked list using the multiple-array representation from given arrays and pointer indices.
     *
     * @param next the array of indices of next elements in the new list
     * @param key  the array of keys in the new list
     * @param prev the array of indices of previous elements in the new list
     * @param L    the index of the head of the new list
     * @param free the index of the head of the free list in the new list
     */
    public MultipleArrayList(Array<Integer> next, Array<E> key, Array<Integer> prev, Integer L, Integer free) {
        this.next = next;
        this.key = key;
        this.prev = prev;
        this.L = L;
        this.free = free;
    }

    /**
     * Creates a doubly linked list using the multiple-array representation
     * by copying an existing doubly linked list using the multiple-array representation.
     *
     * @param otherList the list to be copied
     */
    public MultipleArrayList(MultipleArrayList<E> otherList) {
        next = Array.copyOf(otherList.next);
        key = Array.copyOf(otherList.key);
        prev = Array.copyOf(otherList.prev);
        L = otherList.L;
        free = otherList.free;
    }

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

    /**
     * Returns the number of elements in the free list.
     *
     * @return the number of elements in the free list
     */
    public int getFreeListLength() {
        int length = 0;
        Integer x = free;
        while (x != null) {
            length++;
            x = next.at(x);
        }
        return length;
    }

    /**
     * Transforms the list to an array.
     *
     * @return the array containing all the elements in the list
     */
    public Array<E> toArray() {
        Array<E> array = Array.withLength(getLength());
        Integer x = L;
        int i = 1;
        while (x != null) {
            array.set(i, key.at(x));
            i++;
            x = next.at(x);
        }
        return array;
    }

}
