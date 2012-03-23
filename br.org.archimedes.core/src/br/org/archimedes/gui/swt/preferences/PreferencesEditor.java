/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Fabricio S. Nascimento - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/10/07, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt.layers on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt.preferences;

import java.util.HashMap;

import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import br.org.archimedes.Utils;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.rca.editor.DrawingEditor;
import br.org.archimedes.gui.swt.Messages;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author Juliana Yamashita
 * @author Rodolfo Souza
 */
public class PreferencesEditor {

    private Shell shell;

    private Shell parent;

    private Button okButton;

    private Label warningIcon;

    private Label warningLabel;
    
    private PreferencesForm form;
    
    private HashMap<String, Color> colors;
	
	private final DrawingEditor drawingEditor;


    /**
     * Constructor.
     * 
     * @param parent
     *            The parent of the Shell
     * @param backgroundColor
     * 			  The workspace current background color 
     */
    public PreferencesEditor (Shell parent, HashMap<String, Color> colors, DrawingEditor drawingEditor) {

        this.parent = parent;
        this.colors = colors;
        this.drawingEditor = drawingEditor;
        createShell();

        this.form = new PreferencesForm(shell, this);
       
        createWarning();
        createButtons();

        shell.pack();
    }

    /**
     * Shows the window.
     */
    public void open () {

        parent.setEnabled(false);
        shell.setVisible(true);
        shell.open();
    }

    /**
     * Creates the shell and all the other components
     */
    public void createShell () {

        shell = new Shell(parent);
        shell.setText(Messages.PreferencesEditor_Title);

        shell.addDisposeListener(new DisposeListener() {

            public void widgetDisposed (DisposeEvent e) {

                parent.setEnabled(true);
            }
        });
        shell.addShellListener(new ShellListener() {

            public void shellActivated (ShellEvent e) {

                // Ignore
            }

            public void shellClosed (ShellEvent e) {

                e.doit = okButton.isEnabled();
                if ( !e.doit) {
                    ErrorDialog error = new ErrorDialog(shell,
                            Messages.CloseError_Title, Messages.CloseError_Message, null, 0);
                    error.open();
                }
            }

            public void shellDeactivated (ShellEvent e) {

                // Ignore
            }

            public void shellDeiconified (ShellEvent e) {

                // Ignore
            }

            public void shellIconified (ShellEvent e) {

                // Ignore
            }

        });
        RowLayout layout = new RowLayout();
        layout.type = SWT.VERTICAL;
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.spacing = 8;
        layout.fill = true;
        shell.setLayout(layout);
    }

    /**
     * Creates a warning composite.
     */
    private void createWarning () {

        Composite warningComposite = new Composite(shell, SWT.NONE);
        RowLayout rowLayout = new RowLayout();
        rowLayout.type = SWT.HORIZONTAL;
        warningComposite.setLayout(rowLayout);

        warningIcon = new Label(warningComposite, SWT.NONE);
        RowData rowData = new RowData();
        rowData.height = 16;
        rowData.width = 20;
        warningIcon.setLayoutData(rowData);

        warningLabel = new Label(warningComposite, SWT.NONE);
        rowData = new RowData();
        rowData.height = 16;
        rowData.width = 500;
        warningLabel.setLayoutData(rowData);

        warningIcon.setVisible(true);
        warningLabel.setVisible(true);
    }
    
    public SelectionAdapter getOKSelectionAdapter() {
    	return new SelectionAdapter() {
            public void widgetSelected (SelectionEvent e) {
            	Utils.getWorkspace().setGripSelectionColor(getColor("gripSelection"));
            	Utils.getWorkspace().setGripMouseOverColor(getColor("gripMouseOver"));
            	Utils.getWorkspace().setCursorColor(getColor("cursor"));
            	Utils.getWorkspace().setBackgroundColor(getColor("background"));
            	Utils.getWorkspace().saveProperties(false);
                shell.dispose();
                drawingEditor.update(null, null);
            }
        };
    }
    
    /**
     * Creates the buttons
     */
    private void createButtons () {

        Composite buttonsComposite = new Composite(shell, SWT.NONE);
        buttonsComposite.setLayout(new FillLayout());
        
        okButton = new Button(buttonsComposite, SWT.PUSH);
        okButton.setText(Messages.OK);
        okButton.addSelectionListener(getOKSelectionAdapter());

        okButton.setVisible(true);
    }

    /**
     * @see org.eclipse.swt.widgets.Shell#isDisposed()
     */
    public boolean isDisposed () {

        return shell.isDisposed();
    }


    /**
     * @param icon
     *            The icon to be shown
     * @param message
     *            The message to be displayed
     */
    protected void setWarning (Image icon, String message) {

        warningIcon.setImage(icon);
        warningLabel.setText(message);
    }

    /**
     * @param closable
     *            true if this editor can be closed, false otherwise.
     */
    public void setClosable (boolean closable) {

        okButton.setEnabled(closable);
    }
    
    public void setColor(String name, Color color) {
    	colors.put(name, color);
    }
    
    public Color getColor(String name) {
    	Color color = this.colors.get(name);
    	if(color == null){
    		color = new Color(0,0,0);
    	}
    	return color;
    }
}
