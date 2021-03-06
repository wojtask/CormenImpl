package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a queue.
 *
 * @param <E> the type of elements in the queue
 */
public class Queue<E> extends Array<E> {

    /**
     * The index of the head.
     */
    public int head;

    /**
     * The index of the element after the tail.
     */
    public int tail;

    private Queue(Array<E> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    /**
     * Creates an empty queue with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @return the empty queue with an underlying array of length {@code length}
     */
    public static <E> Queue<E> ofLength(int length) {
        return new Queue<>(Array.ofLength(length));
    }

}
