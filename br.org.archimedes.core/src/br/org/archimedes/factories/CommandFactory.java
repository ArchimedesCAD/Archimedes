/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/16, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.factories;

import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author night
 */
public interface CommandFactory {

    /**
     * Method called to start a command factory
     * 
     * @return Some message to be returned to the user or null if nothing should
     *         be returned.
     */
    public String begin ();

    /**
     * Method called to execute the next factory iteration.
     * 
     * @param parameter
     *            the next parameter to the factory
     * @return Some message to be returned to the user or null if nothing should
     *         be returned
     * @throws InvalidParameterException
     *             thrown if the parameter is invalid.
     */
    public String next (Object parameter) throws InvalidParameterException;

    /**
     * @return true if the factory stopped producing commands, false otherwise.
     */
    public boolean isDone ();

    /**
     * Cancels the factory.
     * 
     * @return The message to be to returned to the user when the factory is
     *         canceled.
     */
    public String cancel ();

    /**
     * @return The next parser to be used by the interpreter.
     */
    public Parser getNextParser ();

    /**
     * Draws the visual helper for the command.
     * 
     * @param writer
     *            The writer where to draw the visual helper
     */
    public void drawVisualHelper ();

    /**
     * @return A list of generated commands if some was generated in the last
     *         iteration or null.
     */
    public List<Command> getCommands ();

    /**
     * @return The name of the factory to be display to the user.
     */
    public String getName ();

    /**
     * @return True if this factory transforms elements; false otherwise
     */
    public boolean isTransformFactory ();
}
