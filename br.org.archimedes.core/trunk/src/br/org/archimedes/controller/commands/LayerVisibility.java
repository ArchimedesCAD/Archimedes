/*
 * Created on 27/10/2006
 */

package br.org.archimedes.controller.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;


/**
 * Belongs to package br.org.archimedes.controller.commands.
 * 
 * @author night
 */
public class LayerVisibility implements UndoableCommand {

    private Map<String, Layer> layers;

    private boolean isLayOff;


    /**
     * Constructor.<br>
     * Constructs a command that is a LayOn.
     */
    public LayerVisibility () {

        isLayOff = false;
    }

    /**
     * Constructor.<br>
     * Constructs a command that is a LayOff.
     * 
     * @param elements
     *            The elements whose layers must become invisible
     * @throws NullArgumentException
     *             thrown if the collection is null
     * @throws InvalidArgumentException
     *             thrown if the collection is empty
     */
    public LayerVisibility (Collection<Element> elements)
            throws NullArgumentException, InvalidArgumentException {

        if (elements == null) {
            throw new NullArgumentException();
        }
        if (elements.isEmpty()) {
            throw new InvalidArgumentException();
        }
        
        layers = new HashMap<String, Layer>();
        for (Element element : elements) {
            Layer layer = element.getLayer();
            if ( !layers.containsKey(layer.getName())) {
                layers.put(layer.getName(), layer);
            }
        }
        isLayOff = true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }

        if (layers != null) {
            Map<String, Layer> validLayers = drawing.getLayerMap();
            if ( !validLayers.values().containsAll(layers.values())) {
                throw new IllegalActionException();
            }
        }

        setLayersVisibility(drawing, !isLayOff);
    }

    /**
     * Changes the layers visibility according to the specified boolean.
     * 
     * @param drawing
     *            The drawing in which the layers should be changed
     * @param visible
     *            true if the layers are to be visible, false otherwise.
     */
    private void setLayersVisibility (Drawing drawing, boolean visible) {

        if (layers == null) {
            layers = new HashMap<String, Layer>();
            for (Layer layer : drawing.getLayerMap().values()) {
                if ( !layer.isVisible()) {
                    layers.put(layer.getName(), layer);
                }
            }
        }

        for (Layer layer : layers.values()) {
            layer.setVisible(visible);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.controller.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
     */
    public void undoIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        if (drawing == null) {
            throw new NullArgumentException();
        }
        if (layers == null) {
            throw new IllegalActionException();
        }

        setLayersVisibility(drawing, isLayOff);
    }

}
