package pl.kwojtas.cormenimpl;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix<T extends Number> {

    private ArrayList<ArrayList<T>> data;
    public int rows;
    public int columns;

    @SafeVarargs
    public Matrix(T[]... rows) {
        this.data = new ArrayList<>();
        for (T[] row : rows) {
            if (row.length != rows[0].length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data.add(new ArrayList<>(Arrays.asList(row)));
        }
        this.rows = data.size();
        this.columns = rows.length > 0 ? rows[0].length : 0;
    }

    public ArrayList<T> row(int row) {
        if (row < 1 || row > rows) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return data.get(row - 1);
    }

    public T at(int row, int column) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return data.get(row - 1).get(column - 1);
    }

    public void set(int row, int column, T element) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        data.get(row - 1).set(column - 1, element);
    }

    public void exch(int row1, int column1, int row2, int column2) {
        T swap = at(row1, column1);
        set(row1, column1, at(row2, column2));
        set(row2, column2, swap);
    }
}
