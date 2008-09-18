
package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class ArcPolylineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        Arc baseArc;
        Polyline polyline;

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        if (element.getClass() == Arc.class) {
            baseArc = (Arc) element;
            polyline = (Polyline) otherElement;
        }
        else {
            baseArc = (Arc) otherElement;
            polyline = (Polyline) element;
        }

        List<Line> lines = polyline.getLines();
        Collection<Point> intersectionPoints = new ArrayList<Point>();

        Collection<Point> intersection;

        ArcLineIntersector arcLineIntersector = new ArcLineIntersector();
        for (Line line : lines) {
            intersection = arcLineIntersector.getIntersections(line, baseArc);
            intersectionPoints.addAll(intersection);
        }
        return intersectionPoints;
    }

}
