package br.org.archimedes.intersector.dimensionline;

import java.util.Collection;
import java.util.HashSet;

import br.org.archimedes.dimension.Dimension;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.intersector.lineline.LineLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class DimensionLineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Dimension dimension;
		Line line;
		Intersector intersector = new LineLineIntersector();
		
		if (element.getClass() == Dimension.class)
		{
			dimension = (Dimension) element;
			line = (Line) otherElement;
		}
		else
		{
			dimension = (Dimension) otherElement;
			line = (Line) element;
		}
		
		Collection<Line> dimensionLines = dimension.getLinesToDraw();
		// Using a Set prevents the addition of the same point twice
		// in the case where the line intersects two of the lines
		// of Dimension in the same point
		Collection<Point> intersections = new HashSet<Point>();
		
		for (Line l : dimensionLines)
			intersections.addAll(intersector.getIntersections(l, line));
		
		return intersections;
	}

}
