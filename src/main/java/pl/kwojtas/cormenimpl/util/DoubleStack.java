package pl.kwojtas.cormenimpl.util;

/**
 * Implements two stacks in an array.
 *
 * @param <T> the type of elements in the stacks
 */
public class DoubleStack<T> extends Array<T> {

    /**
     * The index of the element at the top of the first stack.
     */
    public int top1;

    /**
     * The index of the element at the top of the second stack.
     */
    public int top2;

    private DoubleStack(Array<T> array) {
        super(array);
        this.top1 = 0;
        this.top2 = array.length + 1;
    }

    /**
     * Creates two empty stacks in an array of a given length.
     *
     * @param length the length of the underlying array
     * @param <T>    the type of elements in the stacks
     * @return two empty stacks in an array of length {@code length}
     */
    public static <T> DoubleStack<T> withLength(int length) {
        return new DoubleStack<>(Array.withLength(length));
    }

}
