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
package br.org.archimedes.gui.swt.layers;

import java.util.HashMap;
import java.util.Map;

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

import br.org.archimedes.Constant;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.rca.Activator;
import br.org.archimedes.gui.swt.Messages;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author Fabricio S. Nascimento
 */
public class LayerEditor {

    protected static final Image ERROR_ICON = Activator.getImageDescriptor(
            "icons/error.png").createImage(); //$NON-NLS-1$

    protected static final Color DEFAULT_COLOR = Constant.WHITE;

    protected static final String DEFAULT_NAME = Messages.LayerEditor_Layer;

    protected static final LineStyle DEFAULT_LINE_STYLE = LineStyle.CONTINUOUS;

    protected static final double DEFAULT_THICKNESS = 1.0;

    private Shell shell;

    private Shell parent;

    private Button okButton;

    private Label warningIcon;

    private Label warningLabel;

    private LayerTable table;

    private LayerForm form;


    /**
     * Constructor.
     * 
     * @param parent
     *            The parent of the Shell
     * @param layers
     *            The list of the layers
     * @param currentLayer
     *            The current layer
     */
    public LayerEditor (Shell parent, Map<String, Layer> layers,
            Layer currentLayer) {

        this.parent = parent;
        createShell();

        table = new LayerTable(shell, new HashMap<String, Layer>(layers));
        form = new LayerForm(shell, this);
        table.addObserver(form);
        form.addObserver(table);

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
        shell.setText(Messages.LayerEditor_Title);

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

    /**
     * Creates the buttons
     */
    private void createButtons () {

        Composite buttonsComposite = new Composite(shell, SWT.NONE);
        buttonsComposite.setLayout(new FillLayout());

        Button createButton = new Button(buttonsComposite, SWT.PUSH);
        createButton.setText(Messages.LayerEditor_Create);
        createButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                Layer layer = table.newLayer();

                try {
                    br.org.archimedes.Utils.getController().getActiveDrawing().addLayer(layer);
                }
                catch (NoActiveDrawingException e1) {
                    // This should never happen
                    e1.printStackTrace();
                }
            }
        });

        okButton = new Button(buttonsComposite, SWT.PUSH);
        okButton.setText(Messages.OK);
        okButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                shell.dispose();
            }
        });

        okButton.setVisible(true);
        createButton.setVisible(true);
    }

    /**
     * @see org.eclipse.swt.widgets.Shell#isDisposed()
     */
    public boolean isDisposed () {

        return shell.isDisposed();
    }

    /**
     * @param name
     *            A layer name to be validated
     * @param layerOnModification
     *            The layer that wishes this name
     * @return true if the layer name can be used, false otherwise
     */
    public boolean isValid (String name, Layer layerOnModification) {

        Layer layer = table.getLayer(name);
        return (layer == null || layer == layerOnModification);
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
}
