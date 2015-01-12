package pl.kwojtas.cormenimpl;

import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {

    private ArrayList<ArrayList<Double>> data;
    public int rows;
    public int columns;

    public Matrix(Double[]... rows) {
        this.data = new ArrayList<>();
        for (Double[] row : rows) {
            if (row.length != rows[0].length) {
                throw new RuntimeException("Different sizes of rows");
            }
            this.data.add(new ArrayList<>(Arrays.asList(row)));
        }
        this.rows = data.size();
        this.columns = rows.length > 0 ? rows[0].length : 0;
    }

    public ArrayList<Double> row(int row) {
        if (row < 1 || row > rows) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return data.get(row - 1);
    }

    public Double at(int row, int column) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        return data.get(row - 1).get(column - 1);
    }

    public void set(int row, int column, Double element) {
        if (row < 1 || row > rows || column < 1 || column > columns) {
            throw new RuntimeException("Matrix index out of bound");
        }
        data.get(row - 1).set(column - 1, element);
    }

}
