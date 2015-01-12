package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter4Test {

    @DataProvider
    public static Object[][] provideDataForFindingMissingInteger() {
        return new Object[][]{
                {new Array<>(), 0},
                {new Array<>(0), 1},
                {new Array<>(1), 0},
                {new Array<>(0,1), 2},
                {new Array<>(0,2), 1},
                {new Array<>(1,2), 0},
                {new Array<>(1,6,7,3,0,8,5,2), 4},
                {new Array<>(0,1,2,3,4,5,6,8,9,10,11,12,13,14,15), 7}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingMissingInteger")
    public void shouldFindMissingInteger(Array<Integer> array, Integer expectedMissingInteger) {
        // given

        // when
        Integer actualMissingInteger = Chapter4.findMissingInteger(array);

        // then
        assertEquals(expectedMissingInteger, actualMissingInteger);
    }

    @DataProvider
    public static Object[][] provideDataForGettingLeftmostMinimaIndexesOfMongeArray() {
        return new Object[][]{
                {new Matrix(new Double[]{0.0}), new Array<>(1)},
                {new Matrix(
                        new Double[]{0.7,2.5},
                        new Double[]{1.5,3.2}
                ), new Array<>(1,1)},
                {new Matrix(
                        new Double[]{10.0,17.0,13.0,28.0,23.0},
                        new Double[]{17.0,22.0,16.0,29.0,23.0},
                        new Double[]{24.0,28.0,22.0,34.0,24.0},
                        new Double[]{11.0,13.0, 6.0,17.0, 7.0},
                        new Double[]{45.0,44.0,32.0,37.0,23.0},
                        new Double[]{36.0,33.0,19.0,21.0, 6.0},
                        new Double[]{75.0,66.0,51.0,53.0,34.0}
                ), new Array<>(1,3,3,3,5,5,5)},
                {new Matrix(
                        new Double[]{37.0,23.0,24.0,32.0},
                        new Double[]{21.0, 6.0, 7.0,10.0},
                        new Double[]{53.0,34.0,30.0,31.0},
                        new Double[]{32.0,13.0, 9.0, 6.0},
                        new Double[]{43.0,21.0,15.0, 8.0}
                ), new Array<>(2,2,3,4,4)}
        };
    }

    @Test
    @UseDataProvider("provideDataForGettingLeftmostMinimaIndexesOfMongeArray")
    public void shouldGetLeftmostMinimaIndexesOfMongeArray(Matrix matrix, Array<Integer> expectedLeftmostMinimaIndexes) {
        // given

        // when
        Array<Integer> actualLeftmostMinimaIndexes = Chapter4.mongeLeftmostMinimaIndexes(matrix);

        // then
        assertArrayEquals(expectedLeftmostMinimaIndexes, actualLeftmostMinimaIndexes);
    }

}
