/*
 * Created on 31/08/2006
 */

package br.org.archimedes.io.pdf;

import java.io.IOException;

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
 * Belongs to package com.tarantulus.archimedes.model.writers.
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
     * @seecom.tarantulus.archimedes.model.writers.Writer#write(com.tarantulus.
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
     * @seecom.tarantulus.archimedes.model.writers.Writer#write(com.tarantulus.
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
            }
        }
    }
}
