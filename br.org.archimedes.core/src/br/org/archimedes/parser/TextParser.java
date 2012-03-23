/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Fabricio S. Nascimento - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/08/16, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.interpreter.partese.
 * 
 * @author fabsn
 */
public class TextParser implements Parser {

    private String message;

    private boolean awaitConfirmation;


    /**
     * @param content
     */
    public TextParser () {

        message = null;
        awaitConfirmation = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
     */
    public String next (String message) throws InvalidParameterException {

        String returnValue = null;
        if (Utils.isPoint(message)) {
            returnValue = Messages.Text_confirmPoint;
            returnValue += " " + message; //$NON-NLS-1$
            awaitConfirmation = true;
            this.message = message;
        }
        else if (awaitConfirmation) {
            if (message.equalsIgnoreCase(Messages.Text_yes)) {
                awaitConfirmation = false;
            }
            else if (message.equalsIgnoreCase(Messages.Text_no)) {
                awaitConfirmation = false;
                this.message = null;
                returnValue = Messages.Text_iteration;
            }
        }
        else {
            this.message = message;
        }
        return returnValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#isDone()
     */
    public boolean isDone () {

        return (message != null && !awaitConfirmation);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
     */
    public Object getParameter () {

        return awaitConfirmation ? null : message;
    }
}
