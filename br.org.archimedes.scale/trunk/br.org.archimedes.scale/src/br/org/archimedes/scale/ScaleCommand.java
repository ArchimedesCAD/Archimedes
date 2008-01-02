/*
 * Created on 13/09/2006
 */

package br.org.archimedes.scale;

import java.util.Set;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;


/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author marivb
 */
public class ScaleCommand implements UndoableCommand {

    private Set<Element> selection;

    private Point reference;

    private Double proportion;


    /**
     * Constructor.
     * 
     * @param selection
     *            The selection on which to perform the scale
     * @param reference
     *            The reference point
     * @param proportion
     *            The proportion to scale
     * @throws InvalidArgumentException
     *             In case the proportion is not positive
     */
    public ScaleCommand (Set<Element> selection, Point reference,
            Double proportion) throws NullArgumentException,
            InvalidArgumentException {

        if (proportion <= 0) {
            throw new InvalidArgumentException();
        }
        this.selection = selection;
        this.reference = reference;
        this.proportion = proportion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        scale(drawing, 1.0 / proportion);
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        scale(drawing, proportion);
    }

    /**
     * Performs scale on the selected elements
     * 
     * @param drawing
     *            The drawing on which the elements supposedly are
     * @param proportion
     *            The proportion of the scale
     * @throws NullArgumentException
     *             In case the drawing is null
     * @throws IllegalActionException
     *             In case some element is not on the drawing
     */
    private void scale (Drawing drawing, double proportion)
            throws NullArgumentException, IllegalActionException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        boolean shouldThrowIllegal = false;
        for (Element element : selection) {
            Layer layer = element.getLayer();
            if (layer != null && !layer.isLocked()
                    && drawing.contains(layer)
                    && layer.contains(element)) {
                element.scale(reference, proportion);
            }
            else {
                shouldThrowIllegal = true;
            }
        }

        if (shouldThrowIllegal) {
            // TODO Que mensagem que eu ponho??
            throw new IllegalActionException();
        }
    }
}
