package pl.kwojtas.cormenimpl.util;

/**
 * Implements a deque.
 *
 * @param <T> the type of elements in the deque
 */
public class Deque<T> extends Array<T> {

    /**
     * The index of the head element.
     */
    public int head;

    /**
     * The index of the next element after the tail element.
     */
    public int tail;

    private Deque(Array<T> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    /**
     * Creates an empty deque of a given length of the underlying array.
     *
     * @param length the length of the underlying array
     * @param <T> the type of elements in the new deque
     * @return the empty deque with underlying array of length {@code length}
     */
    public static <T> Deque<T> withLength(int length) {
        return new Deque<>(Array.withLength(length));
    }

}
