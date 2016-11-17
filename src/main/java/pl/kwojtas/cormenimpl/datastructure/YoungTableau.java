package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a Young tableau.
 */
public class YoungTableau extends Matrix<Integer> {

    private YoungTableau(int nRows, int nColumns) {
        super(nRows, nColumns);
        for (int i = 1; i <= nRows; i++) {
            for (int j = 1; j <= nColumns; j++) {
                this.set(i, j, Integer.MAX_VALUE);
            }
        }
    }

    /**
     * Creates an empty Young tableau of given dimensions.
     *
     * @param nRows    the number of rows of the Young tableau
     * @param nColumns the number of columns of the Young tableau
     */
    public static YoungTableau emptyOfDimensions(int nRows, int nColumns) {
        return ofDimensionsAndRows(nRows, nColumns);
    }

    /**
     * Creates a Young tableau of given dimensions and rows of elements.
     *
     * @param nRows    the number of rows of the Young tableau
     * @param nColumns the number of columns of the Young tableau
     * @param rows     the initial row prefixes of the Young tableau
     * @return the Young tableau {@code nRows} by {@code nColumns} containing elements from {@code rows}.
     * If there are less rows than {@code nRows} or any row is shorter than {@code nColumns} then the Young table
     * will have infinities on non specified positions.
     */
    @SafeVarargs
    public static YoungTableau ofDimensionsAndRows(int nRows, int nColumns, Array<Integer>... rows) {
        if (rows.length > nRows) {
            throw new IllegalStateException("The first dimension is smaller than number of rows");
        }
        YoungTableau youngTableau = new YoungTableau(nRows, nColumns);
        for (int i = 1; i <= rows.length; i++) {
            Array<Integer> row = rows[i - 1];
            if (row.length > nColumns) {
                throw new IllegalStateException("The second dimension is smaller than one of rows");
            }
            for (int j = 1; j <= row.length; j++) {
                youngTableau.set(i, j, row.at(j));
            }
        }
        return youngTableau;
    }

}
