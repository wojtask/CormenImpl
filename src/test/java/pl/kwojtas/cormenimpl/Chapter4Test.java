package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Matrix;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(DataProviderRunner.class)
public class Chapter4Test {

    @DataProvider
    public static Object[][] provideDataForFindingMissingInteger() {
        return new Object[][]{
                {new Array<>()},
                {new Array<>(0)},
                {new Array<>(1)},
                {new Array<>(0,1)},
                {new Array<>(0,2)},
                {new Array<>(1,2)},
                {new Array<>(1,6,7,3,0,8,5,2)},
                {new Array<>(0,1,2,3,4,5,6,8,9,10,11,12,13,14,15)}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingMissingInteger")
    public void shouldFindMissingInteger(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Integer actualMissingInteger = Chapter4.findMissingInteger(array);

        // then
        assertNotNull(actualMissingInteger);
        original.getData().sort(Comparator.<Integer>naturalOrder());
        int i = 1;
        while (i <= original.length && original.at(i) == i - 1) {
            i++;
        }
        assertEquals(new Integer(i - 1), actualMissingInteger);
    }

    @DataProvider
    public static Object[][] provideDataForGettingLeftmostMinimaIndexesOfMongeArray() {
        return new Object[][]{
                {new Matrix<>(
                        new Double[]{0.0}
                )},
                {new Matrix<>(
                        new Double[]{0.7,2.5},
                        new Double[]{1.5,3.2}
                )},
                {new Matrix<>(
                        new Double[]{10.0,17.0,13.0,28.0,23.0},
                        new Double[]{17.0,22.0,16.0,29.0,23.0},
                        new Double[]{24.0,28.0,22.0,34.0,24.0},
                        new Double[]{11.0,13.0, 6.0,17.0, 7.0},
                        new Double[]{45.0,44.0,32.0,37.0,23.0},
                        new Double[]{36.0,33.0,19.0,21.0, 6.0},
                        new Double[]{75.0,66.0,51.0,53.0,34.0}
                )},
                {new Matrix<>(
                        new Double[]{37.0,23.0,24.0,32.0},
                        new Double[]{21.0, 6.0, 7.0,10.0},
                        new Double[]{53.0,34.0,30.0,31.0},
                        new Double[]{32.0,13.0, 9.0, 6.0},
                        new Double[]{43.0,21.0,15.0, 8.0}
                )}
        };
    }

    @Test
    @UseDataProvider("provideDataForGettingLeftmostMinimaIndexesOfMongeArray")
    public void shouldGetLeftmostMinimaIndexesOfMongeArray(Matrix<Double> matrix) {
        // given
        Matrix<Double> original = new Matrix<>(matrix);

        // when
        Array<Integer> actualLeftmostMinimaIndexes = Chapter4.mongeLeftmostMinimaIndexes(matrix);

        // then
        assertNotNull(actualLeftmostMinimaIndexes);
        for (int i = 1; i <= actualLeftmostMinimaIndexes.length; i++) {
            int actualLeftmostMinimumIndex = actualLeftmostMinimaIndexes.at(i);
            assertTrue(1 <= actualLeftmostMinimumIndex && actualLeftmostMinimumIndex <= original.columns);
            for (int j = 1; j <= actualLeftmostMinimumIndex - 1; j++) {
                assertTrue(original.at(i, actualLeftmostMinimumIndex) < original.at(i, j));
            }
        }
    }

}
