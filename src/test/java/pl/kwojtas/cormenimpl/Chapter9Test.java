package pl.kwojtas.cormenimpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Pair;
import pl.kwojtas.cormenimpl.util.Point2D;

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
@PrepareForTest({ Chapter5.class })
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
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        int actualMinimum = Chapter9.minimum(array);

        // then
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimum <= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfEvenSize() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        // then
        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfEvenSize2() {
        // given
        Array<Integer> array = new Array<>(7,5,2,9,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        // then
        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindMinimumAndMaximumForArrayOfOddSize() {
        // given
        Array<Integer> array = new Array<>(3,5,2,6,6,5,1,8,9,4,7,4,2);
        Array<Integer> original = new Array<>(array);

        // when
        Pair<Integer, Integer> actualMinimumMaximum = Chapter9.minimumMaximum(array);

        // then
        assertNotNull(actualMinimumMaximum);
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualMinimumMaximum.first <= original.at(i));
            assertTrue(actualMinimumMaximum.second >= original.at(i));
        }
    }

    @Test
    public void shouldFindOrderStatisticUsingRandomizedSelectAtTheFirstRecursionLevel() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 4;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(1);

        // when
        int actualOrderStatistic = Chapter9.randomizedSelect(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingRandomizedSelectByCallingItRecursively() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 8;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(5);
        when(Chapter5.random(8, array.length)).thenReturn(10);
        when(Chapter5.random(8, 10)).thenReturn(8);

        // when
        int actualOrderStatistic = Chapter9.randomizedSelect(array, 1, array.length, order);

        // then
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
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 4;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(1);

        // when
        int actualOrderStatistic = Chapter9.iterativeRandomizedSelect(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingIterativeRandomizedSelectByDividingArray() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 8;
        mockStatic(Chapter5.class);
        when(Chapter5.random(1, array.length)).thenReturn(5);
        when(Chapter5.random(8, array.length)).thenReturn(10);
        when(Chapter5.random(8, 10)).thenReturn(8);

        // when
        int actualOrderStatistic = Chapter9.iterativeRandomizedSelect(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelect() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 7;

        // when
        int actualOrderStatistic = Chapter9.select(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldSortArrayUsingBestCaseQuicksort() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter9.bestCaseQuicksort(array, 1, array.length);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutineInSingleElementArray() {
        // given
        Array<Integer> array = new Array<>(5);
        Array<Integer> original = new Array<>(array);
        int order = 1;

        // when
        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, 1, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 6;

        // when
        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine2() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 4;

        // when
        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSelectUsingMedianSubroutine3() {
        // given
        Array<Integer> array = new Array<>(5,7,9,2,6,8,6,6,3,1,7,8);
        Array<Integer> original = new Array<>(array);
        int order = 8;

        // when
        int actualOrderStatistic = Chapter9.selectUsingMedianSubroutine(array, 1, array.length, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldReturnEmptySetOfQuantilesOfFirstOrder() {
        // given
        Array<Integer> array = new Array<>(5,0,7,9,4,2,6,8,3,1);
        int order = 1;

        // when
        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        // then
        assertNotNull(actualQuantiles);
        assertTrue(actualQuantiles.isEmpty());
    }

    @Test
    public void shouldReturnMedianAsQuantileOfSecondOrder() {
        // given
        Array<Integer> array = new Array<>(5,0,7,9,4,2,6,8,3,1);
        int order = 2;

        // when
        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        // then
        assertNotNull(actualQuantiles);
        assertEquals(1, actualQuantiles.size());
        int actualMedian = actualQuantiles.iterator().next();
        assertTrue(actualMedian == 4 || actualMedian == 5); // could be lower or upper median
    }

    @Test
    public void shouldFindQuantilesOfAverageOrder() {
        // given
        Array<Integer> array = new Array<>(5,0,7,9,4,2,6,8,3,1);
        Array<Integer> original = new Array<>(array);
        int order = 5;

        // when
        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        // then
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
        // given
        Array<Integer> array = new Array<>(5,0,7,9,4,2,6,8,3,1);
        Array<Integer> original = new Array<>(array);
        int order = array.length + 1;

        // when
        Set<Integer> actualQuantiles = Chapter9.quantiles(array, 1, array.length, order);

        // then
        assertNotNull(actualQuantiles);
        assertEquals(original.length, actualQuantiles.size());
        for (int i = 1; i <= original.length; i++) {
            assertTrue(actualQuantiles.contains(original.at(i)));
        }
    }

    @Test
    public void shouldReturnMedianAsMedianProximityOf1() {
        // given
        Array<Integer> array = new Array<>(5,0,15,17,4,2,6,16,3,1);
        int proximitySize = 1;
        int expectedMedian = 4;

        // when
        Set<Integer> actualMedianProximity = Chapter9.medianProximity(array, proximitySize);

        // then
        assertNotNull(actualMedianProximity);
        assertEquals(1, actualMedianProximity.size());
        int actualMedian = actualMedianProximity.iterator().next();
        assertEquals(expectedMedian, actualMedian); // should be the lower median
    }

    @Test
    public void shouldFindMedianProximity() {
        // given
        Array<Integer> array = new Array<>(5,0,15,17,4,2,6,16,3,1);
        Array<Integer> original = new Array<>(array);
        int proximitySize = 4;

        // when
        Set<Integer> actualMedianProximity = Chapter9.medianProximity(array, proximitySize);

        // then
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
        // given
        Array<Integer> array1 = new Array<>(1);
        Array<Integer> array2 = new Array<>(2);
        Array<Integer> original1 = new Array<>(array1);
        Array<Integer> original2 = new Array<>(array2);

        // when
        int actualMedian = Chapter9.twoArraysMedian(array1, 1, 1, array2, 1, 1);

        // then
        assertMedianOfTwoArrays(original1, original2, actualMedian);
    }

    @Test
    public void shouldFindMedianOfTwoArrays() {
        // given
        Array<Integer> array1 = new Array<>(1,2,3,5,6,6,6,7,7,8,8,9);
        Array<Integer> array2 = new Array<>(1,1,5,6,7,8,8,8,9,9,9,9);
        Array<Integer> original1 = new Array<>(array1);
        Array<Integer> original2 = new Array<>(array2);

        // when
        int actualMedian = Chapter9.twoArraysMedian(array1, 1, array1.length, array2, 1, array2.length);

        // then
        assertMedianOfTwoArrays(original1, original2, actualMedian);
    }

    private void assertMedianOfTwoArrays(Array<Integer> original1, Array<Integer> original2, int actualMedian) {
        Array<Integer> combinedArrays = Array.withLength(original1.length + original2.length);
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
        // given
        Array<Integer> array = new Array<>(0,1,2,3,4,5,6,7,8,9);
        Array<Double> weights = new Array<>(0.05,0.05,0.1,0.2,0.02,0.1,0.03,0.05,0.3,0.1);
        Array<Integer> originalArray = new Array<>(array);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        double actualWeightedMedian = Chapter9.weightedMedianUsingSorting(array, weights);

        // then
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
        // given
        Array<Integer> array = new Array<>(6);
        Array<Double> weights = new Array<>(1.0);
        Array<Integer> originalArray = new Array<>(array);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        double actualWeightedMedian = Chapter9.weightedMedian(array, weights, 1, 1);

        // then
        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    @Test
    public void shouldFindWeightedMedian() {
        // given
        Array<Integer> array = new Array<>(0,1,2,3,4,5,6,7,8,9);
        Array<Double> weights = new Array<>(0.05,0.05,0.1,0.2,0.02,0.1,0.03,0.05,0.3,0.1);
        Array<Integer> originalArray = new Array<>(array);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        double actualWeightedMedian = Chapter9.weightedMedian(array, weights, 1, array.length);

        // then
        assertWeightedMedian(originalArray, originalWeights, actualWeightedMedian);
    }

    @Test
    public void shouldFindPostOfficeLocation() {
        // given
        Array<Point2D> points = new Array<>(new Point2D(1.0,1.0),new Point2D(1.0,3.0),new Point2D(1.0,5.0),new Point2D(3.0,1.0),
                new Point2D(3.0,5.0),new Point2D(5.0,1.0),new Point2D(5.0,3.0),new Point2D(5.0,5.0));
        Array<Double> weights = new Array<>(0.1,0.1,0.2,0.02,0.2,0.3,0.05,0.03);
        Array<Point2D> originalPoints = new Array<>(points);
        Array<Double> originalWeights = new Array<>(weights);

        // when
        Point2D actualLocation = Chapter9.postOfficeLocation2D(points, weights);

        // then
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
        // given
        Array<Integer> array = new Array<>(5,12,1,0,13,12,0,10,9,1,4,3,16,15,19,6,11,20,2);
        Array<Integer> original = new Array<>(array);
        int order = 3;

        // when
        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSmallOrderSelect2() {
        // given
        Array<Integer> array = new Array<>(5,12,1,0,13,12,0,10,9,1,4,3,16,15,19,6,11,20,2);
        Array<Integer> original = new Array<>(array);
        int order = 7;

        // when
        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

    @Test
    public void shouldFindOrderStatisticUsingSmallOrderSelectInSingleElementArray() {
        // given
        Array<Integer> array = new Array<>(44);
        Array<Integer> original = new Array<>(array);
        int order = 1;

        // when
        int actualOrderStatistic = Chapter9.smallOrderSelect(array, order);

        // then
        assertOrderStatistic(original, order, actualOrderStatistic);
    }

}
