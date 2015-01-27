package pl.kwojtas.cormenimpl.util;

public class Queue<T> extends Array<T> {

    public int head;
    public int tail;

    private Queue(Array<T> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    public static <T> Queue<T> withLength(int length) {
        return new Queue<>(Array.withLength(length));
    }

}
