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
     * Creates an empty doubly linked list using the multiple-array representation.
     */
    public MultipleArrayList() {
    }

    /**
     * Creates a doubly linked list using the multiple-array representation
     * by copying an existing doubly linked list using the multiple-array representation.
     *
     * @param otherList the list to be copied
     */
    public MultipleArrayList(MultipleArrayList<T> otherList) {
        next = new Array<>(otherList.next);
        key = new Array<>(otherList.key);
        prev = new Array<>(otherList.prev);
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
    public Array<T> toArray() {
        Array<T> array = Array.withLength(getLength());
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
