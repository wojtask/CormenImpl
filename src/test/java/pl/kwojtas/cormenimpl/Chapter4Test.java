package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

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

}
