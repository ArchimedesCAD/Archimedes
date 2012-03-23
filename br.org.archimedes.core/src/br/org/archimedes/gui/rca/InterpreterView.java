/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/01/19, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.rca on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.rca;

import br.org.archimedes.Utils;
import br.org.archimedes.controller.InputController;
import br.org.archimedes.gui.actions.SelectionCommand;
import br.org.archimedes.gui.model.MouseClickHandler;
import br.org.archimedes.gui.model.ParameterHandler;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Rectangle;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import java.util.Observable;
import java.util.Observer;

public class InterpreterView extends ViewPart implements Observer, ISelectionListener {

    public static final String ID = "Archimedes.interpreterView"; //$NON-NLS-1$

    private Text input;

	private Text output;


    public void createPartControl (Composite parent) {

        getSite().getWorkbenchWindow().getSelectionService().addSelectionListener(this);
        br.org.archimedes.Utils.getInputController().addObserver(this);

        final FormLayout layout = new FormLayout();
        parent.setLayout(layout);
        FormData layoutData;
        
        
        input = new Text(parent, SWT.SINGLE | SWT.BORDER);
        layoutData = new FormData();
        layoutData.left = new FormAttachment(0);
        layoutData.right = new FormAttachment(100);
        layoutData.bottom = new FormAttachment(100);
        input.setLayoutData(layoutData);

        output = new Text(parent, SWT.MULTI | SWT.V_SCROLL | SWT.READ_ONLY | SWT.BORDER);
        layoutData = new FormData();
        layoutData.left = new FormAttachment(0);
        layoutData.right = new FormAttachment(100);
        layoutData.top = new FormAttachment(0);
        layoutData.bottom = new FormAttachment(input);
        output.setLayoutData(layoutData);

        input.addSelectionListener(new ParameterHandler(input, output));
        input.addListener(SWT.MouseWheel, new Listener() {

            // Used to make the mouse wheel work in Windows
            // (only the widget on focus receives the event)
            public void handleEvent (Event event) {

                Rectangle rect = br.org.archimedes.Utils.getWorkspace().getWindowSize();
                int outputHeight = output.getSize().y;
                int canvasHeight = (int) rect.getHeight();
                int marginsHeight = 10;
                // Relocating the y position of the event to be relative to the
                // canvas origin.
                // FIXME Problems if the user relocates the interpreter view.
                event.y = event.y + outputHeight + canvasHeight + marginsHeight;
                MouseClickHandler.getInstance().receiveMouseWheel(event);
            }
        });

        input.addKeyListener(new KeyListener() {
           
            public void keyPressed (KeyEvent e) {
                InputController inputController = br.org.archimedes.Utils.getInputController();
                

                if (e.keyCode == SWT.ESC) {
                    
                	if(!input.getText().equals("")) {
                		input.setText("");
                	}
                	else {
                		if (SelectionCommand.isActive()) {
                			SelectionCommand.getActive().cancel();
                		}
                		else if ( !(inputController.getCurrentFactory() == null || inputController
                				.getCurrentFactory().isDone())) {
                			inputController.cancelCurrentFactory();
                		}
                		else {
                			br.org.archimedes.Utils.getController().deselectAll();
                		}
                	}
                }
                else if (e.keyCode == SWT.SHIFT) {
                	
                	Workspace workspace = Utils.getWorkspace();
                	workspace.setShiftDown(true);
                }
                else if (e.keyCode == SWT.DEL && input.getText().equals("")) {
                	inputController.receiveText("br.org.archimedes.erase");
                	e.doit = false;
                }
                else if (Character.isWhitespace(e.character) && !inputController.wantsSpace()) {
                    inputController.receiveText(input.getText());
                    e.doit = false;
                    input.setText(""); //$NON-NLS-1$
                }
            }

            public void keyReleased (KeyEvent e) {
            	if (e.keyCode == SWT.SHIFT) {
            		Workspace workspace = Utils.getWorkspace();
                	workspace.setShiftDown(false);
            	}
            	
            }
        });        
        
    }

    public void setFocus () {

        input.forceFocus();
        input.setFocus();
    }

    /*
     * (non-Javadoc)
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable arg0, Object arg1) {

        if (arg0 == br.org.archimedes.Utils.getInputController()
                && (arg1 != null && arg1.getClass() == String.class)) {
            output.append(arg1.toString());
        }
    }

    /**
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart,
     *      org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged (IWorkbenchPart part, ISelection selection) {

        if (StructuredSelection.class.isAssignableFrom(selection.getClass())) {
            IStructuredSelection structuredSelection = (IStructuredSelection) selection;
            try {
                br.org.archimedes.Utils.getInputController().setDrawing(
                        (Drawing) structuredSelection.getFirstElement());
            }
            catch (ClassCastException e) {
                // This is a selection that does not regard drawings so I don't
                // care
            }
        }
    }

    /**
     * @see org.eclipse.ui.part.WorkbenchPart#dispose()
     */
    @Override
    public void dispose () {

        getSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(this);
        super.dispose();
    }
}
