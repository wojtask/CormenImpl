package pl.kwojtas.cormenimpl.util;

public class DoubleStack<T> extends Array<T> {

    public int top1;
    public int top2;

    private DoubleStack(Array<T> array) {
        super(array);
        this.top1 = 0;
        this.top2 = array.length + 1;
    }

    public static <T> DoubleStack<T> withLength(int length) {
        return new DoubleStack<>(Array.withLength(length));
    }

}
