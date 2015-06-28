package pl.kwojtas.cormenimpl.util;

/**
 * Implements a stack.
 *
 * @param <T> the type of elements on the stack
 */
public class Stack<T> extends Array<T> {

    /**
     * The index of the element at the top of the stack.
     */
    public int top;

    private Stack(Array<T> array) {
        super(array);
        this.top = 0;
    }

    /**
     * Creates an empty stack with an underlying array of a given length.
     *
     * @param length the length of the underlying array
     * @param <T>    the type of elements in the new stack
     * @return the empty stack with an underlying array of length {@code length}
     */
    public static <T> Stack<T> withLength(int length) {
        return new Stack<>(Array.withLength(length));
    }

}
