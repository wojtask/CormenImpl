package pl.kwojtas.cormenimpl.util;

/**
 * Implements a queue.
 *
 * @param <T> the type of elements in the queue
 */
public class Queue<T> extends Array<T> {

    /**
     * The index of the head element.
     */
    public int head;

    /**
     * The index of the next element after the tail element.
     */
    public int tail;

    private Queue(Array<T> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    /**
     * Creates an empty queue of a given length of the underlying array.
     *
     * @param length the length of the underlying array
     * @param <T> the type of elements in the new queue
     * @return the empty queue with underlying array of length {@code length}
     */
    public static <T> Queue<T> withLength(int length) {
        return new Queue<>(Array.withLength(length));
    }

}
