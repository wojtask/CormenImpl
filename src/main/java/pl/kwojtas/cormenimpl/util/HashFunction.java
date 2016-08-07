package pl.kwojtas.cormenimpl.util;

/**
 * Defines an interface of a hash function.
 */
@FunctionalInterface
public interface HashFunction {

    /**
     * Computes a value of the hash function.
     *
     * @param key the argument of the hash function
     * @return the value of the hash function for {@code key}
     */
    int compute(int key);

}
