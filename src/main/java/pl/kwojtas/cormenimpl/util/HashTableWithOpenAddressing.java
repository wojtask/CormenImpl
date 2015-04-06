package pl.kwojtas.cormenimpl.util;

public class HashTableWithOpenAddressing extends ZeroBasedIndexedArray<Integer> {

    public HashProbingFunction h;

    private HashTableWithOpenAddressing(int length, HashProbingFunction h) {
        super(ZeroBasedIndexedArray.withLength(length));
        this.h = h;
    }

    public static HashTableWithOpenAddressing withLengthAndHashFunction(int length, HashProbingFunction h) {
        return new HashTableWithOpenAddressing(length, h);
    }

}
