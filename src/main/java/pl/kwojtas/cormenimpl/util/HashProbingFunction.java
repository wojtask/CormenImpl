package pl.kwojtas.cormenimpl.util;

/**
 * Defines an interface of a hash probing function.
 */
public abstract class HashProbingFunction {

    /**
     * Computes a value of the hash probing function.
     *
     * @param key the argument of the hash function
     * @param i the probe number (starting from 0)
     * @return the value of the hash function for {@code key} in the {@code i}-th probe
     */
    public abstract int compute(int key, int i);

}
