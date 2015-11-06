package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

public class YoungTest {

    @Test
    public void shouldCreateEmptyYoungTableau() {
        int rows = 3;
        int columns = 2;

        Young young = new Young(rows, columns);

        for (int i = 1; i <= rows; i++) {
            assertArrayEquals(new Array<>(Integer.MAX_VALUE, Integer.MAX_VALUE), young.row(i));
        }
    }

    @Test
    public void shouldCreateYoungTableauFromGivenRows() {
        Array<Integer> row1 = new Array<>(4, 5, 6);
        Array<Integer> row2 = new Array<>(2, 4, Integer.MAX_VALUE);

        Young young = new Young(row1, row2);

        assertArrayEquals(row1, young.row(1));
        assertArrayEquals(row2, young.row(2));
    }

}
