/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno Klava, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/26, 12:05:56, by Ricardo Sider.<br>
 * It is part of package br.org.archimedes.io.svg on the br.org.archimedes.io.svg project.<br>
 */

package br.org.archimedes.io.svg;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import br.org.archimedes.exceptions.NotSupportedException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.io.svg.rcp.ElementExporterEPLoader;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;
import br.org.archimedes.semiline.Semiline;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author klava, sider
 */
public class SVGExporter implements Exporter {

    /**
     * Returns the boundary rectangle containing all the drawing elements. It ignores the infinite
     * lines and includes only the initial point of semilines.
     * 
     * @param drawing
     * @return the boundary rectangle containing all the drawing elements
     */
    private Rectangle getBoundaryRectangle (Drawing drawing) {

        Rectangle boundaryRectangle = null;

        for (Layer layer : drawing.getLayerMap().values()) {

            for (Element element : layer.getElements()) {

                if (element instanceof InfiniteLine) {
                    continue;
                }
                else if (element instanceof Semiline) {
                    Semiline semiline = (Semiline) element;
                    Rectangle rect = new Rectangle(semiline.getInitialPoint().getX(), semiline
                            .getInitialPoint().getY(), 0, 0);
                    if (boundaryRectangle == null) {
                        boundaryRectangle = rect;
                    }
                    else {
                        boundaryRectangle = boundaryRectangle.union(rect);
                    }
                    continue;
                }
                else {
                    boundaryRectangle = boundaryRectangle.union(element.getBoundaryRectangle());
                }

            }

        }

        if (boundaryRectangle == null) {
            boundaryRectangle = new Rectangle(0, 0, 0, 0);
        }

        return boundaryRectangle;

    }

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.Exporter#exportDrawing(br.org.archimedes.interfaces.Drawing,
     *      java.io.OutputStream)
     */
    public void exportDrawing (Drawing drawing, OutputStream output) throws IOException {

        String charset = "UTF-8"; //$NON-NLS-1$
        // TODO Forçar locale

        Rectangle boundaryRectangle = getBoundaryRectangle(drawing);

        exportSVGHeader(drawing, output, charset, boundaryRectangle);

        byte[] endContainerTagBytes = ("\t" + "</container>" + "\n") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                .getBytes(charset);

        ElementEPLoader elementEPLoader = new ElementEPLoader();
        ElementExporterEPLoader exporterLoader = new ElementExporterEPLoader();

        for (Layer layer : drawing.getLayerMap().values()) {
            StringBuilder containerTag = new StringBuilder();
            containerTag.append("\t" + "<container name=\"" + layer.getName() //$NON-NLS-1$ //$NON-NLS-2$
                    + "\" lineStyle=\"" + layer.getLineStyle().ordinal() //$NON-NLS-1$
                    + "\" thickness=\"" + layer.getThickness() //$NON-NLS-1$
                    + "\" visible=\"" + layer.isVisible() //$NON-NLS-1$
                    + "\" locked=\"" + layer.isLocked() + "\"" + " >" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            writeColor(containerTag, layer.getColor());
            writeColor(containerTag, layer.getPrintColor());

            output.write(containerTag.toString().getBytes(charset));

            for (Element element : layer.getElements()) {

                String elementId = elementEPLoader.getElementId(element);
                ElementExporter<Element> exporter = exporterLoader.getExporter(elementId);
                if (exporter == null) {
                    System.err.println(Messages.bind(Messages.SVGExporter_NoExporter, elementId));
                }
                else {
                    try {
                        if (element instanceof InfiniteLine || element instanceof Semiline) {
                            exporter.exportElement(element, output, boundaryRectangle);
                        }
                        else {
                            exporter.exportElement(element, output);
                        }
                    }
                    catch (NotSupportedException e) {
                        // wont reach here
                    }

                }

            }

            output.write(endContainerTagBytes);
        }
        output.write(("</drawing>" + "\n").getBytes(charset)); //$NON-NLS-1$ //$NON-NLS-2$
        output.close();
    }

    /**
     * @param drawing
     *            Drawing to be exported
     * @param output
     *            The outputstream to write on
     * @param charset
     *            The charset to use to write the file @ * @throws IOException Thrown if something
     *            goes wrong when writing the file
     * @throws UnsupportedEncodingException
     *             Thrown if the system cannot write in the specified charset
     */
    private void exportSVGHeader (Drawing drawing, OutputStream output, String charset,
            Rectangle boundaryRectangle) throws IOException, UnsupportedEncodingException {

        StringBuilder drawingTag = new StringBuilder("<?xml version=\"1.0\" encoding=\"" + charset
                + "\"?>" + "\n");

        drawingTag.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\""
                // + " width="10cm" height="3cm"

                + " viewBox=\"" + (int) Math.ceil(boundaryRectangle.getLowerLeft().getX()) + " "
                + (int) Math.ceil(boundaryRectangle.getLowerLeft().getY()) + " "
                + (int) Math.ceil(boundaryRectangle.getWidth()) + " "
                + (int) Math.ceil(boundaryRectangle.getHeight()) + ">");

        output.write(drawingTag.toString().getBytes(charset));
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param color
     *            The color that should be written
     */
    private void writeColor (StringBuilder builder, Color color) {

        builder.append("\t" + "\t" + "<color>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        writeComponent(builder, color.getRed());
        writeComponent(builder, color.getGreen());
        writeComponent(builder, color.getBlue());
        builder.append("\t" + "\t" + "</color>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param component
     *            The component to be written
     */
    private void writeComponent (StringBuilder builder, int component) {

        builder.append("\t" + "\t" + "\t" + "<unsignedByte>"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
        builder.append(component);
        builder.append("</unsignedByte>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
    }
}
