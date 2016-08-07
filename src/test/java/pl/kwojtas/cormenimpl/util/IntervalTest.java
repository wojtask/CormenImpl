package pl.kwojtas.cormenimpl.util;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class IntervalTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldCreateIntervalFromLowerAndUpperBounds() {
        double lowerBound = -2.3;
        double upperBound = 3.1;

        Interval interval = new Interval(lowerBound, upperBound);

        assertEquals(lowerBound, interval.a, 1e-7);
        assertEquals(upperBound, interval.b, 1e-7);
    }

    @Test
    public void shouldNotCreateIntervalWhenLowerBoundIsGreaterThanUpperBound() {
        double lowerBound = -1.0;
        double upperBound = -1.1;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Lower bound greater than upper bound");
        new Interval(lowerBound, upperBound);
    }

}
