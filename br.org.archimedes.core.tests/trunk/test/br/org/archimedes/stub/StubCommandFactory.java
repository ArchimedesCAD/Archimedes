/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 2, 2009, 12:38:56 PM.<br>
 * It is part of br.org.archimedes.factories on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.stub;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

import java.util.Collections;
import java.util.List;


/**
 * Belongs to package br.org.archimedes.factories.
 *
 * @author "Hugo Corbucci"
 *
 */
public class StubCommandFactory implements CommandFactory {

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#begin()
     */
    public String begin () {

        return "begin";
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#cancel()
     */
    public String cancel () {

        return "cancel";
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper()
     */
    public void drawVisualHelper () {

    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        return Collections.emptyList();
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#getName()
     */
    public String getName () {

        return "stub";
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#getNextParser()
     */
    public Parser getNextParser () {

        return null;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isDone()
     */
    public boolean isDone () {

        return true;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#next(java.lang.Object)
     */
    public String next (Object parameter) throws InvalidParameterException {

        return null;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {
        return false;
    }
}
