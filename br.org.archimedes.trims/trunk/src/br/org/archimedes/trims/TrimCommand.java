/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Jeferson R. Silva - initial API and implementation<br>
 * Hugo Corbucci, Jonas K. Hirata, Helton Rosa - later contributions<br>
 * Bruno da Hora, Wesley Seidel - later contributions<br>
 * <br>
 * This file was created on 2006/08/25, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.TrimManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.rcp.extensionpoints.TrimManagerEPLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
 */
public class TrimCommand implements UndoableCommand {

    private Collection<Element> references;

    private Collection<Point> cutPoints;

    private List<Point> clicks;

    private Map<Element, Set<Element>> trimMap;

    private MacroCommand macro;

    private boolean performedOnce;

    private TrimManager trimManager;

    private IntersectionManager intersectionManager;


    /**
     * @param references
     *            The references for this trim
     * @param points
     *            The points where a click occurred
     * @throws NullArgumentException
     *             If one of the arguments is null
     */
    public TrimCommand (Collection<Element> references, List<Point> clicks)
            throws NullArgumentException {

        if (references == null || clicks == null) {
            throw new NullArgumentException();
        }
        trimManager = new TrimManagerEPLoader().getTrimManager();
        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();
        this.clicks = clicks;
        macro = null;
        performedOnce = false;
        trimMap = new HashMap<Element, Set<Element>>();
        this.references = references;
    }

    /**
     * @param drawing
     *            drawing where the trim will be performed
     * @throws NullArgumentException
     *             if drawing is null
     * @throws IllegalActionException
     *             if trimMap is empty
     */
    public void doIt (Drawing drawing) throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if ( !performedOnce) {
            if (references.isEmpty()) {
                references.addAll(drawing.getUnlockedContents());
            }

            for (Point point : clicks) {
                computeTrim(drawing, point);
            }

            if (trimMap.keySet().size() == 0) {
                throw new IllegalActionException();
            }

            Set<Element> allResults = new HashSet<Element>();
            for (Element key : trimMap.keySet()) {
                Set<Element> trimResult = trimMap.get(key);
                allResults.addAll(trimResult);
                if (references.contains(key)) {
                    references.remove(key);
                    references.addAll(trimResult);
                }
            }
            buildMacro(trimMap.keySet(), allResults);
            performedOnce = true;
        }
        if (macro != null) {
            macro.doIt(drawing);
        }
    }

    /**
     * @param drawing
     *            drawing where the trim will be undone
     * @throws NullArgumentException
     *             if drawing or macro is null
     * @throws IllegalActionException
     *             if undoing is not allowed when called.
     */
    public void undoIt (Drawing drawing) throws IllegalActionException, NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (macro != null) {
            macro.undoIt(drawing);
        }
    }

    /**
     * Computes the trim
     * 
     * @param drawing
     *            The base drawing
     * @param click
     *            A click point for the trim
     * @throws IllegalActionException
     *             In case no element was clicked
     * @throws NullArgumentException
     *             In case that the references of trimming are null
     */
    protected void computeTrim (Drawing drawing, Point click) throws IllegalActionException,
            NullArgumentException {

        Element toTrim = getClickedElement(click);
        Element key = null;
        boolean isInMap = false;

        if (toTrim == null) {
            throw new IllegalActionException();
        }

        cutPoints = intersectionManager.getIntersectionsBetween(toTrim, references);

        if (trimMap.containsKey(toTrim)) {
            Set<Element> turnedTo = trimMap.get(toTrim);
            for (Element element : turnedTo) {
                try {
                    if (element.contains(click)) {
                        key = toTrim;
                        toTrim = element;
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

            Collection<Element> trimResult = trimManager.getTrimOf(toTrim, cutPoints, click);
            if ( !trimResult.isEmpty()) {
                Set<Element> turnedTo;
                if (isInMap) {
                    turnedTo = trimMap.get(key);
                    turnedTo.remove(toTrim);
                    turnedTo.addAll(trimResult);
                }
                else {
                    turnedTo = new HashSet<Element>(trimResult);
                    key = toTrim;
                }
                trimMap.put(key, turnedTo);
            }
        }
    }

    /**
     * @param click
     *            The click point
     * @return The clicked element if there was any, null otherwise.
     */
    private Element getClickedElement (Point click) {

        Element clickedElement = null;
        try {
            clickedElement = br.org.archimedes.Utils.getController().getElementUnder(click,
                    Element.class);
        }
        catch (NoActiveDrawingException e) {
            // Should not happen because I know there is a drawing
            e.printStackTrace();
        }

        return clickedElement;
    }

    /**
     * Build a macro-command to perform the necessary actions
     * 
     * @param toRemove
     *            The elements to be removed
     * @param toAdd
     *            The elements to be added
     */
    protected void buildMacro (Set<Element> toRemove, Set<Element> toAdd) {

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

    /**
     * Set the intersection manager to be used instead of the default. Useful for testing
     * 
     * @param intersectionManager
     *            The intersection manager to be used
     */
    public void setIntersectionManager (IntersectionManager intersectionManager) {

        this.intersectionManager = intersectionManager;
    }

    /**
     * Set the trim manager to be used instead of the default. Useful for testing
     * 
     * @param trimManager
     *            The trim manager to be used
     */
    public void setTrimManager (TrimManager trimManager) {

        this.trimManager = trimManager;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals (Object obj) {

        if (obj instanceof TrimCommand) {
            TrimCommand otherCommand = (TrimCommand) obj;
            boolean referencesAreEqual = this.references.containsAll(otherCommand.references)
                    && otherCommand.references.containsAll(this.references);
            boolean clicksAreEqual = this.clicks.containsAll(otherCommand.clicks)
                    && otherCommand.clicks.containsAll(this.clicks);
            return referencesAreEqual && clicksAreEqual;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.interfaces.UndoableCommand#canMergeWith(br.org.archimedes.interfaces.UndoableCommand)
     */
    public boolean canMergeWith (UndoableCommand command) {

        return false;
    }
}
