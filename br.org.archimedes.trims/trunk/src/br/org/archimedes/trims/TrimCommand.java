/*
 * Created on 25/08/2006
 */

package br.org.archimedes.trims;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Trimmable;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
 */
public class TrimCommand implements UndoableCommand {

    private Collection<Element> references;

    private List<Point> points;

    private Map<Element, Set<Element>> trimMap;

    private MacroCommand macro;

    private boolean performedOnce;


    /**
     * @param references
     *            The references for this trim
     * @param points
     *            The points where a click ocurred
     */
    public TrimCommand (Collection<Element> references, List<Point> points) {

        this.points = points;
        macro = null;
        performedOnce = false;
        trimMap = new HashMap<Element, Set<Element>>();
        this.references = references;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws NullArgumentException,
            IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if ( !performedOnce) {
            if (references.isEmpty()) {
                references.addAll(drawing.getUnlockedContents());
            }

            for (Point point : points) {
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

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

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
     * 			   In case that the references of trimming are null
     */
    private void computeTrim (Drawing drawing, Point click)
            throws IllegalActionException, NullArgumentException {

        Element toTrim = getClickedElement(click);
        Element key = null;
        boolean isInMap = false;

        if (toTrim == null) {
            throw new IllegalActionException();
        }

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
            Collection<Element> trimResult = Utils.getTrimManager().getTrimOf(toTrim, references, click);
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
     * @return The clicked element if there was any and it is Trimmable, null
     *         otherwise.
     */
    private Element getClickedElement (Point click) {

        Element clickedElement = null;
        try {
            clickedElement = Controller.getInstance().getElementUnder(click,
                    Trimmable.class);
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
    private void buildMacro (Set<Element> toRemove, Set<Element> toAdd) {

        try {
            UndoableCommand remove = new PutOrRemoveElementCommand(toRemove,
                    true);
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
