/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/08/31, 10:01:23, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.io.pdf on the br.org.archimedes.io.pdf project.<br>
 */
package br.org.archimedes.io.pdf;

import java.io.IOException;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.io.pdf.rcp.ElementExporterEPLoader;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;

/**
 * Belongs to package br.org.archimedes.model.writers.
 * 
 * @author night
 */
public class PDFWriter {

    private PDFWriterHelper helper;


    /**
     * @param cb
     *            The pdf content byte used to draw
     * @param documentArea
     *            The documentArea to be used in the PDF
     */
    public PDFWriter (PdfContentByte cb, Rectangle documentArea) {

        this.helper = new PDFWriterHelper(cb, documentArea);
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.model.writers.Writer#write(br.org.
     * archimedes.model.Drawing)
     */
    public void write (Drawing drawing) {

        this.helper.setViewport(drawing.getViewportPosition());
        this.helper.setZoom(drawing.getZoom());

        for (Layer layer : drawing.getLayerMap().values()) {
            if (layer.isVisible()) {
                write(layer);
            }
        }
    }

    /*
     * (non-Javadoc)
     * @seebr.org.archimedes.model.writers.Writer#write(br.org.
     * archimedes.model.Layer)
     */
    public void write (Layer layer) {

        PdfContentByte cb = helper.getPdfContentByte();
        cb.setLineWidth((float) layer.getThickness());
        Color layerColor = layer.getPrintColor();
        cb.setRGBColorStroke(layerColor.getRed(), layerColor.getGreen(),
                layerColor.getBlue());
        if (layer.getLineStyle() == LineStyle.STIPPED) {
            cb.setLineDash(5, 0);
        }
        else {
            cb.setLineDash(0);
        }

        ElementEPLoader elementEPLoader = new ElementEPLoader();
        ElementExporterEPLoader exporterLoader = new ElementExporterEPLoader();

        for (Element element : layer.getElements()) {
            String elementId = elementEPLoader.getElementId(element);
            ElementExporter<Element> exporter = exporterLoader
                    .getExporter(elementId);
            try {
                exporter.exportElement(element, helper);
            }
            catch (IOException e) {
                // Something went wrong when writting this element.
                // Just skip it and trace the log.
                e.printStackTrace();
            } catch (NotSupportedException e) {
                // wont reach here
            }
        }
    }
}
