package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntervalTest {

    @Test
    public void shouldCreateIntervalFromLowerAndUpperBounds() {
        double lowerBound = -2.3;
        double upperBound = 3.1;

        Interval interval = new Interval(lowerBound, upperBound);

        assertEquals(lowerBound, interval.a, 1e-7);
        assertEquals(upperBound, interval.b, 1e-7);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateIntervalWhenLowerBoundIsGreaterThanUpperBound() {
        double lowerBound = -1.0;
        double upperBound = -1.1;

        try {
            new Interval(lowerBound, upperBound);
        } catch (RuntimeException e) {
            assertEquals("Lower bound greater than upper bound", e.getMessage());
            throw e;
        }
    }

}
