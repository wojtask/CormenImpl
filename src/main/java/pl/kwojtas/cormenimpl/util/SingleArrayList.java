package pl.kwojtas.cormenimpl.util;

public class SingleArrayList {

    public Array<Integer> A;
    public Integer L;
    public Integer free;

    public int getLength() {
        int length = 0;
        Integer x = L;
        while (x != null) {
            length++;
            x = A.at(x + 1);
        }
        return length;
    }

}
