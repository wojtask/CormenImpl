package pl.kwojtas.cormenimpl.util;

public class MultipleArrayList<T> {

    public Array<Integer> next;
    public Array<T> key;
    public Array<Integer> prev;
    public Integer L;
    public Integer free;

    public int getLength() {
        int length = 0;
        Integer x = L;
        while (x != null) {
            length++;
            x = next.at(x);
        }
        return length;
    }

}
