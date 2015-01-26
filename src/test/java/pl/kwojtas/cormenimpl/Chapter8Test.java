package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Point2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

@RunWith(DataProviderRunner.class)
public class Chapter8Test {

    @DataProvider
    public static Object[][] provideDataForCountingSort() {
        return new Object[][]{
                {new Array<>(34), 60},
                {new Array<>(3,2,1,0), 3},
                {new Array<>(2,5,3,0,2,3,0,3), 5},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingCountingSort(Array<Integer> array, int boundary) {
        // given
        Array<Integer> original = new Array<>(array);
        Array<Integer> actualSorted = new Array<>();

        // when
        Chapter8.countingSort(array, actualSorted, boundary);

        // then
        assertShuffled(original, actualSorted);
        assertSorted(actualSorted);
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingNonStableCountingSort(Array<Integer> array, int boundary) {
        // given
        Array<Integer> original = new Array<>(array);
        Array<Integer> actualSorted = new Array<>();

        // when
        Chapter8.nonStableCountingSort(array, actualSorted, boundary);

        // then
        assertShuffled(original, actualSorted);
        assertSorted(actualSorted);
    }

    @DataProvider
    public static Object[][] provideDataForCountingNumbersInRange() {
        return new Object[][]{
                {new Array<>(34), 34, 34, 34},
                {new Array<>(34), 66, 34, 34},
                {new Array<>(34), 34, 2, 35},
                {new Array<>(34), 34, 35, 35},
                {new Array<>(3,2,1,0), 3, 0, 1},
                {new Array<>(3,2,1,0), 3, 0, 3},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, -1},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, -2, 3},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 5, 8},
                {new Array<>(6,0,2,0,1,3,4,6,1,3,2), 6, 10, 20}
        };
    }

    @Test
    @UseDataProvider("provideDataForCountingNumbersInRange")
    public void shouldCountNumbersInRange(Array<Integer> array, int boundary, int a, int b) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualInRange = Chapter8.countNumbersInRange(array, boundary, a, b);

