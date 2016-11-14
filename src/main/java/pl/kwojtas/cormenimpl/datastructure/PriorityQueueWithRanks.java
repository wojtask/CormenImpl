package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a priority queue with ranked elements.
 * Maintains a global rank and sets it to new elements so that ranks of elements are unique in this priority queue.
 *
 * @param <E> the type of keys in the priority queue
 */
public class PriorityQueueWithRanks<E> extends Heap<PriorityQueueWithRanks.KeyWithRank<E>> {

    /**
     * Implements a ranked element.
     *
     * @param <F> the type of key
     */
    public static class KeyWithRank<F> {

        /**
         * The key of the element.
         */
        public F key;

        /**
         * The rank of the element.
         */
        public int rank;

        /**
         * Creates a ranked element with a given key and rank.
         *
         * @param key  the key of the new element
         * @param rank the rank of the new element
         */
        public KeyWithRank(F key, int rank) {
            this.key = key;
            this.rank = rank;
        }
    }

    /**
     * The current rank.
     */
    private int currentRank;

    /**
     * Creates an empty priority queue with ranks of a given initial length and sets the current rank to 1.
     *
     * @param initialLength an initial length of the priority queue
     */
    public PriorityQueueWithRanks(int initialLength) {
        super(Array.emptyArray(), initialLength);
        currentRank = 1;
    }

    /**
     * Returns the current rank, then increments it.
     *
     * @return the current rank
     */
    public int getCurrentRank() {
        return currentRank++;
    }

}
