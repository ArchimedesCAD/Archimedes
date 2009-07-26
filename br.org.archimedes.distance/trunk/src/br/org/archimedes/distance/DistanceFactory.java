/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Paulo L. Huaman - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/19, 13:10:50, by Paulo L. Huaman.<br>
 * It is part of package br.org.archimedes.distance on the br.org.archimedes.distance project.<br>
 */
package br.org.archimedes.distance;

import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pafhuaman
 *
 */
public class DistanceFactory extends TwoPointFactory {

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#completeCommand(br.org.archimedes.model.Point, br.org.archimedes.model.Point)
	 */
	@Override
	protected String completeCommand(Point p1, Point p2) {

        String result;
        try {
            result = Double.toString(Geometrics.calculateDistance(p1, p2));
        }
        catch (Exception e) {
            result = Messages.distanceError; //getBundle().getString("DistanceError");
        }

        return result;
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#drawVisualHelper(br.org.archimedes.model.Point, br.org.archimedes.model.Point)
	 */
	@Override
	protected void drawVisualHelper(Point start, Point end) {

        List<Point> points = new ArrayList<Point>();
        points.add(start);
        points.add(end);
        try {
            Utils.getOpenGLWrapper().drawFromModel(points);
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
        }
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#getCommands()
	 */
	@Override
	public List<Command> getCommands() {
		return null;
	}

	/* (non-Javadoc)
	 * @see br.org.archimedes.factories.TwoPointFactory#getName()
	 */
	@Override
	public String getName() {
		return "distance"; //$NON-NLS-1$
	}

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }

}
