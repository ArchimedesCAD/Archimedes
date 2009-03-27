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
 * This file was created on 2006/10/06, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.org.archimedes.gui.model.Workspace;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author fabsn
 */
public class TextEditor {

    private Shell shell;

    private GridLayout layout;

    private Button okButton;

    protected Workspace workspace;

    private Shell parent;

    private Text text;

    private String contents;

    private Button cancelButton;


    /**
     * Constructor. Builds a new Text Editor dialog.
     * 
     * @param text
     */
    public TextEditor (Shell parent, String text) {

        workspace = br.org.archimedes.Utils.getWorkspace();
        this.parent = parent;
        this.contents = text;
        createShell();
        createAndLayThingsOut();
        shell.pack();
    }

    /**
     * return contents The edited text
     */
    public String getContents () {

        return contents;
    }

    /**
     * Creates the widgets and lays them on the dialog.
     */
    private void createAndLayThingsOut () {

        layout = new GridLayout(2, false);
        layout.marginWidth = 16;
        layout.marginHeight = 16;
        layout.verticalSpacing = 16;

        shell.setLayout(layout);

        text = createTextInput();
        text.setText(contents);
        GridData layoutData = new GridData(SWT.FILL, SWT.CENTER, false, false);
        layoutData.horizontalSpan = 2;
        text.setLayoutData(layoutData);

        okButton = createOkButton();
        layoutData = new GridData();
        layoutData.widthHint = 120;
        layoutData.horizontalAlignment = GridData.CENTER;
        layoutData.verticalAlignment = GridData.VERTICAL_ALIGN_END;
        okButton.setLayoutData(layoutData);

        cancelButton = createCancelButton();
        layoutData = new GridData();
        layoutData.widthHint = 120;
        layoutData.horizontalAlignment = GridData.CENTER;
        layoutData.verticalAlignment = GridData.VERTICAL_ALIGN_BEGINNING;
        cancelButton.setLayoutData(layoutData);
    }

    private Text createTextInput () {

        Text textWidget = new Text(shell, SWT.BORDER);
        textWidget.addSelectionListener(new SelectionAdapter() {

            public void widgetDefaultSelected (SelectionEvent event) {

                contents = text.getText();
                shell.dispose();
            }
        });
        return textWidget;
    }

    private void createShell () {

        shell = new Shell(parent, SWT.DIALOG_TRIM);
        shell.setText(Messages.TextEditor_WindowName);

        shell.addDisposeListener(new DisposeListener() {

            public void widgetDisposed (DisposeEvent arg0) {

                getParent().setEnabled(true);
            }
        });

        shell.addKeyListener(new KeyAdapter() {

            public void keyReleased (KeyEvent event) {

                if (event.character == SWT.ESC) {
                    shell.dispose();
                }
            }
        });
    }

    /**
     * @return The parent of this window.
     */
    public Shell getParent () {

        return parent;
    }

    /**
     * Creates the dialog's OK button.
     * 
     * @return The created button to be layed out.
     */
    private Button createOkButton () {

        Button okButton = new Button(shell, SWT.PUSH);
        okButton.setText(Messages.OK);

        okButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent event) {

                contents = text.getText();
                shell.dispose();
            }
        });

        return okButton;
    }

    /**
     * Creates the dialog's Cancel button.
     * 
     * @return The created button to be layed out.
     */
    private Button createCancelButton () {

        Button cancelButton = new Button(shell, SWT.PUSH);
        cancelButton.setText(Messages.Cancel);

        cancelButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent event) {

                shell.dispose();
            }
        });

        return cancelButton;
    }

    /**
     * Opens the dialog for displaying. Disables the ability of the parent
     * window to receive any events.
     */
    public String open () {

        getParent().setEnabled(false);
        shell.open();
        Display display = parent.getDisplay();
        while ( !shell.isDisposed()) {
            if ( !display.readAndDispatch())
                display.sleep();
        }
        return contents;
    }
}
