/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Luiz C. Real - later contributions<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca.editor on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.gui.rca.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GLContext;

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.MouseClickHandler;
import br.org.archimedes.gui.model.MouseMoveHandler;
import br.org.archimedes.gui.model.VisualHelper;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.gui.rca.Activator;
import br.org.archimedes.gui.rca.InterpreterView;
import br.org.archimedes.model.Drawing;

public class DrawingEditor extends EditorPart implements Observer, ISelectionProvider {

    public static final String EDITOR_ID = "br.org.archimedes.gui.rca.editor.DrawingEditor"; //$NON-NLS-1$

    private static List<Cursor> cursors;

    private GLCanvas canvas;

    private OpenGLWrapper openGL;

    private VisualHelper visualHelper;

    private IViewPart view;

    private int currentCursor;

    private Collection<ISelectionChangedListener> selectionListeners = new LinkedList<ISelectionChangedListener>();


    /**
     * @return the currentCursor
     */
    public int getCurrentCursor () {

        return this.currentCursor;
    }

    /**
     * @param currentCursor
     *            the currentCursor to set
     */
    public void setCurrentCursor (int currentCursor) {

        this.currentCursor = currentCursor;
        canvas.setCursor(cursors.get(currentCursor));
    }

    /**
     * Creates all mouse cursors.
     * 
     * @param display
     *            The display to be used
     * @return The list containing all the cursors
     */
    private static List<Cursor> createCursors (Display display) {

        List<Cursor> cursors = new ArrayList<Cursor>();

        PaletteData paletteData = new PaletteData(new RGB[] {new RGB(0, 0, 0),
                new RGB(255, 255, 255)});
        ImageData sourceData = new ImageData(1, 1, 1, paletteData);
        sourceData.setPixels(0, 0, 1, new int[] {0}, 0);

        Cursor normalCursor = new Cursor(display, sourceData, 0, 0);
        cursors.add(normalCursor);
        cursors.add(createCursor("cursors/open_hand.png")); //$NON-NLS-1$
        cursors.add(createCursor("cursors/closed_hand.png")); //$NON-NLS-1$

        return cursors;
    }

    /**
     * @param path
     *            The path to the cursor image.
     * @return The created cursor or null if the image couldn't be found.
     */
    private static Cursor createCursor (String path) {

        ImageDescriptor imageDescriptor = Activator.getImageDescriptor(path); //$NON-NLS-1$
        Cursor openHandCursor = null;
        if (imageDescriptor != null) { // found the image
            ImageData openHand = imageDescriptor.getImageData();
            openHandCursor = new Cursor(Display.getCurrent(), openHand, 12, 12);
        }
        return openHandCursor;
    }

    @Override
    public void doSave (IProgressMonitor monitor) {

        // TODO Use this system to save the drawing
    }

    @Override
    public void doSaveAs () {

        // TODO Use this system to save the drawing
    }

    @Override
    public void init (IEditorSite site, IEditorInput input) throws PartInitException {

        if (cursors == null) {
            cursors = DrawingEditor.createCursors(site.getShell().getDisplay());
        }
        setSite(site);
        setInput(input);
        setPartName(input.getName());

        openGL = br.org.archimedes.Utils.getOpenGLWrapper();
        visualHelper = new VisualHelper(openGL, br.org.archimedes.Utils.getWorkspace(),
                br.org.archimedes.Utils.getInputController());

        getDrawing().addObserver(this);
        getSite().setSelectionProvider(this);

        getEditorSite().getWorkbenchWindow().getPartService().addPartListener(new IPartListener() {

            private IWorkbenchPart lastActivated;


            public void partActivated (IWorkbenchPart part) {

                if (part == DrawingEditor.this) {
                    System.out.println("Ativando: " //$NON-NLS-1$
                            + getDrawing().getTitle());
                    lastActivated = part;
                    br.org.archimedes.Utils.getInputController().setDrawing(getDrawing());
                }
            }

            public void partBroughtToTop (IWorkbenchPart part) {

                // Nothing to do
            }

            public void partClosed (IWorkbenchPart part) {

                br.org.archimedes.Utils.getInputController().setDrawing(null);
                partActivated(lastActivated);
            }

            public void partDeactivated (IWorkbenchPart part) {

                if (part == DrawingEditor.this) {
                    System.out.println("Desativando: " //$NON-NLS-1$
                            + getDrawing().getTitle());
                    lastActivated = null;
                }
            }

            public void partOpened (IWorkbenchPart part) {

                // Nothing to do
            }
        });
    }

