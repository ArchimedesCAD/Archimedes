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

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.<br>
 * An "offset direction" is either a point or "+" or "-". We don't think any
 * other command might want to use this, but if they do, just rename the class!
 * 
 * @author andy
 */
public class DirectionParser implements Parser {

    private Object result;


    /**
     * Constructor.
     */
    public DirectionParser () {

        result = null;

    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;

        if (message == null) {
            throw new InvalidParameterException();
        }
        if (Utils.isPoint(message)) {
            result = (Point) Utils.getPointCoordinates(message);
        }
        else if (Utils.isReturn(message)) {
            result = br.org.archimedes.Utils.getWorkspace().getActualMousePosition();
        }
        else if (message.equals("+")) { //$NON-NLS-1$
            result = true;
        }
        else if (message.equals("-")) { //$NON-NLS-1$
            result = false;
        }
        else {
            throw new InvalidParameterException(
                    Messages.Direction_expectedDirection);
        }

        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return result != null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return result;
    }
}
