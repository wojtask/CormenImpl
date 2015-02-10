package pl.kwojtas.cormenimpl.util;

public class ChainedHashTable<T> extends ZeroBasedIndexedArray<List<Element<T>>> {

    public HashFunction h;

    private ChainedHashTable(int length, HashFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        for (int i = 0; i <= length - 1; i++) {
            set(i, new List<>());
        }
        this.h = h;
    }

    public static <T> ChainedHashTable<T> withLengthAndHashFunction(int length, HashFunction h) {
        return new ChainedHashTable<>(length, h);
    }

}
