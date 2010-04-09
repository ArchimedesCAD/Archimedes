/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/07, 20:31:06, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt.layers on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt.preferences;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.swt.Messages;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author Juliana Yamashita
 * @author Rodolfo Souza
 */
public class PreferencesForm extends Observable implements Observer {

    private Canvas colorCanvas;

    private Group formGroup;

    private Shell parent;

    private PreferencesEditor editor;


    /**
     * @param parent
     *            The composite parent that contains this form.
     */
    public PreferencesForm (final Shell parent, final PreferencesEditor editor) {

        this.parent = parent;
        this.editor = editor;
        createForm(parent);
    }

    /**
     * Creates the form to edit preferences
     * 
     * @param parent
     *            The parent composite that contains this form
     */
    private void createForm (Composite parent) {

        formGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        formGroup.setText(Messages.PreferencesEditor_Title);
        GridLayout layout = new GridLayout(5, false);
        formGroup.setLayout(layout);

        GridData layoutData;

        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
                | GridData.FILL_VERTICAL);
        layoutData.verticalSpan = 3;
        layoutData.widthHint = 15;

        createColorField(formGroup);

        formGroup.pack();
        formGroup.setVisible(true);
    }

  

 
    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createColorField (Group formGroup) {

        Label colorLabel = new Label(formGroup, SWT.NONE);
        colorLabel.setText(Messages.PreferencesEditor_BackgroundColorLabel);
        GridData layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        colorLabel.setLayoutData(layoutData);

        Composite colorComposite = new Composite(formGroup, SWT.NONE);
        colorComposite.setLayout(new RowLayout());

        colorCanvas = new Canvas(colorComposite, SWT.BORDER);
        Color backgroundColor = editor.getBackgroundColor();
        colorCanvas.setBackground(new org.eclipse.swt.graphics.Color(parent.getDisplay(), backgroundColor.getRed(), backgroundColor.getGreen(), backgroundColor.getBlue()));
        colorCanvas.setLayoutData(new RowData(60, 20));

        Button colorButton = new Button(colorComposite, SWT.PUSH);
        colorButton.setText(Messages.PreferencesEditor_BackgroundColorChange);
        colorButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                ColorDialog colorDialog = new ColorDialog(parent);
                RGB rgb = colorDialog.open();
                
                if (rgb != null) {
                    Color newColor = new Color(rgb.red, rgb.green, rgb.blue);
                    if ( !newColor.equals(editor.getBackgroundColor())) {
                        Device device = parent.getDisplay();
                        org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(
                                device, rgb);

                        colorCanvas.setBackground(color);
                        editor.setBackgroundColor(newColor);
                        PreferencesForm.this.setChanged();
                    }
                }

            }
        });

        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        colorComposite.setLayoutData(layoutData);
    }

    /**
     *  
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable table, Object layerObject) {}
}
