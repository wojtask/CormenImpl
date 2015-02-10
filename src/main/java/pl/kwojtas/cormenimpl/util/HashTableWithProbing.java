package pl.kwojtas.cormenimpl.util;

public class HashTableWithProbing extends ZeroBasedIndexedArray<Integer> {

    public HashProbingFunction h;

    private HashTableWithProbing(int length, HashProbingFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        this.h = h;
    }

    public static HashTableWithProbing withLengthAndHashFunction(int length, HashProbingFunction h) {
        return new HashTableWithProbing(length, h);
    }

}
