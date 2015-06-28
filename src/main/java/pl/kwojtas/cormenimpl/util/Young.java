package pl.kwojtas.cormenimpl.util;

/**
 * Implements a Young tableau.
 */
public class Young extends Matrix<Integer> {

    /**
     * Creates an empty Young tableau of given dimensions.
     *
     * @param rows    the number of rows of the Young tableau
     * @param columns the number of columns of the Young tableau
     */
    public Young(int rows, int columns) {
        super(matrixOfInfinities(rows, columns));
    }

    private static Array<Array<Integer>> matrixOfInfinities(int rows, int columns) {
        Array<Array<Integer>> infinities = Array.withLength(rows);
        for (int i = 1; i <= rows; i++) {
            infinities.set(i, Array.withLength(columns));
            for (int j = 1; j <= columns; j++) {
                infinities.at(i).set(j, Integer.MAX_VALUE);
            }
        }
        return infinities;
    }

    /**
     * Creates a Young tableau from given arrays representing rows.
     *
     * @param rows the initial rows of the Young tableau
     */
    @SafeVarargs
    public Young(Array<Integer>... rows) {
        super(rows);
    }

}
