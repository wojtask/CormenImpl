package pl.kwojtas.cormenimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.Pair;
import pl.kwojtas.cormenimpl.datastructure.Point2D;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.TestUtil.sortArray;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Chapter5.class})
public class Chapter9Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter9> constructor = Chapter9.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldFindMinimum() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        int actualMinimum = Chapter9.minimum(array);

        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimum <= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfEvenSize() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfEvenSize2() {
        Array<Integer> array = Array.of(7, 5, 2, 9, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfOddSize() {
        Array<Integer> array = Array.of(3, 5, 2, 6, 6, 5, 1, 8, 9, 4, 7, 4, 2);
        Array<Integer> original = Array.copyOf(array);

        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindOrderStatisticUsingRandomizedSelectAtTheFirstRecursionLevel() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 4;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(1);

        int actualOrderStatistic = Chapter9.randomizedSelect(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingRandomizedSelectByCallingItRecursively() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 8;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(5);
        when(Chapter5.random(8, array.length)).thenReturn(10);
        when(Chapter5.random(8, 10)).thenReturn(8);

        int actualOrderStatistic = Chapter9.randomizedSelect(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    private void assertOrderStatistic(Array<Integer> array, int order, int actualOrderStatistic) {
        int lessThanOrderStatistic = 0;
        int greaterThanOrderStatistic = 0;
        for (int i = 1; i <= array.length; i++) {
            if (array.at(i) < actualOrderStatistic) {
                lessThanOrderStatistic++;
            } else if (array.at(i) > actualOrderStatistic) {
                greaterThanOrderStatistic++;
            }
        }
        assertTrue(lessThanOrderStatistic < order);
        assertTrue(greaterThanOrderStatistic <= array.length - order);
    }

    @Test
    public void shouldFindOrderStatisticUsingIterativeRandomizedSelectAtTheFirstAttempt() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 4;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(1);

        int actualOrderStatistic = Chapter9.iterativeRandomizedSelect(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingIterativeRandomizedSelectByDividingArray() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 8;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(5);
        when(Chapter5.random(8, array.length)).thenReturn(10);
        when(Chapter5.random(8, 10)).thenReturn(8);

        int actualOrderStatistic = Chapter9.iterativeRandomizedSelect(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelect() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 7;

        int actualOrderStatistic = Chapter9.select(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldSortArrayUsingBestCaseQuicksort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter9.bestCaseQuicksort(array, 1, array.length);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutineInSingleElementArray() {
        Array<Integer> array = Array.of(5);
        Array<Integer> original = Array.copyOf(array);
        int order = 1;

        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, 1, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 6;

        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine2() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 4;

        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine3() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);
        int order = 8;

        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldReturnEmptySetOfQuantilesOfFirstOrder() {
        Array<Integer> array = Array.of(5, 0, 7, 9, 4, 2, 6, 8, 3, 1);
        int order = 1;

        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        assertNotNull(actualQuantiles);
        assertTrue(actualQuantiles.isEmpty());
    }

    @Test
    public void shouldReturnMedianAsQuantileOfSecondOrder() {
        Array<Integer> array = Array.of(5, 0, 7, 9, 4, 2, 6, 8, 3, 1);
        int order = 2;

        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        assertNotNull(actualQuantiles);
        assertEquals(1, actualQuantiles.size());
        int actualMedian = actualQuantiles.iterator().next();
        assertTrue(actualMedian == 4 || actualMedian == 5); // could be lower or upper median
    }

    @Test
    public void shouldFindQuantilesOfAverageOrder() {
        Array<Integer> array = Array.of(5, 0, 7, 9, 4, 2, 6, 8, 3, 1);
        Array<Integer> original = Array.copyOf(array);
        int order = 5;

        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        assertNotNull(actualQuantiles);
        assertEquals(order - 1, actualQuantiles.size());
        assertQuantiles(original, actualQuantiles);
    }

    private void assertQuantiles(Array<Integer> array, Set<Integer> quantiles) {
        sortArray(array);
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

    @Test
    public void shouldReturnAllElementsAsQuantilesOfHighestOrder() {
        Array<Integer> array = Array.of(5, 0, 7, 9, 4, 2, 6, 8, 3, 1);
        Array<Integer> original = Array.copyOf(array);
        int order = array.length + 1;

        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        assertNotNull(actualQuantiles);
        assertEquals(original.length, actualQuantiles.size());
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualQuantiles.contains(original.at(i)));
        }
    }

    @Test
    public void shouldReturnMedianAsMedianProximityOf1() {
        Array<Integer> array = Array.of(5, 0, 15, 17, 4, 2, 6, 16, 3, 1);
        int proximitySize = 1;
        int expectedMedian = 4;

        Set<Integer> actualMedianProximity = Chapter9.medianProximity(array, proximitySize);

        assertNotNull(actualMedianProximity);
        assertEquals(1, actualMedianProximity.size());
        int actualMedian = actualMedianProximity.iterator().next();
        assertEquals(expectedMedian, actualMedian); // should be the lower median
    }

    @Test
    public void shouldFindMedianProximity() {
        Array<Integer> array = Array.of(5, 0, 15, 17, 4, 2, 6, 16, 3, 1);
        Array<Integer> original = Array.copyOf(array);
        int proximitySize = 4;

        Set<Integer> actualMedianProximity = Chapter9.medianProximity(array, proximitySize);

        assertNotNull(actualMedianProximity);
        assertEquals(proximitySize, actualMedianProximity.size());
        assertMedianProximity(original, proximitySize, actualMedianProximity);
    }

    private void assertMedianProximity(Array<Integer> array, int proximitySize, Set<Integer> actualMedianProximity) {
        sortArray(array);
        int median = array.at((array.length + 1) / 2);
        sortArray(array, (o1, o2) -> abs(o1 - median) - abs(o2 - median));
        for (int i = 1; i <= proximitySize; i++) {
            assertTrue(actualMedianProximity.contains(array.at(i)));
        }
    }

    @Test
    public void shouldFindMedianOfTwoArraysOfOneElementEach() {
        Array<Integer> array1 = Array.of(1);
        Array<Integer> array2 = Array.of(2);
        Array<Integer> original1 = Array.copyOf(array1);
        Array<Integer> original2 = Array.copyOf(array2);

        int actualMedian = Chapter9.twoArraysMedian(array1, 1, 1, array2, 1, 1);

        assertMedianOfTwoArrays(original1, original2, actualMedian);
    }

    @Test
    public void shouldFindMedianOfTwoArrays() {
        Array<Integer> array1 = Array.of(1, 2, 3, 5, 6, 6, 6, 7, 7, 8, 8, 9);
        Array<Integer> array2 = Array.of(1, 1, 5, 6, 7, 8, 8, 8, 9, 9, 9, 9);
        Array<Integer> original1 = Array.copyOf(array1);
        Array<Integer> original2 = Array.copyOf(array2);

        int actualMedian = Chapter9.twoArraysMedian(array1, 1, array1.length, array2, 1, array2.length);

        assertMedianOfTwoArrays(original1, original2, actualMedian);
    }

    private void assertMedianOfTwoArrays(Array<Integer> original1, Array<Integer> original2, int actualMedian) {
        Array<Integer> combinedArrays = Array.ofLength(original1.length + original2.length);
        for (int i = 1; i <= original1.length; i++) {
            combinedArrays.set(i, original1.at(i));
        }
        for (int i = 1; i <= original2.length; i++) {
            combinedArrays.set(original1.length + i, original2.at(i));
        }
        sortArray(combinedArrays);
        int expectedMedian = combinedArrays.at((combinedArrays.length + 1) / 2);
        assertEquals(expectedMedian, actualMedian);
    }

    @Test
    public void shouldFindWeightedMedianUsingSorting() {
        Array<Integer> array = Array.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Array<Double> weights = Array.of(0.05, 0.05, 0.1, 0.2, 0.02, 0.1, 0.03, 0.05, 0.3, 0.1);
        Array<Integer> originalArray = Array.copyOf(array);
        Array<Double> originalWeights = Array.copyOf(weights);

        double actualWeightedMedian = Chapter9.weightedMedianUsingSorting(array, weights);

        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    private void assertWeightedMedian(Array<Integer> array, Array<Double> weights, double actualWeightedMedian) {
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
    public void shouldFindWeightedMedianInSingleElementArray() {
        Array<Integer> array = Array.of(6);
        Array<Double> weights = Array.of(1.0);
        Array<Integer> originalArray = Array.copyOf(array);
        Array<Double> originalWeights = Array.copyOf(weights);

        double actualWeightedMedian = Chapter9.weightedMedian(array, weights, 1, 1);

        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    @Test
    public void shouldFindWeightedMedian() {
        Array<Integer> array = Array.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Array<Double> weights = Array.of(0.05, 0.05, 0.1, 0.2, 0.02, 0.1, 0.03, 0.05, 0.3, 0.1);
        Array<Integer> originalArray = Array.copyOf(array);
        Array<Double> originalWeights = Array.copyOf(weights);

        double actualWeightedMedian = Chapter9.weightedMedian(array, weights, 1, array.length);

        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    @Test
    public void shouldFindPostOfficeLocation() {
        Array<Point2D> points = Array.of(new Point2D(1.0, 1.0), new Point2D(1.0, 3.0), new Point2D(1.0, 5.0), new Point2D(3.0, 1.0),
                new Point2D(3.0, 5.0), new Point2D(5.0, 1.0), new Point2D(5.0, 3.0), new Point2D(5.0, 5.0));
        Array<Double> weights = Array.of(0.1, 0.1, 0.2, 0.02, 0.2, 0.3, 0.05, 0.03);
        Array<Point2D> originalPoints = Array.copyOf(points);
        Array<Double> originalWeights = Array.copyOf(weights);

        Point2D actualLocation = Chapter9.postOfficeLocation2D(points, weights);

        assertNotNull(actualLocation);
        double postOfficeTotalWeighedDistance = getTotalWeighedDistance(actualLocation, points, weights);
        for (int i = 1; i <= originalPoints.length; i++) {
            double pointTotalWeighedDistance = getTotalWeighedDistance(originalPoints.at(i), originalPoints, originalWeights);
            assertTrue(postOfficeTotalWeighedDistance <= pointTotalWeighedDistance);
        }
    }

    private double getTotalWeighedDistance(Point2D origin, Array<Point2D> locations, Array<Double> weights) {
        double totalWeighedDistance = 0.0;
        for (int i = 1; i <= locations.length; i++) {
            totalWeighedDistance += weights.at(i) * (abs(origin.x - locations.at(i).x) + abs(origin.y - locations.at(i).y));
        }
        return totalWeighedDistance;
    }

    @Test
    public void shouldFindOrderStatisticUsingSmallOrderSelect() {
        Array<Integer> array = Array.of(5, 12, 1, 0, 13, 12, 0, 10, 9, 1, 4, 3, 16, 15, 19, 6, 11, 20, 2);
        Array<Integer> original = Array.copyOf(array);
        int order = 3;

        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSmallOrderSelect2() {
        Array<Integer> array = Array.of(5, 12, 1, 0, 13, 12, 0, 10, 9, 1, 4, 3, 16, 15, 19, 6, 11, 20, 2);
        Array<Integer> original = Array.copyOf(array);
        int order = 7;

        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSmallOrderSelectInSingleElementArray() {
        Array<Integer> array = Array.of(44);
        Array<Integer> original = Array.copyOf(array);
        int order = 1;

        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        assertOrderStatistic(original, order, actualOrderStatistic);
    }

}
