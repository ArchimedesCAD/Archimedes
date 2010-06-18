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

import java.util.HashMap;
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

    private HashMap<String, Canvas> allCanvas;
    
    private HashMap<String, String> messages;

    private Group formGroup;

    private Shell parent;

    private PreferencesEditor editor;

    public static String background = "background";
    public static String cursor = "cursor";
    public static String gripSelection = "gripSelection";
    public static String gripMouseOver = "gripMouseOver";
    
    /**
     * @param parent
     *            The composite parent that contains this form.
     */
    public PreferencesForm (final Shell parent, final PreferencesEditor editor) {

        this.parent = parent;
        this.editor = editor;
        allCanvas = new HashMap<String, Canvas>();
        messages = new HashMap<String, String>();
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
        GridLayout layout = new GridLayout(2, true);
        formGroup.setLayout(layout);

        GridData layoutData;
        
        messages.put(PreferencesForm.background + "Label", Messages.PreferencesEditor_BackgroundColorLabel);
        messages.put(PreferencesForm.background + "Change", Messages.PreferencesEditor_BackgroundColorChange);
        messages.put(PreferencesForm.cursor + "Label", Messages.PreferencesEditor_CursorColorLabel);
        messages.put(PreferencesForm.cursor + "Change", Messages.PreferencesEditor_CursorColorChange);
        messages.put(PreferencesForm.gripSelection + "Label", Messages.PreferencesEditor_GripSelectionColorLabel);
        messages.put(PreferencesForm.gripSelection + "Change", Messages.PreferencesEditor_GripSelectionColorChange);
        messages.put(PreferencesForm.gripMouseOver + "Label", Messages.PreferencesEditor_GripMouseOverColorLabel);
        messages.put(PreferencesForm.gripMouseOver + "Change", Messages.PreferencesEditor_GripMouseOverColorChange);
        
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
                | GridData.FILL_VERTICAL);
        layoutData.verticalSpan = 1;
                
        createColorField(formGroup, PreferencesForm.background);
        
        createColorField(formGroup, PreferencesForm.cursor);
        
        createColorField(formGroup, PreferencesForm.gripSelection);
        
        createColorField(formGroup, PreferencesForm.gripMouseOver);

        formGroup.pack();
        formGroup.setVisible(true);
    }

    private void createColorField(Group formGroup, final String element) {
    	Label colorLabel = new Label(formGroup, SWT.NONE);
    	String messageLabel = messages.get(element + "Label");
    	String changeMessage = messages.get(element + "Change");
    	colorLabel.setText(messageLabel);
    	GridData layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    	colorLabel.setLayoutData(layoutData);
    	
    	Composite colorComposite = new Composite(formGroup, SWT.NONE);
    	colorComposite.setLayout(new RowLayout());
    	
    	Color color = editor.getColor(element);
    	allCanvas.put(element,new Canvas(colorComposite, SWT.BORDER));
    	allCanvas.get(element).setBackground(new org.eclipse.swt.graphics.Color(parent.getDisplay(), color.getRed(), color.getGreen(), color.getBlue()));
        allCanvas.get(element).setLayoutData(new RowData(60, 20));
        
        Button colorButton = new Button(colorComposite, SWT.PUSH);
        colorButton.setText(changeMessage);
        colorButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                ColorDialog colorDialog = new ColorDialog(parent);
                RGB rgb = colorDialog.open();
                
                if (rgb != null) {
                    Color newColor = new Color(rgb.red, rgb.green, rgb.blue);
                    if ( !newColor.equals(editor.getColor(element))) {
                        Device device = parent.getDisplay();
                        org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(
                                device, rgb);

                        allCanvas.get(element).setBackground(color);
                        editor.setColor(element, newColor);
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