        // then
        int expectedInRange = 0;
        for (int i = 1; i <= original.length; i++) {
            if (a <= original.at(i) && original.at(i) <= b) {
                expectedInRange++;
            }
        }
        assertEquals(expectedInRange, actualInRange);
    }

    @DataProvider
    public static Object[][] provideDataForRadixSort() {
        return new Object[][]{
                {new Array<>(34), 2},
                {new Array<>(3,2,1,0), 1},
                {new Array<>(345,765,439,89,123,417,688), 3},
                {new Array<>(24015,44036,14014,62027,55033,19012,63032), 5}
        };
    }

    @Test
    @UseDataProvider("provideDataForRadixSort")
    public void shouldSortArrayUsingRadixSort(Array<Integer> array, int digits) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.radixSort(array, digits);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForSortingNSquaredNumbers() {
        return new Object[][]{
                {new Array<>(0)},
                {new Array<>(3,2,1,0)},
                {new Array<>(22,48,1,0,45,26,15)},
                {new Array<>(15,56,25,66,23,92,2,45,7,39)}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingNSquaredNumbers")
    public void shouldSortNSquaredNumbers(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.sortNSquaredNumbers(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForSortingUsingBucketSort() {
        return new Object[][]{
                {new Array<>(.0)},
                {new Array<>(.3,.2,.1,.0)},
                {new Array<>(.22,.48,.1,.0,.45,.26,.15)},
                {new Array<>(.15,.92,.56,.25,.66,.23,.9,.2,.45,.7,.39,.99,.3,.01,.33,.91)}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingUsingBucketSort")
    public void shouldSortArrayUsingBucketSort(Array<Double> array) {
        // given
        Array<Double> original = new Array<>(array);

        // when
        Chapter8.bucketSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForSortingUnitCirclePoints() {
        return new Object[][]{
                {new Array<>(new Point2D(.0,.0))},
                {new Array<>(new Point2D(.3,.3),new Point2D(.2,.2),new Point2D(.1,.1),new Point2D(.0,.0))},
                {new Array<>(new Point2D(.22,.15),new Point2D(.48,.63),new Point2D(.1,.8),new Point2D(.0,.44),
                        new Point2D(.45,.09),new Point2D(.26,.28),new Point2D(.15,.02))},
                {new Array<>(new Point2D(.15,.79),new Point2D(.92,.16),new Point2D(.56,.06),new Point2D(.25,.33),
                        new Point2D(.66,.15),new Point2D(.23,.81),new Point2D(.69,.72),new Point2D(.2,.37),new Point2D(.45,.88),
                        new Point2D(.7,.07),new Point2D(.39,.55),new Point2D(.99,.04),new Point2D(.3,.49),new Point2D(.01,.68),
                        new Point2D(.33,.08),new Point2D(.91,.4))}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingUnitCirclePoints")
    public void shouldSortUnitCirclePoints(Array<Point2D> array) {
        // given
        Array<Point2D> original = new Array<>(array);

        // when
        Chapter8.sortUnitCirclePoints(array);

        // then
        assertShuffled(original, array);
        assertSorted(array, (p1, p2) -> {
            double dist1 = p1.x * p1.x + p1.y * p1.y;
            double dist2 = p2.x * p2.x + p2.y * p2.y;
            return dist1 < dist2 ? -1 : dist1 > dist2 ? 1 : 0;
        });
    }

    @DataProvider
    public static Object[][] provideDataForSortingUsingBitwiseSort() {
        return new Object[][]{
                {new Array<>(0)},
                {new Array<>(0,1)},
                {new Array<>(1,1,0,1)},
                {new Array<>(1,1,1,0,1,1,1,1,1,0,0)}
        };
    }

    @Test
    @UseDataProvider("provideDataForSortingUsingBitwiseSort")
    public void shouldSortArrayUsingBitwiseSort(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.bitwiseSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForCountingSort")
    public void shouldSortArrayUsingCountingSortInPlace(Array<Integer> array, int boundary) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.countingSortInPlace(array, boundary);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForVariousLengthNumbersSort() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1,0)},
                {new Array<>(601346132)},
                {new Array<>(235,510,207,455,317,635,344,102,301)},
                {new Array<>(235,510207,455,317,63534,102,301,42342352,100234,231,331,66,1)},
                {new Array<>(235,510207,455,0,90199239,317,63534,102,301,42342352,100234,231,331,66,1,3002,1123581321)},
                {new Array<>(235,-510207,455,0,90199239,317,63534,-102,301,42342352,-100234,-231,-331,-66,1,3002,-1123581321)}
        };
    }

    @Test
    @UseDataProvider("provideDataForVariousLengthNumbersSort")
    public void shouldSortArrayUsingVariousLengthNumbersSort(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.variousLengthNumbersSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForVariousLengthStringsSort() {
        return new Object[][]{
                {new Array<>("aaa")},
                {new Array<>("d","c","b","a")},
                {new Array<>("cormenimpl")},
                {new Array<>("aaa","fff","eee","ccc","ddd","bbb","ccc")},
                {new Array<>("lorem","ipsum","dolor","sit","amet","consectetur","adipiscing","elit","sed","do","eiusmod","tempor",
                        "incididunt","ut","labore","et","dolore","magna","aliqua")}
        };
    }

    @Test
    @UseDataProvider("provideDataForVariousLengthStringsSort")
    public void shouldSortArrayUsingVariousLengthStringsSort(Array<String> array) {
        // given
        Array<String> original = new Array<>(array);

        // when
        Chapter8.variousLengthStringsSort(array, 1);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForGroupingJugs() {
        return new Object[][]{
                {new Array<>(34.0), new Array<>(34.0)},
                {new Array<>(1.0,2.0,3.0), new Array<>(3.0,2.0,1.0)},
                {new Array<>(1.0,2.0,3.0), new Array<>(1.0,2.0,3.0)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(-0.53,-2.75,2.23,2.55,3.14)},
                {new Array<>(5.0,7.0,9.0,2.0,6.0,8.0,6.0,6.0,3.0,1.0,7.0,8.0),
                        new Array<>(6.0,5.0,9.0,7.0,3.0,8.0,2.0,6.0,8.0,6.0,7.0,1.0)}
        };
    }

    @Test
    @UseDataProvider("provideDataForGroupingJugs")
    public void shouldGroupJugsUsingJugsGroup(Array<Double> jugs1, Array<Double> jugs2) {
        // given
        Array<Double> originalJugs1 = new Array<>(jugs1);
        Array<Double> originalJugs2 = new Array<>(jugs2);

        // when
        Chapter8.jugsGroup(jugs1, jugs2);

        // then
        assertShuffled(originalJugs1, jugs1);
        assertShuffled(originalJugs2, jugs2);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @Test
    @UseDataProvider("provideDataForGroupingJugs")
    public void shouldGroupJugsUsingJugsSort(Array<Double> jugs1, Array<Double> jugs2) {
        // given
        Array<Double> originalJugs1 = new Array<>(jugs1);
        Array<Double> originalJugs2 = new Array<>(jugs2);

        // when
        Chapter8.jugsMatch(jugs1, jugs2, 1, jugs1.length);

        // then
        assertShuffled(originalJugs1, jugs1);
        assertShuffled(originalJugs2, jugs2);
        for (int i = 1; i <= jugs1.length; i++) {
            assertEquals(jugs1.at(i), jugs2.at(i));
        }
    }

    @DataProvider
    public static Object[][] provideDataForKSorting() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(34), 3},
                {new Array<>(3,2,1), 2},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 8},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 6},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 4},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForKSorting")
    public void shouldKSortArray(Array<Integer> array, int k) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter8.kSort(array, k, 1, array.length);

        // then
        assertShuffled(original, array);
        for (int i = 1; i <= array.length - k; i++) {
            assertTrue(array.at(i) <= array.at(i + k));
        }
    }

}
