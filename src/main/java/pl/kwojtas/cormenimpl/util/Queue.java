package pl.kwojtas.cormenimpl.util;

public class Queue<T> extends Array<T> {

    public int head;
    public int tail;

    public Queue() {
        super();
        this.head = 1;
        this.tail = 1;
    }

}
