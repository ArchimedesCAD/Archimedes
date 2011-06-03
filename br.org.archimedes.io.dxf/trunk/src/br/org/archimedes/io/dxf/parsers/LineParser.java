package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFLine;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class LineParser extends ElementParser{
	
	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		Collection<Element> archimedesLines = new ArrayList<Element>();
		List<DXFLine> dxfLines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LINE);
		
		if(dxfLines != null) {
			for (DXFLine dxfLine : dxfLines) {
		  		org.kabeja.dxf.helpers.Point startPoint = dxfLine.getStartPoint();
		  		org.kabeja.dxf.helpers.Point endPoint = dxfLine.getEndPoint();
	
		  		Point p1 = new Point(startPoint.getX(), startPoint.getY());
		  		Point p2 = new Point(endPoint.getX(), endPoint.getY());
		  		
				Line line = new Line(p1, p2);
							
				archimedesLines.add(line);
			}
		}
		return archimedesLines;
	}
}
