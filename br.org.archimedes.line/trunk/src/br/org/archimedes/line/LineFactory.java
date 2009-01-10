/*
 * Created on 27/03/2006
 */

package br.org.archimedes.line;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.TwoPointFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.StringDecoratorParser;
import br.org.archimedes.undo.UndoCommand;

/**
 * Belongs to package com.tarantulus.archimedes.commands.
 */
public class LineFactory extends TwoPointFactory {

    private Command command;

    private List<Point> points;


    /**
     * Constructor.
     */
    public LineFactory () {

        super(false);
        points = new ArrayList<Point>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.TwoPointFactory#next(java.lang.Object)
     */
    public String begin () {

        points = new ArrayList<Point>();

        return super.begin();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.TwoPointFactory#next(java.lang.Object)
     */
    public String next (Object parameter) throws InvalidParameterException {

        String returnValue;
        if (Messages.undo.equals(parameter) && getP1() != null) {
            if (points.isEmpty()) {
                setP1(null);
            }
            else {
                points.remove(points.size() - 1);
                if (points.size() > 0) {
                    setP1(points.get(points.size() - 1));
                    command = new UndoCommand();
                }
                else {
                    setP1(null);
                }
            }

            returnValue = Messages.undone;
        }
        else {
            returnValue = super.next(parameter);
        }
        return returnValue;
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
            if (points.isEmpty()) {
                points.add(p1);
            }
            points.add(p2);

            Line newLine = new Line(p1, p2);
            command = new PutOrRemoveElementCommand(newLine, false);
            result = Messages.created;
        }
        catch (Exception e) {
            result = Messages.notCreated;
        }

        return result;
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
    protected void drawVisualHelper (Point start, Point end) {

        List<Point> points = new ArrayList<Point>();
        points.add(start);
        points.add(end);
        try {
            br.org.archimedes.Utils.getOpenGLWrapper().drawFromModel(points);
        }
        catch (NullArgumentException e) {
            // Shouldn't happen since the parent class handle it
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.factories.TwoPointFactory#getNextParser()
     */
    public Parser getNextParser () {

        Parser returnParser = super.getNextParser();
        if ( !points.isEmpty() && returnParser != null) {
            returnParser = new StringDecoratorParser(returnParser,
                    Messages.undo);
        }
        return returnParser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.TwoPointFactory#getName()
     */
    @Override
    public String getName () {

        return "line"; //$NON-NLS-1$
    }
}
