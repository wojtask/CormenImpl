package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;

@RunWith(DataProviderRunner.class)
public class Chapter9Test {

    @DataProvider
    public static Object[][] provideDataForFindingMinimum() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingMinimum")
    public void shouldFindMinimum(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualMinimum = Chapter9.minimum(array);

        // then
        assertArrayEquals(original, array);
        for (int i = 1; i <= array.length; i++) {
            assertTrue(actualMinimum <= array.at(i));
        }
    }

    @Test
    @UseDataProvider("provideDataForFindingMinimum")
    public void shouldFindMinimumAndMaximum(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        // then
        assertArrayEquals(original, array);
        for (int i = 1; i <= array.length; i++) {
            assertTrue(actualMinimumMaximum.first <= array.at(i));
            assertTrue(actualMinimumMaximum.second >= array.at(i));
        }
    }

    @DataProvider
    public static Object[][] provideDataForFindingOrderStatistic() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(3,2,1), 1},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 3},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 8},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), 12},
                {new Array<>(5,7,5,9,4,2,9,6,1,6,8,6,6,2,8,3,1,7,3,7,8), 15}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingOrderStatistic")
    public void shouldFindOrderStatisticUsingRandomizedSelect(Array<Integer> array, int order) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualOrderStatistic = Chapter9.randomizedSelect(array, 1, array.length, order);

        // then
        assertShuffled(original, array);
        int lessThanOrderStatistic = 0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualOrderStatistic) {
                lessThanOrderStatistic++;
            }
        }
        assertTrue(lessThanOrderStatistic < order);
    }

    @Test
    @UseDataProvider("provideDataForFindingOrderStatistic")
    public void shouldFindOrderStatisticUsingIterativeRandomizedSelect(Array<Integer> array, int order) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualOrderStatistic = Chapter9.iterativeRandomizedSelect(array, 1, array.length, order);

        // then
        assertShuffled(original, array);
        int lessThanOrderStatistic = 0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualOrderStatistic) {
                lessThanOrderStatistic++;
            }
        }
        assertTrue(lessThanOrderStatistic < order);
    }

    @Test
    @UseDataProvider("provideDataForFindingOrderStatistic")
    public void shouldFindOrderStatisticUsingSelect(Array<Integer> array, int order) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualOrderStatistic = Chapter9.select(array, 1, array.length, order);

        // then
        assertShuffled(original, array);
        int lessThanOrderStatistic = 0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualOrderStatistic) {
                lessThanOrderStatistic++;
            }
        }
        assertTrue(lessThanOrderStatistic < order);
    }

    @DataProvider
    public static Object[][] provideDataForSorting() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23)},
                {new Array<>("aaa","eee","ccc","ddd","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForSorting")
    public <T extends Comparable> void shouldSortArrayUsingBestCaseQuicksort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter9.bestCaseQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    @UseDataProvider("provideDataForFindingOrderStatistic")
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine(Array<Integer> array, int order) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        // then
        assertShuffled(original, array);
        int lessThanOrderStatistic = 0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualOrderStatistic) {
                lessThanOrderStatistic++;
            }
        }
        assertTrue(lessThanOrderStatistic < order);
    }

    @DataProvider
    public static Object[][] provideDataForFindingQuantiles() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 1},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 2},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 3},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 4},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 5},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 6},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 7},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 8},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 9},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 10},
                {new Array<>(5,0,7,9,4,2,6,8,3,1), 11}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingQuantiles")
    public void shouldFindQuantiles(Array<Integer> array, int order) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        // then
        assertShuffled(original, array);
        assertEquals(order - 1, actualQuantiles.size());
        assertQuantiles(array, actualQuantiles);
    }

    private void assertQuantiles(Array<Integer> array, Set<Integer> quantiles) {
        array.getData().sort(Comparator.<Integer>naturalOrder());
        int i = 1;
        while (i <= array.length && !quantiles.contains(array.at(i))) {
            i++;
        }
        int minDistance = i - 1;
        int maxDistance = i - 1;
        int distance = 0;
        i++;
        while (i <= array.length) {
            if (!quantiles.contains(array.at(i))) {
                distance++;
            } else {
                minDistance = min(minDistance, distance);
                maxDistance = max(maxDistance, distance);
                distance = 0;
            }
            i++;
        }
        assertTrue(maxDistance - minDistance <= 1);
    }

    @DataProvider
    public static Object[][] provideDataForFindingMedianProximity() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 1},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 2},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 3},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 4},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 5},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 6},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 7},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 8},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 9},
                {new Array<>(5,0,15,17,4,2,6,16,3,1), 10}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingMedianProximity")
    public void shouldFindMedianProximity(Array<Integer> array, int proximitySize) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Set<Integer> actualMedianProximity = Chapter9.medianProximity(array, proximitySize);

        // then
        assertShuffled(original, array);
        assertEquals(proximitySize, actualMedianProximity.size());
        array.getData().sort(Comparator.<Integer>naturalOrder());
        int median = array.at((array.length + 1) / 2);
        array.getData().sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return abs(o1 - median) - abs(o2 - median);
            }
        });
        for (int i = 1; i <= proximitySize; i++) {
            assertTrue(actualMedianProximity.contains(array.at(i)));
        }
    }

    @DataProvider
    public static Object[][] provideDataForFindingMedianOfTwoArrays() {
        return new Object[][]{
                {new Array<>(34), new Array<>(23)},
                {new Array<>(1,3,4), new Array<>(2,5,6)},
                {new Array<>(3,3,3), new Array<>(1,1,1)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(2,4,4,5,5,6,6,6,8,8,9,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingMedianOfTwoArrays")
    public void shouldFindMedianOfTwoArrays(Array<Integer> array1, Array<Integer> array2) {
        // given
        Array<Integer> original1 = new Array<>(array1);
        Array<Integer> original2 = new Array<>(array2);

        // when
        int actualMedian = Chapter9.twoArraysMedian(array1, 1, array1.length, array2, 1, array2.length);

        // then
        assertArrayEquals(original1, array1);
        assertArrayEquals(original2, array2);
        List<Integer> combinedArrays = new ArrayList<>(array1.getData());
        combinedArrays.addAll(array2.getData());
        combinedArrays.sort(Comparator.<Integer>naturalOrder());
        int expectedMedian = combinedArrays.get((combinedArrays.size() - 1) / 2);
        assertEquals(expectedMedian, actualMedian);
    }

    @DataProvider
    public static Object[][] provideDataForFindingWeightedMedian() {
        return new Object[][]{
                {new Array<>(34.0), new Array<>(1.0)},
                {new Array<>(3.0,1.0,4.0), new Array<>(0.3,0.3,0.4)},
                {new Array<>(3.0,1.0,4.0), new Array<>(0.05,0.9,0.05)},
                {new Array<>(0.0,1.0,2.0,3.0,4.0,5.0,6.0,7.0,8.0,9.0), new Array<>(0.05,0.05,0.1,0.2,0.02,0.1,0.03,0.05,0.3,0.1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingWeightedMedian")
    public void shouldFindWeightedMedianUsingSorting(Array<Double> array, Array<Double> weights) {
        // given
        Array<Double> originalArray = new Array<>(array);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        double actualWeightedMedian = Chapter9.weightedMedianUsingSorting(array, weights);

        // then
        assertShuffled(originalArray, array);
        assertShuffled(originalWeights, weights);
        assertWeightedMedian(array, weights, actualWeightedMedian);
    }

    private void assertWeightedMedian(Array<Double> array, Array<Double> weights, double actualWeightedMedian) {
        double weightLeftSum = 0.0;
        double weightRightSum = 0.0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualWeightedMedian) {
                weightLeftSum += weights.at(i);
            } else if (array.at(i) > actualWeightedMedian) {
                weightRightSum += weights.at(i);
            }
        }
        assertTrue(weightLeftSum < 0.5);
        assertTrue(weightRightSum <= 0.5);
    }

    @Test
    @UseDataProvider("provideDataForFindingWeightedMedian")
    public void shouldFindWeightedMedian(Array<Double> array, Array<Double> weights) {
        // given
        Array<Double> originalArray = new Array<>(array);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        double actualWeightedMedian = Chapter9.weightedMedian(array, weights, 1, array.length);

        // then
        assertShuffled(originalArray, array);
        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    @DataProvider
    public static Object[][] provideDataForFindingPostOfficeLocation() {
        return new Object[][]{
                {new Array<>(new Pair<>(34.0,0.0)), new Array<>(1.0)},
                {new Array<>(new Pair<>(3.0,2.0),new Pair<>(1.0,1.0),new Pair<>(4.0,2.0)), new Array<>(0.3,0.3,0.4)},
                {new Array<>(new Pair<>(3.0,2.0),new Pair<>(1.0,1.0),new Pair<>(4.0,2.0)), new Array<>(0.05,0.9,0.05)},
                {new Array<>(new Pair<>(1.0,1.0),new Pair<>(1.0,3.0),new Pair<>(1.0,5.0),new Pair<>(3.0,1.0),
                        new Pair<>(3.0,5.0),new Pair<>(5.0,1.0),new Pair<>(5.0,3.0),new Pair<>(5.0,5.0)),
                        new Array<>(0.1,0.1,0.2,0.02,0.2,0.3,0.05,0.03)}
        };
    }

    @Test
    @UseDataProvider("provideDataForFindingPostOfficeLocation")
    public void shouldFindPostOfficeLocation(Array<Pair<Double, Double>> points, Array<Double> weights) {
        // given
        Array<Pair<Double, Double>> originalPoints = new Array<>(points);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        Pair<Double, Double> actualLocation = Chapter9.postOfficeLocation2D(points, weights);

        // then
        assertArrayEquals(originalPoints, points);
        assertArrayEquals(originalWeights, weights);
        double postOfficeTotalWeighedDistance = getTotalWeighedDistance(actualLocation, points, weights);
        for (int i = 1; i <= points.length; i++) {
            double pointTotalWeighedDistance = getTotalWeighedDistance(points.at(i), points, weights);
            assertTrue(postOfficeTotalWeighedDistance <= pointTotalWeighedDistance);
        }
    }

    private double getTotalWeighedDistance(Pair<Double, Double> origin, Array<Pair<Double, Double>> locations, Array<Double> weights) {
        double totalWeighedDistance = 0.0;
        for (int i = 1; i <= locations.length; i++) {
            totalWeighedDistance += weights.at(i) * (abs(origin.first - locations.at(i).first) + abs(origin.second - locations.at(i).second));
        }
        return totalWeighedDistance;
    }

}
