package pl.kwojtas.cormenimpl.util;

/**
 * Defines an interface of a hash function.
 */
public abstract class HashFunction {

    /**
     * Computes a value of the hash function.
     *
     * @param key the argument of the hash function
     * @return the value of the hash function for {@code key}
     */
    public abstract int compute(int key);

}
