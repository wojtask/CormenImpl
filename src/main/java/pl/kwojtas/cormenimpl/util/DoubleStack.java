package pl.kwojtas.cormenimpl.util;

public class DoubleStack<T> extends Array<T> {

    public int top1;
    public int top2;

    public DoubleStack(Array<T> array) {
        super(array);
        this.top1 = 0;
        this.top2 = array.length + 1;
    }

}
