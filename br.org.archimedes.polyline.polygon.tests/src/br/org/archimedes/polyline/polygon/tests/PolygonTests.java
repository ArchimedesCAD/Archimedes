package br.org.archimedes.polyline.polygon.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.polygon.Polygon;

public class PolygonTests {
    
    Polygon p;
    
    @Test
    public void shouldCreateAPolygonWith4VertexTest() throws NullArgumentException, InvalidArgumentException {
        p = new Polygon(new Point(0.0, 0.0), new Point(1.0, 1.0), 4, false);
        List<Point> vertexList = p.getVertexPoints();
        assertEquals(4, vertexList.size());
    }
    
    @Test(expected=InvalidArgumentException.class)
    public void shouldThrowInvalidArgumentExceptionWhenSidesLessThen3() throws NullArgumentException, InvalidArgumentException {
        p = new Polygon(new Point(0.0, 0.0), new Point(1.0, 1.0), 2, false);
    }
    
    @Test(expected=InvalidArgumentException.class)
    public void shouldThrowInvalidArgumentExceptionWhenPointsAreEqual() throws NullArgumentException, InvalidArgumentException {
        p = new Polygon(new Point(0.0, 0.0), new Point(0.0, 0.0), 3, false);
    }

}
