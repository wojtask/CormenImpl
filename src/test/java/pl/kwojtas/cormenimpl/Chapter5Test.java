package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;

@RunWith(DataProviderRunner.class)
public class Chapter5Test {

    @DataProvider
    public static Object[][] provideDataForPickingRandomNumberFromInterval() {
        return new Object[][]{
                {0,0},
                {0,1},
                {0,50},
                {4999,5000},
                {-30,-15},
                {-4,4},
                {-2000000000,2000000000}
        };
    }

    @Test
    @UseDataProvider("provideDataForPickingRandomNumberFromInterval")
    public void shouldPickNumberFromInterval(int a, int b) {
        // given

        // when
        int pickedNumber = Chapter5.random(a, b);

        // then
        assertTrue(a <= pickedNumber && pickedNumber <= b);
    }

    @DataProvider
    public static Object[][] provideDataForPermuting() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(1,2,3)},
                {new Array<>(9,8,8,7,7,6,6,6,5,3,2,1)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23)},
                {new Array<>("aaa","eee","ccc","ddd","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForPermuting")
    public <T extends Comparable> void shouldPermuteBySorting(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter5.permuteBySorting(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    @UseDataProvider("provideDataForPermuting")
    public <T extends Comparable> void shouldRandomizeInPlace(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter5.randomizeInPlace(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    @UseDataProvider("provideDataForPermuting")
    public <T extends Comparable> void shouldRandomizeInPlaceUsingAlternativeMethod(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter5.randomizeInPlace_(array);

        // then
        assertShuffled(original, array);
    }

    @Test
    @UseDataProvider("provideDataForPermuting")
    public <T extends Comparable> void shouldPermuteUniformlyBySorting(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter5.permuteUniformlyBySorting(array);

        // then
        assertShuffled(original, array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulRandomSearch() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 6},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 8},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), -2.3},
                {new Array<>("aaa","bbb","aaa","ccc"), "ccc"}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulRandomSearch")
    public <T> void shouldFindObject(Array<T> array, T key) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Integer actualIndex = Chapter5.randomSearch(array, key);

        // then
        assertNotNull(actualIndex);
        assertTrue(1 <= actualIndex && actualIndex <= original.length);
        assertEquals(key, original.at(actualIndex));
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulRandomSearch() {
        return new Object[][]{
                {new Array<>(34), 35},
                {new Array<>(5,7,9,2,6,1,6,6,3,1,7,8), 4},
                {new Array<>(5.0,-2.3,-1.3,-1.9,-2.3), 2.3},
                {new Array<>("aaa","bbb","aaa","ccc"), "xyz"}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulRandomSearch")
    public <T> void shouldNotFindObject(Array<T> array, T key) {
        // given

        // when
        Integer actualIndex = Chapter5.randomSearch(array, key);

        // then
        assertNull(actualIndex);
    }

}
