package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter8Test {

    @DataProvider
    public static Object[][] provideDataForCountingSort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34), 60},
                {new Array<>(3,2,1,0), new Array<>(0,1,2,3), 3},
                {new Array<>(2,5,3,0,2,3,0,3), new Array<>(0,0,2,2,3,3,3,5), 5},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), new Array<>(0,0,1,1,2,2,3,3,4,6,6), 6}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingCountingSort(Array<Integer> array, Array<Integer> expectedSorted, int boundary) {
        // given
        Array<Integer> actualSorted = new Array<>();

        // when
        Chapter8.countingSort(array, actualSorted, boundary);

        // then
        assertArrayEquals(expectedSorted, actualSorted);
    }

    @DataProvider
    public static Object[][] provideDataForCountingNumbersInRange() {
        return new Object[][]{
                {new Array<>(34), 34, 34, 34, 1},
                {new Array<>(34), 66, 34, 34, 1},
                {new Array<>(34), 34, 2, 35, 1},
                {new Array<>(34), 34, 35, 35, 0},
                {new Array<>(3,2,1,0), 3, 0, 1, 2},
                {new Array<>(3,2,1,0), 3, 0, 3, 4},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, -1, 0},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, 3, 8},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 5, 8, 2},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 10, 20, 0}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingNumbersInRange")
    public void shouldCountNumbersInRange(Array<Integer> array, int boundary, int a, int b, int expectedInRange) {
        // given

        // when
        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        // then
        assertEquals(expectedInRange, actualInRange);
    }

}
