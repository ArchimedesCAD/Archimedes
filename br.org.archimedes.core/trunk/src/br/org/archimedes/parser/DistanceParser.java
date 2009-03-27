/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Anderson V. Siqueira - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import br.org.archimedes.Geometrics;
import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 * 
 * @author andy
 */
public class DistanceParser implements Parser {

    private Double distance;

    private Point p1;
    
    private boolean done;


    /**
     * Constructor.
     */
    public DistanceParser () {
        this(null);
    }
    
    public DistanceParser (Point p1) {

        distance = null;
        this.p1 = p1;
        done = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;

        Workspace workspace = Utils.getWorkspace();

        if (Utils.isPoint(message)) {
            Point point = (Point) Utils.getPointCoordinates(message);
            if (p1 == null) {
                p1 = point;
                workspace.setPerpendicularGripReferencePoint(p1);
            }
            else {
                try {
                    distance = Geometrics.calculateDistance(p1, point);
                    done = true;
                }
                catch (NullArgumentException e) {
                    // Should not happen.
                    e.printStackTrace();
                }
            }
        }
        else if (Utils.isDouble(message)) {
            distance = Math.abs((Double) Utils.getDouble(message));
            done = true;
        }
        else if (Utils.isReturn(message)) {
            distance = null;
            done = true;
        }
        else {
            throw new InvalidParameterException();
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return done;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return distance;
    }
}
