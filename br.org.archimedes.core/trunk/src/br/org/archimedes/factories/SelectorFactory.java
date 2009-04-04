/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/09/06, 22:55:36, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.util.Set;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.parser.SimpleSelectionParser;

/**
 * Belongs to package br.org.archimedes.factories. NOTA: Factories that
 * will handle double clicks must extend SelectorFactory. After all, it makes
 * sense that the first step of a factory that will handle double clicks is a
 * selection... Otherwise in what should you be double clicking?
 * 
 * @author night
 */
public abstract class SelectorFactory implements CommandFactory {

    private Parser parser;

    private boolean done;


    public SelectorFactory () {

        parser = null;
        done = true;
    }

    public String begin () {

        String returnValue = null;
        done = false;
        try {
            Set<Element> selection = br.org.archimedes.Utils.getController()
                    .getCurrentSelectedElements();

            if (selection == null || selection.isEmpty()) {
                parser = new SimpleSelectionParser();
                returnValue = Messages.SelectorFactory_Select;
            }
            else {
                returnValue = next(selection);
            }
        }
        catch (NoActiveDrawingException e) {
            returnValue = cancel();
        }
        catch (InvalidParameterException e) {
            // Should not happen
            e.printStackTrace();
        }

        return returnValue;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public String next (Object parameter) throws InvalidParameterException {

        String message;
        if ( !isDone() && parameter != null) {
            Set<Element> selection;
            try {
                selection = (Set<Element>) parameter;
                message = finishFactory(selection);
                parser = null;
                done = true;
            }
            catch (Exception e) {
                selection = null;
                throw new InvalidParameterException();
            }
        }
        else {
            throw new InvalidParameterException();
        }
        return message;
    }

    /**
     * @param selection
     *            The selection used by the factory.
     * @return A nice message to the user.
     */
    protected abstract String finishFactory (Set<Element> selection)
            throws InvalidParameterException;

    public boolean isDone () {

        return done;
    }

    public String cancel () {

        parser = null;
        done = true;
        return getCancelMessage();
    }

    /**
     * @return The cancel message.
     */
    protected abstract String getCancelMessage ();

    public void drawVisualHelper () {

        // No visual helper
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        return parser;
    }
}
