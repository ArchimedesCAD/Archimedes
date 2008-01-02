package br.org.archimedes.io.xml;

import java.util.List;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.ElementFactory;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * @author vidlopes
 * 
 */
public class LineElementFactory extends ElementFactory {
	public LineElementFactory() {
		
	}
	
	/**
	 * @param className
	 * @param args
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Element createElement(String className, Object... args) {
		List<Point> points = (List<Point>) args[0];
		
		Line line = null;
		try {
			line = new Line(points.get(0), points.get(1));
		} catch (NullArgumentException e) {
			// Ok, devolvo null
		} catch (InvalidArgumentException e) {
			// Ok, devolvo null
		}
		return line;
	}
}
