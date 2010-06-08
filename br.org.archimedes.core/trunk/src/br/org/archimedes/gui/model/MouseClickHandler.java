/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Gustavo Menezes - initial API and implementation<br>
 * Hugo Corbucci, Julien Renaut - later contributions<br>
 * <br>
 * This file was created on 2006/03/27, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.model on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Event;

import br.org.archimedes.Constant;
import br.org.archimedes.Utils;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.controller.commands.MacroCommand;
import br.org.archimedes.controller.commands.PanCommand;
import br.org.archimedes.controller.commands.RelativeZoomCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.gui.actions.SelectionCommand;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Vector;
import br.org.archimedes.rcp.extensionpoints.FactoryEPLoader;

/**
 * This class manipulate mouse click related events, and send them to its observers.<br>
 * Belongs to package br.org.archimedes.gui.model.
 * 
 * @author gigante
 */
public class MouseClickHandler extends Observable {

    private static MouseClickHandler instance;

    private Workspace workspace;

    private FactoryEPLoader loader;


    /**
     * Constructor.
     */
    private MouseClickHandler () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        loader = new FactoryEPLoader();
    }

    /**
     * This method receives the click from the current Canvas, and sends to the observers the
     * normalized point.
     * 
     * @param event
     *            The mouse event received.
     */
    public void receiveClick (MouseEvent event) {

        boolean isShiftSelected = event.stateMask == SWT.SHIFT;
        SelectionCommand.setShiftSelected(isShiftSelected);

        Point point = getNormalizedPoint(event);
        workspace.setMousePosition(point);
        point = workspace.getMousePosition();
        setChanged();
        notifyObservers(point);
    }

    /**
     * @param event
     *            The mouse event received
     * @return The point in model coordinates.
     */
    private Point getNormalizedPoint (MouseEvent event) {

        Point point;

        Canvas canvas = (Canvas) event.getSource();

        org.eclipse.swt.graphics.Rectangle rect = canvas.getClientArea();

        double x = (double) event.x;
        double y = (double) event.y;

        point = new Point(x - rect.width / 2, (rect.height - y) - rect.height / 2);

        try {
            point = workspace.screenToModel(point);
        }
        catch (NullArgumentException e) {
            // Should not happen.
            e.printStackTrace();
        }

        return point;
    }

    /**
     * @return the unique instance of the MouseClickHandler.
     */
    public static MouseClickHandler getInstance () {

        if (instance == null) {
            instance = new MouseClickHandler();
        }
        return instance;
    }

    /**
     * This method is called whenever the right mouse button is clicked over a drawing.
     */
    public void receiveRightClick () {

        setChanged();
        notifyObservers();
    }

    /**
     * This method is called whenever the mouse's wheel is rolled over a drawing.
     * 
     * @param event
     *            The mouse wheel event.
     */
    public void receiveMouseWheel (Event event) {

        double ratio = 1.0 + event.count / 3 * Constant.ZOOM_RATE;

        Rectangle rect = workspace.getWindowSize();

        double x = (double) event.x;
        double y = (double) event.y;

        Point screenPoint = new Point(x - rect.getWidth() / 2, ((rect.getHeight() - y) - rect
                .getHeight() / 2));

        Point modelPoint = null;
        Controller controller = br.org.archimedes.Utils.getController();
        try {
            workspace.setMousePosition(workspace.screenToModel(screenPoint));
            modelPoint = workspace.getMousePosition();

            Point viewport = workspace.getViewport();
            double zoom = 1 / (workspace.getCurrentZoom() * ratio);
            double xModel = zoom * screenPoint.getX() + viewport.getX();
            double yModel = zoom * screenPoint.getY() + viewport.getY();
            Point newModelPoint = new Point(xModel, yModel);
            Vector modelVector = new Vector(newModelPoint, modelPoint);

            List<UndoableCommand> commands = new ArrayList<UndoableCommand>();
            commands.add(new RelativeZoomCommand(ratio));
            if ( !newModelPoint.equals(modelPoint)) {
                commands.add(new PanCommand(viewport, viewport.addVector(modelVector)));
            }
            MacroCommand macro = new MacroCommand(commands);
            List<Command> list = new ArrayList<Command>();
            list.add(macro);

            controller.execute(list);
        }
        catch (NullArgumentException e) {
            // Should not reach this code.
            e.printStackTrace();
        }
        catch (NoActiveDrawingException e) {
            // Ignores it because no drawing is open
        }
        catch (IllegalActionException e) {
            // Ignores it because it's a max zoom
        }
    }

    /**
     * Receives a middle click and if the mouse is down, starts a pan command, otherwise cancels the
     * current pan command.
     * 
     * @param event
     *            The event that triggered this method
     */
    public void receiveMiddleClick (MouseEvent event) {

        // TODO find a better way to refer to the pan command
        String pan = "br.org.archimedes.pan";
        if ( !loader.getFatoryMap().containsKey(pan))
            return;

        boolean activatedPan = workspace.isMouseDown();
        if (activatedPan) {
            Utils.getInputController().receiveText(pan);
            Point point = getNormalizedPoint(event);
            Utils.getInputController().receiveText(point.getX() + ";" + point.getY());
        }
        else {
            // TODO might have a concurrency issue here.
            Utils.getInputController().cancelCurrentFactory();
        }
    }

    /**
     * This method receives the double click from the current Canvas.
     * 
     * @param event
     *            The mouve event.
     */
    public void receiveDoubleClick (MouseEvent event) {

        Controller controller = br.org.archimedes.Utils.getController();

        try {
            // achar o elemento que está abaixo do duplo clique
            Point normalizedPoint = getNormalizedPoint(event);
            Element element = controller.getElementUnder(normalizedPoint, Element.class);

            if (element != null) {
                // dado esse elemento, achar a factory que lida com
                // duplo clique nesse elemento
                SelectorFactory factory = loader.getDoubleClickFactoryFor(element);

                if (factory != null) {
                    // ativar a factory
                    InputController inputController = br.org.archimedes.Utils.getInputController();
                    inputController.receiveText(factory.getName());
                }

            }

        }
        catch (NoActiveDrawingException e) {
            e.printStackTrace();
        }
    }

}
