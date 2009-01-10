/*
 * Created on 27/03/2006
 */

package br.org.archimedes.infiniteline;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.VectorParser;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
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
                returnParser = new VectorParser(getP1(), 1.0, false);
            }
        }
        return returnParser;
    }
}
