package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a deque.
 *
 * @param <E> the type of elements in the deque
 */
public class Deque<E> extends Array<E> {

    /**
     * The index of the head.
     */
    public int head;

    /**
     * The index of the element after the tail.
     */
    public int tail;

    private Deque(Array<E> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    /**
     * Creates an empty deque with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @return the empty deque with an underlying array of length {@code length}
     */
    public static <E> Deque<E> withLength(int length) {
        return new Deque<>(Array.withLength(length));
    }

}
