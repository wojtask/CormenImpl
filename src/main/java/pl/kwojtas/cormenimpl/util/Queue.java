package pl.kwojtas.cormenimpl.util;

/**
 * Implements a queue.
 *
 * @param <T> the type of elements in the queue
 */
public class Queue<T> extends Array<T> {

    /**
     * The index of the head.
     */
    public int head;

    /**
     * The index of the element after the tail.
     */
    public int tail;

    private Queue(Array<T> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    /**
     * Creates an empty queue with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @param <T>    the type of elements in the new queue
     * @return the empty queue with an underlying array of length {@code length}
     */
    public static <T> Queue<T> withLength(int length) {
        return new Queue<>(Array.withLength(length));
    }

}
