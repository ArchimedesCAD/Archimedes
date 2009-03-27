/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/06/15, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 */
public class ZoomParser implements Parser {

    private Object result;


    public ZoomParser () {

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
        else if (Utils.isDouble(message)) {
            result = Utils.getDouble(message);
        }
        else if (Utils.isPoint(message)) {
            result = Utils.getPointCoordinates(message);
        }
        else if (message.trim().equals("e")) { //$NON-NLS-1$
            result = "e"; //$NON-NLS-1$
        }
        else if (message.trim().equals("p")) { //$NON-NLS-1$
            result = "p"; //$NON-NLS-1$
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

        return (result != null);
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
