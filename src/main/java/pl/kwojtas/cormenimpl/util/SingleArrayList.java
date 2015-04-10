package pl.kwojtas.cormenimpl.util;

/**
 * Implements a list using the single-array representation.
 */
public class SingleArrayList extends Array<Integer> {

    /**
     * The index of the head of the list.
     */
    public Integer L;

    /**
     * The index of the head of the free list.
     */
    public Integer free;

}
