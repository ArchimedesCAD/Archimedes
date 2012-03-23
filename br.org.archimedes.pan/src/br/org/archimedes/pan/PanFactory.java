/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Julien Renaut - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/05/10, 10:10:26, by Julien Renaut.<br>
 * It is part of package br.org.archimedes.pan on the br.org.archimedes.pan project.<br>
 */
package br.org.archimedes.pan;

import br.org.archimedes.Constant;
import br.org.archimedes.controller.commands.PanCommand;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.MouseMoveHandler;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.parser.PointParser;
import br.org.archimedes.parser.ReturnDecoratorParser;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Belongs to package br.org.archimedes.pan.
 */
public class PanFactory implements CommandFactory, Observer {

    private Workspace workspace;

    private Point firstPoint;

    private Point previousViewport;

    private Point firstViewport;

    private boolean isActive;

    private boolean observing;

    private PanCommand command;

    private DrawingEditor activeDrawingEditor;


    public PanFactory () {

        workspace = br.org.archimedes.Utils.getWorkspace();
        isActive = false;
        observing = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#begin()
     */
    public String begin () {

        activeDrawingEditor = getEditor();
        activeDrawingEditor.setCurrentCursor(Constant.OPENHAND_CURSOR);

        isActive = true;
        firstViewport = workspace.getViewport();

        return Messages.ClickDrag;
    }

    /**
     * @return the current drawing editor or null if none (meaning tests or a
     *         wrong factory activation)
     */
    private DrawingEditor getEditor () {

        DrawingEditor drawingEditor = new DrawingEditor();
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null) {
            IEditorPart activeEditor = workbench
                    .getActiveWorkbenchWindow().getActivePage()
                    .getActiveEditor();
            if (activeEditor != null
                    && activeEditor.getClass() == DrawingEditor.class) {
                drawingEditor = (DrawingEditor) activeEditor;
            }
        }
        return drawingEditor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#next(java.lang.Object)
     */
    public String next (Object parameter) throws InvalidParameterException {

        if (parameter == null) {
            cancel();
        }
        else {
            try {
                Point point = tryGetPoint(parameter);
                firstPoint = workspace.modelToScreen(point);
                if ( !observing) {
                    MouseMoveHandler mouseMove = MouseMoveHandler.getInstance();
                    mouseMove.addObserver(this);
                    observing = true;
                }
                previousViewport = workspace.getViewport();

                activeDrawingEditor
                        .setCurrentCursor(Constant.CLOSEDHAND_CURSOR);
            }
            catch (NullArgumentException e) {
                // Should not reach this block
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param parameter
     *            The parameter that should be a point
     * @return The parameter as a Point
     * @throws InvalidParameterException
     *             Thrown if parameter is not a Point
     */
    private Point tryGetPoint (Object parameter)
            throws InvalidParameterException {

        Point point = null;
        try {
            point = (Point) parameter;
        }
        catch (ClassCastException e) {
            throw new InvalidParameterException(Messages.ClickDrag);
        }
        return point;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#isDone()
     */
    public boolean isDone () {

        return !isActive;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#cancel()
     */
    public String cancel () {

        try {
            command = new PanCommand(firstViewport, workspace.getViewport());
        }
        catch (NullArgumentException e) {
            // Should never happen
            e.printStackTrace();
        }
        catch (IllegalActionException e) {
            // May happen
        }
        activeDrawingEditor.setCurrentCursor(Constant.NORMAL_CURSOR);

        isActive = false;
        previousViewport = null;
        firstViewport = null;
        firstPoint = null;
        MouseMoveHandler.getInstance().deleteObserver(this);
        observing = false;
        return Messages.PanCancel;
    }

    /**
     * @see br.org.archimedes.factories.CommandFactory#drawVisualHelper()
     */
    public void drawVisualHelper () {

        // No visual helper
    }

    public void update (Observable mouseMoveHandler, Object pointToBe) {

        Point point = null;
        try {
            point = workspace.modelToScreen((Point) pointToBe);
        }
        catch (NullArgumentException e) {
            // Should not reach this block
            e.printStackTrace();
        }

        if (point != null && isActive && workspace.isMouseDown()
                && previousViewport != null) {
            try {
                Vector vector = new Vector(point, firstPoint);
                Vector scaledVector = workspace.screenToModel(vector);
                Point nextViewport = previousViewport.addVector(scaledVector);
                workspace.setViewport(nextViewport);
                activeDrawingEditor
                        .setCurrentCursor(Constant.CLOSEDHAND_CURSOR);
            }
            catch (NullArgumentException e) {
                // Should not throw this exception
                e.printStackTrace();
            }
            catch (NoActiveDrawingException e) {
                // Should not throw this exception
                e.printStackTrace();
            }
        }
        else if (isActive && !workspace.isMouseDown()) {
            activeDrawingEditor.setCurrentCursor(Constant.OPENHAND_CURSOR);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        Parser parser = null;

        if (isActive) {

            parser = new ReturnDecoratorParser(new PointParser());
        }

        return parser;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.factories.CommandFactory#getCommands()
     */
    public List<Command> getCommands () {

        List<Command> cmds = new ArrayList<Command>();
        if (command != null) {
            cmds.add(command);
            command = null;
        }
        return cmds;
    }

    public String getName () {

        return "pan"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
