package pl.kwojtas.cormenimpl.util;

/**
 * Implements a matrix.
 *
 * @param <T> the type of elements in the matrix
 */
public class Matrix<T> {

    private T[][] data;

    /**
     * The number of rows.
     */
    public int rows;

    /**
     * The number of columns.
     */
    public int columns;

    /**
     * Creates a matrix from given arrays representing rows.
     *
     * @param rows the initial rows of the matrix
     */
    @SafeVarargs
    @SuppressWarnings("unchecked")
    public Matrix(Array<T>... rows) {
        this.data = (T[][]) new Object[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].length != rows[0].length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data[i] = (T[]) new Object[rows[i].length];
            for (int j = 0; j < rows[i].length; j++) {
                this.data[i][j] = rows[i].at(j + 1);
            }
        }
        this.rows = rows.length;
        this.columns = rows.length > 0 ? rows[0].length : 0;
    }

    /**
     * Creates a matrix from given arrays representing rows.
     *
     * @param rows the initial rows of the matrix
     */
    @SuppressWarnings("unchecked")
    public Matrix(Array<Array<T>> rows) {
        this.data = (T[][]) new Object[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            if (rows.at(i + 1).length != rows.at(1).length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data[i] = (T[]) new Object[rows.at(i + 1).length];
            for (int j = 0; j < rows.at(i + 1).length; j++) {
                this.data[i][j] = rows.at(i + 1).at(j + 1);
            }
        }
        this.rows = rows.length;
        this.columns = rows.length > 0 ? rows.at(1).length : 0;
    }

    /**
     * Returns a row of the matrix.
     *
     * @param row the row number to return
     * @return an array representing row {@code row}
     * @throws RuntimeException if {@code row < 1} or {@code row > rows}
     */
    public Array<T> row(int row) {
        if (row < 1 || row > rows) {
            throw new RuntimeException("Row index out of bound");
        }
        return new Array<>(data[row - 1]);
    }

    /**
     * Returns an element at a given position.
     *
     * @param row    the row of the element to return
     * @param column the column of the element to return
     * @return the element in row {@code row} and column {@code column}
     * @throws RuntimeException if {@code row < 1} or {@code row > rows} or {@code column < 1} or {@code column > columns}
     */
    public T at(int row, int column) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Row index or column index out of bound");
        }
        return data[row - 1][column - 1];
    }

    /**
     * Sets an element at a given position.
     *
     * @param row     the row of the element to set
     * @param column  the column of the element to set
     * @param element the new element
     * @throws RuntimeException if {@code row < 1} or {@code row > rows} or {@code column < 1} or {@code column > columns}
     */
    public void set(int row, int column, T element) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Row index or column index out of bound");
        }
        data[row - 1][column - 1] = element;
    }

    /**
     * Exchanges two elements in the matrix.
     *
     * @param row1    the row of the first element
     * @param column1 the column of the first element
     * @param row2    the row of the second element
     * @param column2 the column of the second element
     */
    public void exch(int row1, int column1, int row2, int column2) {
        T swap = at(row1, column1);
        set(row1, column1, at(row2, column2));
        set(row2, column2, swap);
    }

}
