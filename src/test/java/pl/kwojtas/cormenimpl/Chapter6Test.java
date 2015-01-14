package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Heap;
import pl.kwojtas.cormenimpl.util.Young;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter6Test {

    @DataProvider
    public static Object[][] provideDataForHeapsort() {
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
    @UseDataProvider("provideDataForHeapsort")
    public <T extends Comparable> void shouldSortArrayUsingHeapsort(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter6.heapsort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForYoungSort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(4,3,2,1), new Array<>(1,2,3,4)},
                {new Array<>(1,3,4,2), new Array<>(1,2,3,4)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8,6,3,7,8), new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9)},
                {new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9), new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForYoungSort")
    public void shouldSortArrayUsingYoungSort(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter6.youngSort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulYoungSearch() {
        return new Object[][]{
                {new Array[] {
                        new Array<>(34)
                }, 34},
                {new Array[] {
                        new Array<>(2,5),
                        new Array<>(4,Integer.MAX_VALUE)
                }, 4},
                {new Array[] {
                        new Array<>(2,Integer.MAX_VALUE),
                        new Array<>(Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 2},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 5},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 16}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulYoungSearch")
    public void shouldFindKeyUsingYoungSearch(Array<Integer>[] rows, Integer key) {
        // given
        Young Y = new Young(rows);

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertTrue(keyFound);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulYoungSearch() {
        return new Object[][]{
                {new Array[] {
                        new Array<>(34)
                }, 35},
                {new Array[] {
                        new Array<>(Integer.MAX_VALUE)
                }, 35},
                {new Array[] {
                        new Array<>(2,5),
                        new Array<>(4,Integer.MAX_VALUE)
                }, 3},
                {new Array[] {
                        new Array<>(2,Integer.MAX_VALUE),
                        new Array<>(Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 1},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 10},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 17}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulYoungSearch")
    public void shouldNotFindKeyUsingYoungSearch(Array<Integer>[] rows, Integer key) {
        // given
        Young Y = new Young(rows);

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertFalse(keyFound);
    }

}
