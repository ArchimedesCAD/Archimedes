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
package br.org.archimedes.gui.swt.layers;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import br.org.archimedes.Constant;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.gui.swt.Messages;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author nitao
 */
public class LayerForm extends Observable implements Observer {

    private Text nameText;

    private Spinner thicknessSpinner;

    private Canvas colorCanvas;

    private Combo styleCombo;

    private Button visibleButton;

    private Button lockedButton;

    private Canvas printCanvas;

    private Group formGroup;

    private final int DIGIT_NUMBER = 3;

    private Layer currentLayer;

    private Shell parent;

    private LayerEditor editor;

    private ModifyListener modifyListener;


    /**
     * @param parent
     *            The composite parent that contains this form.
     */
    public LayerForm (final Shell parent, final LayerEditor editor) {

        this.parent = parent;
        this.editor = editor;
        this.modifyListener = new ModifyListener() {

            public void modifyText (ModifyEvent e) {

                String name = nameText.getText();
                Display display = LayerForm.this.parent.getDisplay();
                boolean valid = isValid(name);
                LayerForm.this.editor.setClosable(valid);
                if ( !valid) {
                    org.eclipse.swt.graphics.Color swtRed = Constant.RED
                            .toSWTColor(display);
                    nameText.setBackground(swtRed);

                    LayerForm.this.editor.setWarning(LayerEditor.ERROR_ICON,
                            Messages.LayerEditor_DuplicateName);
                }
                else {
                    org.eclipse.swt.graphics.Color swtWhite = Constant.WHITE
                            .toSWTColor(display);
                    nameText.setBackground(swtWhite);
                    currentLayer.setName(name);
                    LayerForm.this.editor.setWarning(null, ""); //$NON-NLS-1$
                    LayerForm.this.setChanged();
                }
                notifyObservers(currentLayer);
            }
        };
        createForm(parent);

    }

    /**
     * Creates the form to edit a layer
     * 
     * @param parent
     *            The parent composite that contians this form
     */
    private void createForm (Composite parent) {

        formGroup = new Group(parent, SWT.SHADOW_ETCHED_IN);
        formGroup.setText(Messages.LayerEditor_Title);
        GridLayout layout = new GridLayout(5, false);
        formGroup.setLayout(layout);

        GridData layoutData;

        createNameField(formGroup);

        Label separator = new Label(formGroup, SWT.VERTICAL | SWT.SEPARATOR);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
                | GridData.FILL_VERTICAL);
        layoutData.verticalSpan = 3;
        layoutData.widthHint = 15;
        separator.setLayoutData(layoutData);

        createThicknessField(formGroup);
        createColorField(formGroup);
        createStyleField(formGroup);
        createPrintField(formGroup);

        createChecks(formGroup);

