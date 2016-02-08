package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a stack.
 *
 * @param <E> the type of elements on the stack
 */
public class Stack<E> extends Array<E> {

    /**
     * The index of the element at the top of the stack.
     */
    public int top;

    private Stack(Array<E> array) {
        super(array);
        this.top = 0;
    }

    /**
     * Creates an empty stack with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @param <E>    the type of elements in the new stack
     * @return the empty stack with an underlying array of length {@code length}
     */
    public static <E> Stack<E> withLength(int length) {
        return new Stack<>(Array.withLength(length));
    }

}
