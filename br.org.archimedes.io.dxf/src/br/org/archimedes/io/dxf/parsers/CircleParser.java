/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Victor D. Lopes - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 */

package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFCircle;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class CircleParser extends ElementParser {

	@Override
	public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
		
		Collection<Element> archimedesCircles = new ArrayList<Element>();
		List<DXFCircle> dxfCircles = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_CIRCLE);
		
		if(dxfCircles != null) {
			for (DXFCircle dxfCircle : dxfCircles) {
		  		org.kabeja.dxf.helpers.Point centerPoint = dxfCircle.getCenterPoint();
		  		
				Point center = new Point(centerPoint.getX(), centerPoint.getY());
		  		double radius = dxfCircle.getRadius();
		  		
				Circle circle = new Circle(center, radius);
							
				archimedesCircles.add(circle);
			}
		}
		return archimedesCircles;
	}

}
