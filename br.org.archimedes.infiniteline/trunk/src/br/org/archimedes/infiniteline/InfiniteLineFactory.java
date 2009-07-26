/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/27, 22:29:49, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.infiniteline on the br.org.archimedes.infiniteline project.<br>
 */
package br.org.archimedes.infiniteline;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.VectorParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Belongs to package br.org.archimedes.infiniteline.
 */
public class InfiniteLineFactory extends TwoPointFactory {

    private Command command;


    /**
     * Constructor.
     */
    public InfiniteLineFactory () {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.TwoPointCommandFactory#completeCommand(com.tarantulus.archimedes.model.Point,
     *      com.tarantulus.archimedes.model.Point)
     */
    @Override
    protected String completeCommand (Point p1, Point p2) {

        String result;
        try {
            InfiniteLine newLine = new InfiniteLine(p1, p2);
            command = new PutOrRemoveElementCommand(newLine, false);
            result = Messages.Created;
        }
        catch (Exception e) {
            result = Messages.NotCreated;
        }

        return result;
    }

    @Override
    public String cancel () {

        return super.cancel();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = null;

        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            command = null;
        }

        return cmds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.TwoPointFactory#drawVisualHelper(com.tarantulus.archimedes.model.writers.Writer,
     *      com.tarantulus.archimedes.model.Point,
     *      com.tarantulus.archimedes.model.Point)
     */
    @Override
    protected void drawVisualHelper (Point start, Point end) {

        try {
            InfiniteLine xline = new InfiniteLine(start.getX(), start.getY(),
                    end.getX(), end.getY());
            xline.draw(br.org.archimedes.Utils.getOpenGLWrapper());
        }
        catch (InvalidArgumentException e) {
            // Draw nothing
        }
    }

    public String getName () {

        return "infiniteline"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.commands.Command#getNextParser()
     */
    @Override
    public Parser getNextParser () {

        Parser returnParser = null;
        if ( !isDone()) {
            if (getP1() == null) {
                returnParser = new PointParser();
            }
            else {
                returnParser = new VectorParser(getP1(), false);
            }
        }
        return returnParser;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return false;
    }
}
