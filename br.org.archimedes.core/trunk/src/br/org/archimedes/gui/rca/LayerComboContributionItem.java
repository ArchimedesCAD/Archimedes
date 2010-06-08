/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Paulo L. Huaman - later contributions<br>
 * <br>
 * This file was created on 2007/04/09, 08:40:07, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.gui.rca;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.menus.WorkbenchWindowControlContribution;

import br.org.archimedes.Messages;
import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.rca.editor.DrawingInput;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Layer;

/**
 * Belongs to package br.org.archimedes.gui.rca.
 * 
 * @author Hugo Corbucci
 */
public class LayerComboContributionItem extends WorkbenchWindowControlContribution implements
        IPartListener, Observer {

    /**
     * Belongs to package br.org.archimedes.gui.rca.
     * 
     * @author "Hugo Corbucci"
     */
    private final class ComboKeyListener implements KeyListener {

        public void keyPressed (KeyEvent e) {

            forceFocusInterpreter();
        }

        public void keyReleased (KeyEvent e) {

            forceFocusInterpreter();
        }
    }

    /**
     * Belongs to package br.org.archimedes.gui.rca.
     * 
     * @author "Hugo Corbucci"
     */
    private final class ComboFocusListener implements FocusListener {

        public void focusGained (FocusEvent e) {

            if (System.getProperty("os.name").indexOf("Win") < 0) { //$NON-NLS-1$ //$NON-NLS-2$
                // So that Non windows machines always have the focus on the
                // interpreter since they can work like that
                forceFocusInterpreter();
            }
        }

        public void focusLost (FocusEvent e) {

            forceFocusInterpreter();
        }
    }

    /**
     * Belongs to package br.org.archimedes.gui.rca.
     * 
     * @author "Hugo Corbucci"
     */
    private final class ComboSelectionListener implements SelectionListener {

        private final Controller controller;


        /**
         * Constructor.
         * 
         * @param controller
         */
        private ComboSelectionListener (Controller controller) {

            this.controller = controller;
        }

        public void widgetDefaultSelected (SelectionEvent e) {

            forceFocusInterpreter();
        }

        public void widgetSelected (SelectionEvent e) {

            try {
                Drawing activeDrawing = this.controller.getActiveDrawing();
                Combo combo = (Combo) e.widget;
                int selectionIndex = combo.getSelectionIndex();
                List<String> layers = activeDrawing.getLayerNames();
                Map<String, Layer> layerMap = activeDrawing.getLayerMap();
                String selectedLayerName = layers.get(selectionIndex);
                Layer layer = layerMap.get(selectedLayerName);

                if ( !layer.isLocked()) {
                    activeDrawing.setCurrentLayer(selectionIndex);
                }
                else {
                    String currentLayerName = activeDrawing.getCurrentLayer().getName();
                    int currentIndex = layers.indexOf(currentLayerName);

                    combo.select(currentIndex);
                    br.org.archimedes.Utils.getInputController().printInInterpreter(
                            Messages.LayerComboContributionItem_LayerLocked);
                }

                forceFocusInterpreter();
            }
            catch (NoActiveDrawingException e1) {
                deactivateCombo();
            }
            catch (IllegalActionException e1) {
                // Should never happen
                e1.printStackTrace();
            }
        }
    }


    private Combo layersCombo;

    private Drawing currentDrawing;


    /**
     * Forces the focus to the interprter view
     */
    protected void forceFocusInterpreter () {

        IWorkbenchPage activePage = getWorkbenchWindow().getActivePage();
        IViewPart part = activePage.findView(InterpreterView.ID);
        part.setFocus();
    }

    /**
     * @param drawing
     *            The drawing that contains the layers that should populate the combo
     */
    private void setObservedDrawing (Drawing drawing) {

        if (currentDrawing != drawing) {
            currentDrawing = drawing;
            drawing.addObserver(this);
            populate(drawing);
        }
    }

    /**
     * @param drawing
     */
    private void populate (Drawing drawing) {

        layersCombo.removeAll();
        layersCombo.setText(""); //$NON-NLS-1$
        List<String> layerNames = drawing.getLayerNames();
        int i = 0;
        boolean done = false;
        for (String layerName : layerNames) {
            layersCombo.add(layerName);
            if ( !done && drawing.getCurrentLayer().getName().equals(layerName)) {
                layersCombo.select(i);
                done = true;
            }
            i++;
        }
        layersCombo.setEnabled(true);
        // FIXME Needs resizing once populated
    }

    /**
     * Deactivates the combo
     */
    private void deactivateCombo () {

        if (currentDrawing != null) {
            currentDrawing.deleteObserver(this);
        }
        currentDrawing = null;
        layersCombo.removeAll();
        layersCombo.setText(Messages.LayerComboContributionItem_NoDrawing);
        layersCombo.setEnabled(false);
    }

    /**
     * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partActivated (IWorkbenchPart part) {

        if (part != null && part.getClass() == DrawingEditor.class) {
            DrawingEditor editor = (DrawingEditor) part;
            DrawingInput editorInput = (DrawingInput) editor.getEditorInput();
            Drawing drawing = editorInput.getDrawing();
            setObservedDrawing(drawing);
            getParent().update(true);
        }
    }

    /**
     * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
     */
    public void partBroughtToTop (IWorkbenchPart part) {

        // What mather is being activated
    }

    /**
     * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
     */
    public void partClosed (IWorkbenchPart part) {

        if (part != null && part.getClass() == DrawingEditor.class) {
            deactivateCombo();
        }
    }

    /**
     * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
     */
    public void partDeactivated (IWorkbenchPart part) {

        // Nothing to do when a part is deactivated (because another one will be
        // activated)
    }

    /**
     * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
     */
    public void partOpened (IWorkbenchPart part) {

        // If a part is open but not active, I don't care
    }

    /**
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable arg0, Object arg1) {

        if (arg1 != null && arg0 == currentDrawing && arg1.getClass() == Layer.class) {
            populate(currentDrawing);
            getParent().update(true);
        }
    }

    /*
     * (non-Javadoc)
     * @see
     * org.eclipse.jface.action.ControlContribution#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createControl (Composite parent) {

        final Controller controller = br.org.archimedes.Utils.getController();
        Composite top = new Composite(parent, SWT.NONE);
        GridLayoutFactory.fillDefaults().applyTo(top);
        layersCombo = new Combo(top, SWT.READ_ONLY);
        layersCombo.addSelectionListener(new ComboSelectionListener(controller));
        layersCombo.addFocusListener(new ComboFocusListener());
        layersCombo.addKeyListener(new ComboKeyListener());

        try {
            Drawing activeDrawing = controller.getActiveDrawing();
            setObservedDrawing(activeDrawing);
        }
        catch (NoActiveDrawingException e) {
            deactivateCombo();
        }

        GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, false).applyTo(
                layersCombo);

        IWorkbenchWindow window = getWorkbenchWindow();
        window.addPageListener(new IPageListener() {

            public void pageActivated (IWorkbenchPage page) {

                // I don't care. I will get the part events.
            }

            public void pageClosed (IWorkbenchPage page) {

                page.removePartListener(LayerComboContributionItem.this);
            }

            public void pageOpened (IWorkbenchPage page) {

                page.addPartListener(LayerComboContributionItem.this);
            }
        });
        return top;
    }
}
