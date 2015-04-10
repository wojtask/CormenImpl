package pl.kwojtas.cormenimpl.util;

/**
 * Implements an ordered pair.
 *
 * @param <T1> the type of the first element of the pair
 * @param <T2> the type of the second element of the pair
 */
public class Pair<T1, T2> {

    /**
     * The first element of the pair.
     */
    public T1 first;

    /**
     * The second element of the pair.
     */
    public T2 second;

    /**
     * Creates an ordered pair from the two elements.
     *
     * @param first the first element
     * @param second the second element
     */
    public Pair(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

}
