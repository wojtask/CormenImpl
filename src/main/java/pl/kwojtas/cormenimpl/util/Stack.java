package pl.kwojtas.cormenimpl.util;

public class Stack<T> extends Array<T> {

    public int top;

    private Stack(Array<T> array) {
        super(array);
        this.top = 0;
    }

    public static <T> Stack<T> withLength(int length) {
        return new Stack<>(Array.withLength(length));
    }

}
