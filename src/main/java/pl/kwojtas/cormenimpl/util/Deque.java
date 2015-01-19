package pl.kwojtas.cormenimpl.util;

public class Deque<T> extends Array<T> {

    public int head;
    public int tail;

    public Deque() {
        super();
        this.head = 1;
        this.tail = 1;
    }

}
