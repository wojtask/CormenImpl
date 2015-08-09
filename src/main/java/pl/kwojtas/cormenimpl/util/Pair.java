package pl.kwojtas.cormenimpl.util;

/**
 * Implements an ordered pair.
 *
 * @param <E> the type of the first element of the pair
 * @param <F> the type of the second element of the pair
 */
public class Pair<E, F> {

    /**
     * The first element of the pair.
     */
    public E first;

    /**
     * The second element of the pair.
     */
    public F second;

    /**
     * Creates an ordered pair from given elements.
     *
     * @param first  the first element
     * @param second the second element
     */
    public Pair(E first, F second) {
        this.first = first;
        this.second = second;
    }

}
