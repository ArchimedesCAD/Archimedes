/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * "Hugo Corbucci" - initial API and implementation<br>
 * <br>
 * This file was created on Apr 2, 2009, 12:41:36 PM.<br>
 * It is part of br.org.archimedes.controller on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.controller;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.model.Drawing;

/**
 * Belongs to package br.org.archimedes.controller.
 * 
 * @author "Hugo Corbucci"
 */
public class StubState extends InputState {

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#cancel()
     */
    @Override
    public String cancel () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#changedDrawing(br.org.archimedes.model.Drawing)
     */
    @Override
    public InputState changedDrawing (Drawing currentDrawing) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getNext()
     */
    @Override
    public InputState getNext () {

        return null;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#nextShouldHandle()
     */
    @Override
    public boolean nextShouldHandle () {

        return false;
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#receiveText(java.lang.String)
     */
    @Override
    public String receiveText (String text) {

        return null;
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.controller.InputState#setCurrentFactory(br.org.archimedes.factories.
     * CommandFactory)
     */
    @Override
    protected String setCurrentFactory (CommandFactory factory) {

        return null;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.controller.InputState#getContextId()
     */
    @Override
    public String getContextId () {

        return null;
    }
}
