
package br.org.archimedes.intersector.infinitelinepolyline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.lineinfiniteline.LineInfiniteLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class InfinitelinePolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        InfiniteLine infiniteLine;
        Polyline polyline;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        if (element.getClass() == Line.class) {
            infiniteLine = (InfiniteLine) element;
            polyline = (Polyline) otherElement;
        }
        else {
            infiniteLine = (InfiniteLine) otherElement;
            polyline = (Polyline) element;
        }

        List<Line> lines = polyline.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        LineInfiniteLineIntersector lineInfinitelineIntersector = new LineInfiniteLineIntersector();
        for (Line line : lines) {
            intersection = lineInfinitelineIntersector.getIntersections(line,
                    infiniteLine);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }

}