        formGroup.pack();
        formGroup.setVisible(true);
    }

    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createChecks (Group formGroup) {

        visibleButton = new Button(formGroup, SWT.CHECK);
        visibleButton.setText(Messages.LayerEditor_Visible);
        GridData layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        visibleButton.setLayoutData(layoutData);

        lockedButton = new Button(formGroup, SWT.CHECK);
        lockedButton.setText(Messages.LayerEditor_Locked);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        lockedButton.setLayoutData(layoutData);

        visibleButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                if (currentLayer.isVisible() != visibleButton.getSelection()) {
                    currentLayer.setVisible(visibleButton.getSelection());
                    LayerForm.this.setChanged();
                }
                if (currentLayer.isLocked() != lockedButton.getSelection()) {
                    lockedButton.setSelection(currentLayer.isLocked());
                    LayerForm.this.setChanged();
                }
                if (visibleButton.getSelection() != lockedButton.getEnabled()) {
                    lockedButton.setEnabled(visibleButton.getSelection());
                    LayerForm.this.setChanged();
                }
                notifyObservers(currentLayer);
            }
        });

        lockedButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                if (lockedButton.getSelection() != currentLayer.isLocked()) {
                    currentLayer.setLocked(lockedButton.getSelection());
                    LayerForm.this.setChanged();
                    notifyObservers(currentLayer);
                }
            }
        });
    }

    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createPrintField (Group formGroup) {

        GridData layoutData;
        Label printLabel = new Label(formGroup, SWT.NONE);
        printLabel.setText(Messages.LayerEditor_PrintColorLabel);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        printLabel.setLayoutData(layoutData);

        Composite printComposite = new Composite(formGroup, SWT.NONE);
        printComposite.setLayout(new RowLayout());

        printCanvas = new Canvas(printComposite, SWT.BORDER);
        printCanvas.setLayoutData(new RowData(60, 20));

        Button printButton = new Button(printComposite, SWT.PUSH);
        printButton.setText(Messages.LayerEditor_PrintColorChange);
        printButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                ColorDialog colorDialog = new ColorDialog(parent);
                RGB rgb = colorDialog.open();
                if (rgb != null) {
                    Color newColor = new Color(rgb.red, rgb.green, rgb.blue);
                    if ( !newColor.equals(currentLayer.getPrintColor())) {
                        Device device = parent.getDisplay();
                        org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(
                                device, rgb);
                        printCanvas.setBackground(color);
                        currentLayer.setPrintColor(newColor);

                        LayerForm.this.setChanged();
                        notifyObservers(currentLayer);
                    }
                }
            }
        });

        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        printComposite.setLayoutData(layoutData);
    }

    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createStyleField (Group formGroup) {

        GridData layoutData;
        Label styleLabel = new Label(formGroup, SWT.NONE);
        styleLabel.setText(Messages.LayerEditor_StyleLabel);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        styleLabel.setLayoutData(layoutData);

        styleCombo = new Combo(formGroup, SWT.READ_ONLY);
        for (LineStyle lineStyle : LineStyle.values()) {
            styleCombo.add(lineStyle.getName());
        }
        styleCombo.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                int selectedLine = styleCombo.getSelectionIndex();
                // Enums can be compared directly since it is always the same
                // instances used
                if (LineStyle.values()[selectedLine] != currentLayer
                        .getLineStyle()) {
                    currentLayer.setLineStyle(LineStyle.values()[selectedLine]);
                    LayerForm.this.setChanged();
                    notifyObservers(currentLayer);
                }
            }
        });
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        layoutData.widthHint = 150;
        styleLabel.setLayoutData(layoutData);
    }

    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createColorField (Group formGroup) {

        Label colorLabel = new Label(formGroup, SWT.NONE);
        colorLabel.setText(Messages.LayerEditor_ColorLabel);
        GridData layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        colorLabel.setLayoutData(layoutData);

        Composite colorComposite = new Composite(formGroup, SWT.NONE);
        colorComposite.setLayout(new RowLayout());

        colorCanvas = new Canvas(colorComposite, SWT.BORDER);
        colorCanvas.setLayoutData(new RowData(60, 20));

        Button colorButton = new Button(colorComposite, SWT.PUSH);
        colorButton.setText(Messages.LayerEditor_ColorChange);
        colorButton.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                ColorDialog colorDialog = new ColorDialog(parent);
                RGB rgb = colorDialog.open();
                if (rgb != null) {
                    Color newColor = new Color(rgb.red, rgb.green, rgb.blue);
                    if ( !newColor.equals(currentLayer.getColor())) {
                        Device device = parent.getDisplay();
                        org.eclipse.swt.graphics.Color color = new org.eclipse.swt.graphics.Color(
                                device, rgb);

                        colorCanvas.setBackground(color);
                        currentLayer.setColor(newColor);

                        LayerForm.this.setChanged();
                        notifyObservers(currentLayer);
                    }
                }

            }
        });

        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        colorComposite.setLayoutData(layoutData);
    }

    /**
     * @param formGroup
     *            The form group that should contain the style field.
     */
    private void createThicknessField (Group formGroup) {

        GridData layoutData;
        Label thicknessLabel = new Label(formGroup, SWT.NONE);
        thicknessLabel.setText(Messages.LayerEditor_ThicknessLabel);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        thicknessLabel.setLayoutData(layoutData);

        thicknessSpinner = new Spinner(formGroup, SWT.BORDER);
        thicknessSpinner.setDigits(DIGIT_NUMBER);
        thicknessSpinner.setMinimum(1);
        thicknessSpinner.setIncrement(10);
        thicknessSpinner.setMaximum((int) Math.pow(10, DIGIT_NUMBER + 1));
        thicknessSpinner.setSelection(1);
        thicknessSpinner.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected (SelectionEvent e) {

                updateThickness();
            }

            public void widgetDefaultSelected (SelectionEvent e) {

                updateThickness();
            }
        });
        thicknessSpinner.addModifyListener(new ModifyListener() {

            public void modifyText (ModifyEvent e) {

                updateThickness();
            }
        });
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        layoutData.widthHint = 100;
        thicknessSpinner.setLayoutData(layoutData);
    }

    /**
     * Creates the name label and text field
     * 
     * @param formGroup
     *            The parent composite
     */
    private void createNameField (Group formGroup) {

        GridData layoutData;
        Label nameLabel = new Label(formGroup, SWT.NONE);
        nameLabel.setText(Messages.LayerEditor_NameLabel);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        nameLabel.setLayoutData(layoutData);

        nameText = new Text(formGroup, SWT.BORDER);
        layoutData = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        layoutData.widthHint = 280;
        nameText.setLayoutData(layoutData);
        nameText.addModifyListener(modifyListener);
    }

    /**
     * @param name
     *            The name to be checked
     * @return true if it is valid, false otherwise.
     */
    protected boolean isValid (String name) {

        return editor.isValid(name, currentLayer);
    }

    /**
     * Updates the thickness of the layer
     */
    protected void updateThickness () {

        double layerWidth = thicknessSpinner.getSelection()
                / Math.pow(10, DIGIT_NUMBER);
        if (Math.abs(layerWidth - currentLayer.getThickness()) > Constant.EPSILON) {
            currentLayer.setThickness(layerWidth);
            setChanged();
            notifyObservers(currentLayer);
        }
    }

    /**
     * Updates the form with the selected layer.
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable table, Object layerObject) {

        try {
            currentLayer = (Layer) layerObject;
        }
        catch (ClassCastException e) {
            currentLayer = new NullLayer();
        }
        Device device = this.formGroup.getDisplay();
        String currentLayerName = currentLayer.getName();
        nameText.removeModifyListener(modifyListener);
        nameText.setText(currentLayerName);
        nameText.addModifyListener(modifyListener);

        org.eclipse.swt.graphics.Color swtColor;
        swtColor = currentLayer.getColor().toSWTColor(device);
        colorCanvas.setBackground(swtColor);
        swtColor = currentLayer.getPrintColor().toSWTColor(device);
        printCanvas.setBackground(swtColor);

        double layerThickness = currentLayer.getThickness()
                * Math.pow(10, DIGIT_NUMBER);
        thicknessSpinner.setSelection((int) layerThickness);

        LineStyle lineStyle = currentLayer.getLineStyle();
        styleCombo.setText(lineStyle.getName());

        visibleButton.setSelection(currentLayer.isVisible());
        lockedButton.setSelection(currentLayer.isLocked());
        lockedButton.setEnabled(visibleButton.getSelection());
    }
}
