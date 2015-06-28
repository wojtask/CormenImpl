package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntervalTest {

    @Test
    public void shouldCreateIntervalFromLowerAndUpperBounds() {
        // given
        double lowerBound = -2.3;
        double upperBound = 3.1;

        // when
        Interval interval = new Interval(lowerBound, upperBound);

        // then
        assertEquals(lowerBound, interval.a, 1e-7);
        assertEquals(upperBound, interval.b, 1e-7);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateIntervalWhenLowerBoundIsGreaterThanUpperBound() {
        // given
        double lowerBound = -1.0;
        double upperBound = -1.1;

        try {
            // when
            new Interval(lowerBound, upperBound);
        } catch (RuntimeException e) {
            // then
            assertEquals("Lower bound greater than upper bound", e.getMessage());
            throw e;
        }
    }

}
