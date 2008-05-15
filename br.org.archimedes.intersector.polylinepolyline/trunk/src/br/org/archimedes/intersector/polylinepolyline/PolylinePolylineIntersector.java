
package br.org.archimedes.intersector.polylinepolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.linePolyline.LinePolylineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class PolylinePolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        Polyline polyline1;
        Polyline polyline2;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        polyline1 = (Polyline) element;
        polyline2 = (Polyline) otherElement;

        List<Line> lines = polyline1.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        LinePolylineIntersector linePolylineIntersector = new LinePolylineIntersector();
        for (Line line : lines) {
            intersection = linePolylineIntersector.getIntersections(line,
                    polyline2);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }
}
