package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Point2DTest {

    @Test
    public void shouldCreatePoint2DFromGivenCoordinates() {
        double x = 4.5;
        double y = -3.0;

        Point2D point2D = new Point2D(x, y);

        assertEquals(x, point2D.x, 1e-7);
        assertEquals(y, point2D.y, 1e-7);
    }
}