    @Override
    public boolean isDirty () {

        return !getDrawing().isSaved();
    }

    @Override
    public boolean isSaveAsAllowed () {

        return true;
    }

    @Override
    public void createPartControl (Composite parent) {

        if (canvas == null) {
            Drawing drawing = getDrawing();

            GLData data = new GLData();
            data.doubleBuffer = true;
            canvas = new GLCanvas(parent, SWT.NO_BACKGROUND | SWT.NO_FOCUS, data);
            canvas.setCurrent();

            try {
                GLContext.useContext(canvas);
            }
            catch (LWJGLException e) {
                // This means trouble!
                e.printStackTrace();
            }

            try {
                openGL.addDrawingCanvas(drawing, canvas);
            }
            catch (NullArgumentException e) {
                // The drawing or the canvas are null
                e.printStackTrace();
            }
            addListeners();

            openGL.setCurrentCanvas(canvas);
            setCurrentCursor(Constant.NORMAL_CURSOR);
        }
    }

    /**
     * Adds the mouse listener, the mouse wheel listener, the mouse move listener and a paint
     * listener.
     */
    private void addListeners () {

        final MouseClickHandler mouseClickHandler = MouseClickHandler.getInstance();
        final MouseMoveHandler mouseMoveHandler = MouseMoveHandler.getInstance();
        canvas.addMouseListener(new MouseListener() {

            private Workspace workspace = br.org.archimedes.Utils.getWorkspace();


            public void mouseDoubleClick (MouseEvent event) {

                if (event.button == 1) {
                    mouseClickHandler.receiveDoubleClick(event);
                }
            }

            public void mouseDown (MouseEvent event) {

                if (event.button == 3) {
                    mouseClickHandler.receiveRightClick();
                }
                else {
                    workspace.setMouseDown(true);
                    if (event.button == 1) {
                        mouseClickHandler.receiveClick(event);
                    }
                    else if (event.button == 2) {
                        mouseClickHandler.receiveMiddleClick(event);
                    }
                }
            }

            public void mouseUp (MouseEvent event) {

                workspace.setMouseDown(false);
                if (event.button == 2) {
                    mouseClickHandler.receiveMiddleClick(event);
                }
            }

        });

        canvas.addListener(SWT.MouseWheel, new Listener() {

            public void handleEvent (Event event) {

                mouseClickHandler.receiveMouseWheel(event);
            }
        });

        canvas.addMouseMoveListener(new MouseMoveListener() {

            public void mouseMove (MouseEvent event) {

                mouseMoveHandler.receiveMouseMove(event);
                update();
            }

        });

        canvas.addPaintListener(new PaintListener() {

            public void paintControl (PaintEvent e) {

                update();
            }
        });
    }

    protected void update () {

        Drawing drawing = getDrawing();
        Drawing active = null;
        try {
            br.org.archimedes.Utils.getWorkspace().setViewport(drawing.getViewportPosition(),
                    drawing.getZoom());
            active = br.org.archimedes.Utils.getController().getActiveDrawing();
        }
        catch (NullArgumentException e) {
            // Should never reach this block
        }
        catch (NoActiveDrawingException e) {
            // Then the selected item is the active drawing
            return;
        }
        openGL.setCurrentDrawing(drawing);
        openGL.clear();
        drawing.draw(openGL);

        setPartName(getEditorInput().getName());

        if (drawing == active) {
            visualHelper.draw(currentCursor == Constant.NORMAL_CURSOR);
        }
        openGL.update();
    }

    private Drawing getDrawing () {

        return ((DrawingInput) getEditorInput()).getDrawing();
    }

    @Override
    public void setFocus () {

        br.org.archimedes.Utils.getInputController().setDrawing(getDrawing());
        if (view == null) {
            this.view = getSite().getPage().findView(InterpreterView.ID);
        }
        view.setFocus();
        update();
    }

    public void update (Observable arg0, Object arg1) {

        update();
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void addSelectionChangedListener (ISelectionChangedListener listener) {

        selectionListeners.add(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
     */
    public ISelection getSelection () {

        return new StructuredSelection(getDrawing());
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
     */
    public void removeSelectionChangedListener (ISelectionChangedListener listener) {

        selectionListeners.remove(listener);
    }

    /**
     * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
     */
    public void setSelection (ISelection selection) {

        // Ignores this
    }
}
