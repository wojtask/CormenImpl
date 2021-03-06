package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class YoungTableauTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateEmptyYoungTableau() {
        int nRows = 3;
        int nColumns = 2;

        YoungTableau youngTableau = YoungTableau.emptyOfDimensions(nRows, nColumns);

        for (int i = 1; i <= nRows; i++) {
            assertArrayEquals(Array.of(Integer.MAX_VALUE, Integer.MAX_VALUE), youngTableau.row(i));
        }
    }

    @Test
    public void shouldCreateYoungTableauFromGivenRows() {
        int nRows = 2;
        int nColumns = 3;
        Array<Integer> row1 = Array.of(4, 5, 6);
        Array<Integer> row2 = Array.of(5, 9);

        YoungTableau youngTableau = YoungTableau.ofDimensionsAndRows(nRows, nColumns, row1, row2);

        Array<Integer> actualRow2 = Array.of(5, 9, Integer.MAX_VALUE);
        assertArrayEquals(row1, youngTableau.row(1));
        assertArrayEquals(actualRow2, youngTableau.row(2));
    }

    @Test
    public void shouldThrowExceptionWhenCreatingYoungTableauWithTooSmallFirstDimension() {
        int nRows = 1;
        int nColumns = 3;
        Array<Integer> row1 = Array.of(4, 5, 6);
        Array<Integer> row2 = Array.of(5, 9);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("The first dimension is smaller than number of rows");
        YoungTableau.ofDimensionsAndRows(nRows, nColumns, row1, row2);
    }

    @Test
    public void shouldThrowExceptionWhenCreatingYoungTableauWithTooSmallSecondDimension() {
        int nRows = 2;
        int nColumns = 2;
        Array<Integer> row1 = Array.of(4, 5, 6);
        Array<Integer> row2 = Array.of(5, 9);

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("The second dimension is smaller than one of rows");
        YoungTableau.ofDimensionsAndRows(nRows, nColumns, row1, row2);
    }

}
