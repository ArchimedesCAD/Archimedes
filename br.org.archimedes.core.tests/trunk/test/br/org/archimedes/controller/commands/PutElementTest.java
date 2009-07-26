/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/18, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller.commands on the br.org.archimedes.core.tests
 * project.<br>
 */

package br.org.archimedes.controller.commands;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.stub.StubElement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author Hugo Corbucci
 */
public class PutElementTest {

    private Drawing drawing;

    private Element element;

    private PutOrRemoveElementCommand putElement;


    @Before
    public void setUp () throws Exception {

        drawing = new Drawing("Drawing");
        element = new StubElement();
        putElement = new PutOrRemoveElementCommand(element, false);
    }

    @After
    public void tearDown () {

        drawing = null;
    }

    /*
     * Test method for
     * 'br.org.archimedes.model.commands.PutElementCommand.PutElementCommand(Element)'
     */
    @Test(expected = NullArgumentException.class)
    public void testPutElementCommandFailsWithNull () throws NullArgumentException {

        Element element = null;
        new PutOrRemoveElementCommand(element, false);
    }

    @Test
    public void canCreatePutElementWithValidElement () throws Exception {

        new PutOrRemoveElementCommand(new StubElement(), false);
    }

    @Test(expected = NullArgumentException.class)
    public void doItWithNullDrawingThrowsException () throws Exception {

        putElement.doIt(null);
    }

    /*
     * Test method for 'br.org.archimedes.model.commands.PutElementCommand.doIt(Drawing)'
     */
    @Test
    public void testDoIt () throws Exception {

        putElement.doIt(drawing);
        assertTrue("The current layer should contain the element.", drawing.getCurrentLayer()
                .contains(element));
    }

    @Test(expected = IllegalActionException.class)
    public void doingItOnceAlreadyDoneThrowsException () throws Exception {

        putElement.doIt(drawing);
        putElement.doIt(drawing);
    }

    @Test(expected = IllegalActionException.class)
    public void undoingItIfNeverDoneShouldThrowException () throws Exception {

        putElement.undoIt(drawing);
    }

    @Test(expected = IllegalActionException.class)
    public void undoingItAlreadyUndoneShouldThrowException () throws Exception {

        putElement.doIt(drawing);
        putElement.undoIt(drawing);

        putElement.undoIt(drawing);
    }

    @Test(expected = NullArgumentException.class)
    public void undoingItOnNullDrawingShouldThrowException () throws Exception {

        putElement.doIt(drawing);
        putElement.undoIt(null);
    }

    /*
     * Test method for 'br.org.archimedes.model.commands.PutElementCommand.undoIt(Drawing)'
     */
    @Test
    public void testUndoIt () throws Exception {

        putElement.doIt(drawing);
        putElement.undoIt(drawing);

        assertFalse("The drawing should not contain this element", drawing.getCurrentLayer()
                .contains(element));
    }
    
    @Test
    public void putsOnLayerOfTheElement () throws Exception {

        Layer layer = new Layer(new Color(100,100,100), "", LineStyle.CONTINUOUS, 1);
        drawing.addLayer(layer);
        element.setLayer(layer);
        putElement.doIt(drawing);
        
        assertEquals(layer, element.getLayer());
    }
    
    
    @Test
    public void putsOnCurrentLayerIfElementHasNoLayer () throws Exception {

        putElement.doIt(drawing);
        
        assertEquals(drawing.getCurrentLayer(), element.getLayer());
    }
    
    @Test
    public void addsLayerAndPutsElementInItIfDrawingDoesntHaveLayer () throws Exception {

        Layer layer = new Layer(new Color(100,100,100), "", LineStyle.CONTINUOUS, 1);
        element.setLayer(layer);
        putElement.doIt(drawing);
        
        assertEquals(layer, element.getLayer());
    }
    
    @Test
    public void putsEachElementInItsLayer () throws Exception {

        Collection<Element> elements = new LinkedList<Element>();
        StubElement noLayer = new StubElement();
        elements.add(noLayer);
        
        StubElement existingLayer = new StubElement();
        Layer layer = new Layer(new Color(10,10,10), "layer", LineStyle.CONTINUOUS, 1);
        existingLayer.setLayer(layer);
        drawing.addLayer(layer);
        elements.add(existingLayer);
        
        StubElement newLayer = new StubElement();
        Layer otherLayer = new Layer(new Color(0,0,100), "other layer", LineStyle.STIPPED, 1);
        newLayer.setLayer(otherLayer);
        elements.add(newLayer);
        
        StubElement sameNamedLayer = new StubElement();
        Layer sameNameLayer = new Layer(new Color(0,100,0), "layer", LineStyle.STIPPED, 1);
        sameNamedLayer.setLayer(sameNameLayer);
        elements.add(sameNamedLayer);
        
        putElement = new PutOrRemoveElementCommand(elements, false);
        putElement.doIt(drawing);
        
        assertEquals(drawing.getCurrentLayer(), noLayer.getLayer());
        assertEquals(layer, existingLayer.getLayer());
        assertEquals(otherLayer, newLayer.getLayer());
        assertEquals(layer, sameNamedLayer.getLayer());
    }
}
