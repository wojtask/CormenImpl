package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter2Test {

    @DataProvider
    public static Object[][] provideDataForSorting() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(3,2,1), new Array<>(1,2,3)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(-2.75,-0.53,2.23,2.55,3.14)},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), new Array<>("aaa","bbb","ccc","ddd","eee")}
        };
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T> void shouldSortArrayUsingInsertionSort(Array<T> array, Array<T> expected) {
        // given

        // when
        Chapter2.insertionSort(array);

        // then
        assertArrayEquals(expected, array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulSearch() {
        return new Object[][]{
                {new Array<>(34), 34, 1},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 6, 5},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 8, 12},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3, 2},
                {new Array<>("aaa","bbb","aaa","ccc"), "ccc", 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulSearch")
    public <T> void shouldFindKeyUsingLinearSearch(Array<T> array, T key, Integer expectedIndex) {
        // given

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertEquals(expectedIndex, actualIndex);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulSearch() {
        return new Object[][]{
                {new Array<>(34), 35},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new Array<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulSearch")
    public <T> void shouldNotFindKeyUsingLinearSeach(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter2.linearSearch(array, key);

        // then
        assertNull(actualIndex);
    }

    @DataProvider
    public static Object[][] provideDataForBinaryAdd() {
        return new Object[][]{
                {new Array<>(0), new Array<>(0), new Array<>(0,0)},
                {new Array<>(1), new Array<>(0), new Array<>(1,0)},
                {new Array<>(0), new Array<>(1), new Array<>(1,0)},
                {new Array<>(1), new Array<>(1), new Array<>(0,1)},
                {new Array<>(0,0,1,1,0,1,0,0,0,0,1), new Array<>(0,1,1,1,1,0,0,1,0,1,1), new Array<>(0,1,0,1,0,0,1,1,0,1,0,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForBinaryAdd")
    public void shouldAddTwoNumbersInBinary(Array<Integer> A, Array<Integer> B, Array<Integer> expectedSum) {
        // given

        // when
        Array<Integer> actualSum = Chapter2.binaryAdd(A, B);

        // then
        assertArrayEquals(expectedSum, actualSum);
    }
}
