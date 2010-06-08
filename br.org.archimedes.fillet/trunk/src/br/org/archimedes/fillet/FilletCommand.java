/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 05/05/2009, 14:33:42.<br>
 * It is part of br.org.archimedes.fillet on the br.org.archimedes.fillet project.<br>
 */

package br.org.archimedes.fillet;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.List;

/**
 * @author Luiz Real, Ricardo Sider
 */
public class FilletCommand implements UndoableCommand {

    private final Element firstElement;

    private final Point firstClick;

    private final Element secondElement;

    private final Point secondClick;

    private Filleter filleter;
    
    private double radius;

    private List<? extends UndoableCommand> commands;


    /**
     * @param firstElement
     *            The first element selected by the user
     * @param firstClick
     *            The point clicked by the user to select the first element
     * @param secondElement
     *            The second element selected by the user
     * @param secondClick
     *            The point clicked by the user to select the second element
     * @throws NullArgumentException
     *             if one of the parameters is null
     * @throws InvalidArgumentException
     *             if one of the points is not part of their elements
     */
    public FilletCommand (Element firstElement, Point firstClick, Element secondElement,
            Point secondClick, double radius) throws NullArgumentException, InvalidArgumentException {

        if (firstElement == null || secondElement == null || firstClick == null
                || secondClick == null) {
            throw new NullArgumentException();
        }

        if ( !firstElement.contains(firstClick) || !secondElement.contains(secondClick)) {
            throw new InvalidArgumentException();
        }

        this.firstElement = firstElement;
        this.firstClick = firstClick;
        this.secondElement = secondElement;
        this.secondClick = secondClick;

        commands = null;

        this.radius = radius;
        this.filleter = new DefaultFilleter(radius);
        
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (commands != null) {
            for (UndoableCommand cmd : commands) {
                cmd.undoIt(drawing);
            }
        }
        else {
            throw new IllegalActionException();
        }
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        commands = filleter.fillet(firstElement, firstClick, secondElement, secondClick);
        for (UndoableCommand cmd : commands) {
            cmd.doIt(drawing);
        }

    }

    /**
     * @param filleter
     *            The filleter to be used (if not the default)
     */
    public void setFilleter (Filleter filleter) {

        this.filleter = filleter;
    }

    public Point getFirstClick () {

        return firstClick;
    }

    public Point getSecondClick () {

        return secondClick;
    }

    public Element getFirstElement () {

        return firstElement;
    }

    public Element getSecondElement () {

        return secondElement;
    }

    @Override
    public boolean equals (Object obj) {

        if (obj instanceof FilletCommand) {
            FilletCommand otherCommand = (FilletCommand) obj;
            return getFirstElement().equals(otherCommand.getFirstElement())
                    && getSecondElement().equals(otherCommand.getSecondElement())
                    && getFirstClick().equals(otherCommand.getFirstClick())
                    && getSecondClick().equals(otherCommand.getSecondClick());
        }
        return false;
    }

    @Override
    public int hashCode () {

        // TODO implement and test this and the above
        return getFirstElement().hashCode();
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
