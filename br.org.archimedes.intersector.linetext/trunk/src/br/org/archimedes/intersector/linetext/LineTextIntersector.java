package br.org.archimedes.intersector.linetext;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Intersector;
import br.org.archimedes.intersector.lineline.LineLineIntersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.text.Text;

public class LineTextIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Line line;
		Text text;
		LineLineIntersector intersector = new LineLineIntersector();

		if (element.getClass() == Line.class) {
			line = (Line) element;
			text = (Text) otherElement;
		} else {
			line = (Line) otherElement;
			text = (Text) element;
		}
		Collection<Point> boundaryPoints = text.getBoundaryRectangle().getPoints();
		Point lastPoint = null;
		Collection<Line> boundaryLines = new ArrayList<Line>();
		for (Point boundaryPoint : boundaryPoints) {
			if (lastPoint != null) {
				try {
					boundaryLines.add(new Line(lastPoint, boundaryPoint));
				} catch (InvalidArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			lastPoint = boundaryPoint;
		}
		Collection<Point> intersections = new ArrayList<Point>();
		for (Line l : boundaryLines)
			intersections.addAll(intersector.getIntersections(l, line));
		return intersections;
	}

}
