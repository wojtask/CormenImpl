package pl.kwojtas.cormenimpl.datastructure;

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

    /**
     * Creates a list using the single-array representation from given array and pointer indices.
     *
     * @param A    the underlying array of the new list compatible to the single-array representation
     * @param L    the index of the head of the new list
     * @param free the index of the head of the free list in the new list
     */
    public SingleArrayList(Array<Integer> A, Integer L, Integer free) {
        super(A);
        this.L = L;
        this.free = free;
    }

}
