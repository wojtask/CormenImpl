package pl.kwojtas.cormenimpl.util;

public class Deque<T> extends Array<T> {

    public int head;
    public int tail;

    private Deque(Array<T> array) {
        super(array);
        this.head = 1;
        this.tail = 1;
    }

    public static <T> Deque<T> withLength(int length) {
        return new Deque<>(Array.withLength(length));
    }

}
