package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFXLine;
import org.kabeja.dxf.helpers.Vector;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class InfiniteLineParser extends ElementParser{

	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		Collection<Element> archimedesInfiniteLines = new ArrayList<Element>();
		List<DXFXLine> dxfInfiniteLines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_XLINE);
		
		if(dxfInfiniteLines != null) {
			for (DXFXLine dxfInfiniteLine : dxfInfiniteLines) {
		  		org.kabeja.dxf.helpers.Point startPoint = dxfInfiniteLine.getBasePoint();
		  		Vector direction = dxfInfiniteLine.getDirection();
		  		
		  		double x = startPoint.getX();
				double y = startPoint.getY();
				
				Point p1 = new Point(x, y);
		  		Point p2 = new Point(direction.getX() + x, direction.getY() + y);
		  		
				InfiniteLine infiniteLine = new InfiniteLine(p1, p2);
							
				archimedesInfiniteLines.add(infiniteLine);
			}
		}
		return archimedesInfiniteLines;
	}

}
