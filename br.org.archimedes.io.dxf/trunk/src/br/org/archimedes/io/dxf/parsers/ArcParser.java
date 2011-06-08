package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFArc;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;

import br.org.archimedes.arc.Arc;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class ArcParser extends ElementParser{

	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		ArrayList<Element> archimedesArcs = new ArrayList<Element>();
		@SuppressWarnings("unchecked")
        List<DXFArc> dxfArcs = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_ARC);

		if(dxfArcs != null) {
			for (DXFArc dxfArc : dxfArcs) {
		  		
				boolean isCounterClockwise = dxfArc.isCounterClockwise();
				
				Point initialPoint = transformToArchimedesPoint(dxfArc.getStartPoint());
				Point endingPoint = transformToArchimedesPoint(dxfArc.getEndPoint());
				Point centerPoint = transformToArchimedesPoint(dxfArc.getCenterPoint());
		  		
		  		Arc arc = new Arc(initialPoint, endingPoint, centerPoint, !isCounterClockwise);
		  		
				archimedesArcs.add(arc);
			}
		}
		return archimedesArcs;
		
	}
}
