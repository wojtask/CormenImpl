package pl.kwojtas.cormenimpl.util;

public class Heap<T> extends Array<T> {

    public int heapSize;

    public Heap() {
        super();
        this.heapSize = 0;
    }

    public Heap(Array<T> otherArray) {
        super(otherArray);
        this.heapSize = otherArray.length;
    }

}
