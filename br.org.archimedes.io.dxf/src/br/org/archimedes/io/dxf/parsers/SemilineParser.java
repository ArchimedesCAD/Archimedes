package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFRay;
import org.kabeja.dxf.helpers.Vector;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.semiline.Semiline;

public class SemilineParser extends ElementParser{
    
	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		Collection<Element> archimedesSemilines = new ArrayList<Element>();
		List<DXFRay> dxfSemilines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_RAY);
		
		if(dxfSemilines != null) {
			for (DXFRay dxfSemiline : dxfSemilines) {
		  		org.kabeja.dxf.helpers.Point startPoint = dxfSemiline.getBasePoint();
		  		Vector direction = dxfSemiline.getDirection();
		  		
		  		double x = startPoint.getX();
				double y = startPoint.getY();
				
				Point p1 = new Point(x, y);
		  		Point p2 = new Point(direction.getX() + x, direction.getY() + y);
		  		
				Semiline semiline = new Semiline(p1, p2);
							
				archimedesSemilines.add(semiline);
			}
		}
		return archimedesSemilines;
	}
}
