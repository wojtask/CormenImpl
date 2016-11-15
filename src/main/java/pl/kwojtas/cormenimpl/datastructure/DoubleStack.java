package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements two stacks in an array.
 *
 * @param <E> the type of elements in the stacks
 */
public class DoubleStack<E> extends Array<E> {

    /**
     * The index of the element at the top of the first stack.
     */
    public int leftTop;

    /**
     * The index of the element at the top of the second stack.
     */
    public int rightTop;

    private DoubleStack(Array<E> array) {
        super(array);
        this.leftTop = 0;
        this.rightTop = array.length + 1;
    }

    /**
     * Creates two empty stacks in an array of a given length.
     *
     * @param length the length of the underlying array
     * @return two empty stacks in an array of length {@code length}
     */
    public static <E> DoubleStack<E> withLength(int length) {
        return new DoubleStack<>(Array.withLength(length));
    }

}
