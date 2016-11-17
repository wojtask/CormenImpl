package pl.kwojtas.cormenimpl.datastructure;

/**
 * Implements a matrix.
 *
 * @param <E> the type of elements in the matrix
 */
public class Matrix<E> {

    private Array<Array<E>> data;

    /**
     * The number of rows.
     */
    public int nRows;

    /**
     * The number of columns.
     */
    public int nColumns;

    protected Matrix(int nRows, int nColumns) {
        data = Array.withLength(nRows);
        for (int i = 1; i <= nRows; i++) {
            data.set(i, Array.withLength(nColumns));
        }
        this.nRows = nRows;
        this.nColumns = nColumns;
    }

    /**
     * Creates a matrix from given rows of elements.
     *
     * @param rows the initial rows of the matrix
     * @return the matrix containing elements from {@code rows}
     */
    @SafeVarargs
    public static <E> Matrix<E> ofRows(Array<E>... rows) {
        int nRows = rows.length;
        int nColumns = nRows > 0 ? rows[0].length : 0;
        Matrix<E> matrix = new Matrix<>(nRows, nColumns);
        for (int i = 1; i <= nRows; i++) {
            Array<E> row = rows[i - 1];
            if (row.length != nColumns) {
                throw new IllegalStateException("Different sizes of rows");
            }
            for (int j = 1; j <= nColumns; j++) {
                matrix.data.at(i).set(j, row.at(j));
            }
        }
        return matrix;
    }

    /**
     * Returns a row of the matrix.
     *
     * @param nRow the row number to return
     * @return an array representing row {@code nRow}
     * @throws IllegalStateException if {@code nRow < 1} or {@code nRow > nRows}
     */
    public Array<E> row(int nRow) {
        if (nRow < 1 || nRow > nRows) {
            throw new IllegalStateException("Row index out of bound");
        }
        return data.at(nRow);
    }

    /**
     * Returns an element at a given position.
     *
     * @param nRow    the row of the element to return
     * @param nColumn the column of the element to return
     * @return the element in row {@code nRow} and column {@code nColumn}
     * @throws IllegalStateException if {@code nRow < 1} or {@code nRow > nRows} or {@code nColumn < 1} or {@code nColumn > nColumns}
     */
    public E at(int nRow, int nColumn) {
        if (nRow < 1 || nRow > nRows || nColumn < 1 || nColumn > nColumns) {
            throw new IllegalStateException("Row index or column index out of bound");
        }
        return data.at(nRow).at(nColumn);
    }

    /**
     * Sets an element at a given position.
     *
     * @param nRow    the row of the element to set
     * @param nColumn the column of the element to set
     * @param element the new element
     * @throws IllegalStateException if {@code nRow < 1} or {@code nRow > nRows} or {@code nColumn < 1} or {@code nColumn > nColumns}
     */
    public void set(int nRow, int nColumn, E element) {
        if (nRow < 1 || nRow > nRows || nColumn < 1 || nColumn > nColumns) {
            throw new IllegalStateException("Row index or column index out of bound");
        }
        data.at(nRow).set(nColumn, element);
    }

    /**
     * Exchanges two elements in the matrix.
     *
     * @param nRow1    the row of the first element
     * @param nColumn1 the column of the first element
     * @param nRow2    the row of the second element
     * @param nColumn2 the column of the second element
     */
    public void exch(int nRow1, int nColumn1, int nRow2, int nColumn2) {
        E swap = at(nRow1, nColumn1);
        set(nRow1, nColumn1, at(nRow2, nColumn2));
        set(nRow2, nColumn2, swap);
    }

}
