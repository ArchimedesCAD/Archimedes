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

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Luiz Real, Ricardo Sider
 */
public class FilletCommand implements UndoableCommand {

    private final Element firstElement;

    private final Point firstClick;

    private final Element secondElement;

    private final Point secondClick;

    private Filleter filleter;

    private MacroCommand macro;

    private boolean performedOnce;

    private Map<Element, Set<Element>> filletMap;


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
            Point secondClick) throws NullArgumentException, InvalidArgumentException {

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

        macro = null;
        performedOnce = false;
        filletMap = new HashMap<Element, Set<Element>>();

        // TODO set default filleter
    }

    /*
     * (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (macro != null) {
            macro.undoIt(drawing);
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

        if ( !performedOnce) {

            computeFillet(drawing);

            if (filletMap.keySet().size() == 0) {
                throw new IllegalActionException();
            }

            Set<Element> allResults = new HashSet<Element>();
            for (Element key : filletMap.keySet()) {
                Set<Element> extendResult = filletMap.get(key);
                allResults.addAll(extendResult);
            }
            buildMacro(filletMap.keySet(), allResults);
            performedOnce = true;

        }
        if (macro != null) {
            macro.doIt(drawing);
        }

    }

    /**
     * Computes the extend
     * 
     * @param drawing
     *            The base drawing
     * @param click
     *            A click point for the extend
     * @throws IllegalActionException
     *             In case no element was clicked
     * @throws NullArgumentException
     *             In case that the references of extending are null
     */
    private void computeFillet (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        Element toFillet = firstElement;
        Element key = null;
        boolean isInMap = false;

        if (filletMap.containsKey(toFillet)) {
            Set<Element> turnedTo = filletMap.get(toFillet);
            for (Element element : turnedTo) {
                try {
                    if (element.contains(firstClick)) {
                        key = toFillet;
                        toFillet = element;
                        isInMap = true;
                    }
                }
                catch (NullArgumentException e) {
                    // Should really not happen
                    e.printStackTrace();
                }
            }
        }

        if (key == null || isInMap) {

            filleter.fillet(firstElement, firstClick, secondElement, secondClick);

            Set<Element> turnedTo;
            if (isInMap) {
                turnedTo = filletMap.get(key);
            }
            else {
                turnedTo = new HashSet<Element>(Collections.singleton(toFillet));
                key = toFillet;
            }
            filletMap.put(key, turnedTo);
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

    /**
     * Build a macro-command to perform the necessary actions
     * 
     * @param toRemove
     *            The elements to be removed
     * @param toAdd
     *            The elements to be added
     */
    private void buildMacro (Set<Element> toRemove, Set<Element> toAdd) {

        try {
            UndoableCommand remove = new PutOrRemoveElementCommand(toRemove, true);
            UndoableCommand add = new PutOrRemoveElementCommand(toAdd, false);
            List<UndoableCommand> cmds = new ArrayList<UndoableCommand>();
            cmds.add(remove);
            cmds.add(add);
            macro = new MacroCommand(cmds);
        }
        catch (Exception e) {
            // Should not happen
            e.printStackTrace();
        }
    }

}
