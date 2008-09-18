
package br.org.archimedes.intersectors;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class DimensionLineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element,
            Element otherElement) throws NullArgumentException {

        if (element == null || otherElement == null)
            throw new NullArgumentException();

        Dimension dimension;
        Line line;
        Intersector intersector;

        if (element.getClass() == Dimension.class) {
            dimension = (Dimension) element;
            line = (Line) otherElement;
        }
        else {
            dimension = (Dimension) otherElement;
            line = (Line) element;
        }

        intersector = new LineTextIntersector();
        Collection<Point> intersections = new LinkedList<Point>();
        intersections.addAll(intersector.getIntersections(line, dimension
                .getText()));

        intersector = new LineLineIntersector();
        Collection<Line> dimensionLines = dimension.getLinesToDraw();
        Set<Point> linesIntersections = new HashSet<Point>();
        for (Line l : dimensionLines) {
            linesIntersections.addAll(intersector.getIntersections(l, line));
        }
        intersections.addAll(linesIntersections);

        return intersections;
    }
}
