package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

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

}
