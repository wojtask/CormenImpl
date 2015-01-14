package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public static Object[][] provideDataForNonincreasingHeapsort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(1,2,3), new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(9,8,8,7,7,6,6,6,5,3,2,1)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(9,8,8,7,7,6,6,6,5,3,2,1)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(3.14,2.55,2.23,-0.53,-2.75)},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), new Array<>("eee","ddd","ccc","bbb","aaa")}
        };
    }

    @Test
    @UseDataProvider("provideDataForNonincreasingHeapsort")
    public <T extends Comparable> void shouldSortArrayInNonincreasingOrderUsingHeapsort(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter6.nonincreasingHeapsort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @Test
    @UseDataProvider("provideDataForHeapsort")
    public <T extends Comparable> void shouldSortArrayUsingHeapsortWithIterativeMaxHeapify(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter6.heapsortUsingIterativeMaxHeapify(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForHeapsort_() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(3,2,1), new Array<>(1,2,3)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapsort_")
    public void shouldSortArrayUsingHeapsort_(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter6.heapsort_(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

}
