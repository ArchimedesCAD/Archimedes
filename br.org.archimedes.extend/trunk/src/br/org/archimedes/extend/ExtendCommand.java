/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno da Hora, Kenzo Yamada - initial API and implementation<br>
 * Bruno da Hora, Wesley Seidel - later contributions<br>
 * <br>
 * This file was created on 2009/04/28, 11:00:00, by Bruno da Hora.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extend;

import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.ExtendManagerEPLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Bruno da Hora, Kenzo Yamada
 */
public class ExtendCommand implements UndoableCommand {

    private Collection<Element> references;

    private HashMap<Point, Element> elementsToExtend;

    private Map<Element, Set<Element>> extendMap;

    private MacroCommand macro;

    private boolean performedOnce;

    private ExtendManager extendManager;

    private List<Point> points;

    private Element extendResult;


    /**
     * @param references
     *            The references for this extend
     * @param pointstoelements
     *            The points where a click occurred
     */
    public ExtendCommand (Collection<Element> references, HashMap<Point, Element> elementsToExtend,
            List<Point> points) {

        extendManager = new ExtendManagerEPLoader().getExtendManager();
        this.elementsToExtend = elementsToExtend;
        this.points = points;
        macro = null;
        performedOnce = false;
        extendMap = new HashMap<Element, Set<Element>>();
        this.references = references;
    }

    /**
     * @param drawing
     *            drawing where the extend will be performed
     * @throws NullArgumentException
     *             if drawing is null
     * @throws IllegalActionException
     *             if extendMap is empty
     */
    public void doIt (Drawing drawing) throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if ( !performedOnce) {
            if (references.isEmpty()) {
                references.addAll(drawing.getUnlockedContents());
            }

            for (Point point : points) {
                computeExtend(drawing, point);
            }

            if (extendMap.keySet().size() == 0) {
                throw new IllegalActionException();
            }

            Set<Element> allResults = new HashSet<Element>();
            for (Element key : extendMap.keySet()) {
                Set<Element> extendResult = extendMap.get(key);
                allResults.addAll(extendResult);
                if (references.contains(key)) {
                    references.remove(key);
                    references.addAll(extendResult);
                }
            }
            buildMacro(extendMap.keySet(), allResults);
            performedOnce = true;
        }
        if (macro != null) {
            macro.doIt(drawing);
        }
    }

    /**
     * @param drawing
     *            drawing where the extend will be undone
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
    private void computeExtend (Drawing drawing, Point click) throws IllegalActionException,
            NullArgumentException {

        Element toExtend = elementsToExtend.get(click);
        Element key = null;
        boolean isInMap = false;

        if (toExtend == null) {
            throw new IllegalActionException();
        }

        if (extendMap.containsKey(toExtend)) {
            Set<Element> turnedTo = extendMap.get(toExtend);
            for (Element element : turnedTo) {
                try {
                    if (element.contains(click)) {
                        key = toExtend;
                        toExtend = element;
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

            extendResult = extendManager.extend(toExtend, references, click);

            Set<Element> turnedTo;
            if (isInMap) {
                turnedTo = extendMap.get(key);
                turnedTo.remove(toExtend);
                turnedTo.addAll(Collections.singleton(extendResult));
            }
            else {
                turnedTo = new HashSet<Element>(Collections.singleton(extendResult));
                key = toExtend;
            }
            extendMap.put(key, turnedTo);
        }
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
