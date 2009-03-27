/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/07, 20:21:47, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt.layers on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt.layers;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import br.org.archimedes.gui.swt.Messages;
import br.org.archimedes.model.Layer;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author nitao
 */
public class LayerTable extends Observable implements Observer {

    private Table table;

    private Map<String, Layer> layers;

    private Layer selectedLayer;

    private Shell shell;


    /**
     * @param parent
     *            The parent composite of this table
     * @param layers
     *            The list of layers to fill the table
     */
    public LayerTable (Shell parent, Map<String, Layer> layers) {

        this.shell = parent;
        this.layers = layers;

        createTable(parent);
    }

    private void createTable (Composite parent) {

        table = new Table(parent, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
        RowData layoutData = new RowData(680, 300);
        table.setLayoutData(layoutData);

        table.setLinesVisible(true);
        table.setHeaderVisible(true);

        TableColumn nameColumn = new TableColumn(table, SWT.NONE);
        nameColumn.setText(Messages.LayerEditor_NameLabel);
        nameColumn.setWidth(170);

        TableColumn visibleColumn = new TableColumn(table, SWT.NONE);
        visibleColumn.setText(Messages.LayerEditor_Visible);
        visibleColumn.setWidth(70);

        TableColumn lockedColumn = new TableColumn(table, SWT.NONE);
        lockedColumn.setText(Messages.LayerEditor_Locked);
        lockedColumn.setWidth(70);

        TableColumn colorColumn = new TableColumn(table, SWT.NONE);
        colorColumn.setText(Messages.LayerEditor_ColorLabel);
        colorColumn.setWidth(80);

        TableColumn styleColumn = new TableColumn(table, SWT.NONE);
        styleColumn.setText(Messages.LayerEditor_StyleLabel);
        styleColumn.setWidth(120);

        TableColumn widthColumn = new TableColumn(table, SWT.NONE);
        widthColumn.setText(Messages.LayerEditor_ThicknessLabel);
        widthColumn.setWidth(90);

        TableColumn printColumn = new TableColumn(table, SWT.NONE);
        printColumn.setText(Messages.LayerEditor_PrintColorLabel);
        printColumn.setWidth(80);

        List<String> keys = new ArrayList<String>(layers.keySet());
        Collections.sort(keys);
        for (String layerName : keys) {
            Layer layer = layers.get(layerName);
            boolean select = (layer == selectedLayer);
            insertLayer(layer, select);
        }

        table.pack();
        table.setVisible(true);
        table.addSelectionListener(new SelectionAdapter() {

            /**
             * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
             */
            @Override
            public void widgetSelected (SelectionEvent e) {

                TableItem item = (TableItem) e.item;
                selectedLayer = (Layer) item.getData();
                setChanged();
                notifyObservers(selectedLayer);
            }
        });
    }

    /**
     * Updates the table with information on the current layer.
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    public void update (Observable arg0, Object layerObject) {

        Layer layer;
        try {
            layer = (Layer) layerObject;
        }
        catch (ClassCastException e) {
            layer = new NullLayer();
        }

        updateItem(table.getSelection()[0], layer);
    }

    /**
     * @return The created layer
     */
    protected Layer newLayer () {

        int counter = table.getItemCount();
        String layerName = LayerEditor.DEFAULT_NAME + counter;
        while (layers.containsKey(layerName)) {
            layerName = LayerEditor.DEFAULT_NAME + ++counter;
        }

        Layer layer = new Layer(LayerEditor.DEFAULT_COLOR, layerName,
                LayerEditor.DEFAULT_LINE_STYLE, LayerEditor.DEFAULT_THICKNESS);
        layers.put(layer.getName(), layer);
        insertLayer(layer, true);

        return layer;
    }

    /**
     * Adds a layer to the layer table
     * 
     * @param layer
     *            The layer to be inserted
     * @param select
     *            true if the new item is to be selected, false otherwise
     */
    private void insertLayer (Layer layer, boolean select) {

        TableItem item = new TableItem(table, SWT.NONE);
        updateItem(item, layer);

        if (select) {
            table.setSelection(item);
            selectedLayer = layer;
            setChanged();
            notifyObservers(selectedLayer);
        }
    }

    /**
     * @param item
     *            The table item associated to this layer.
     * @param layer
     *            The layer containing the model.
     */
    private void updateItem (TableItem item, Layer layer) {

        Display display = shell.getDisplay();
        String oldName = item.getText(0);
        if ( !oldName.equals(layer.getName())) {
            layers.remove(oldName);
            layers.put(layer.getName(), layer);
            item.setText(0, layer.getName());
        }

        // TODO Setar imagens
        item.setText(1, "" + layer.isVisible()); //$NON-NLS-1$
        item.setText(2, "" + layer.isLocked()); //$NON-NLS-1$

        item.setBackground(3, layer.getColor().toSWTColor(display));
        item.setForeground(3, layer.getColor().toSWTColor(display));

        item.setText(4, layer.getLineStyle().getName());
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        item.setText(5, nf.format(layer.getThickness()));

        item.setBackground(6, layer.getPrintColor().toSWTColor(display));
        item.setForeground(6, layer.getPrintColor().toSWTColor(display));

        item.setData(layer);
    }

    /**
     * @param name
     *            The layer's name
     * @return The layer that has this name or null if none
     */
    public Layer getLayer (String name) {

        return layers.get(name);
    }
}
