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

import br.org.archimedes.exceptions.NotSupportedException;
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

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

/**
 * Belongs to package br.org.archimedes.io.svg.
 * 
 * @author Bruno Klava and Ricardo Sider
 */
public class SVGExporter implements Exporter {

    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.Exporter#exportDrawing(br.org.archimedes.interfaces.Drawing,
     *      java.io.OutputStream)
     */
    public void exportDrawing (Drawing drawing, OutputStream output) throws IOException {

        String charset = "UTF-8"; //$NON-NLS-1$
        // TODO Forçar locale

        Rectangle boundaryRectangle = drawing.getBoundary();
        if (boundaryRectangle == null) {
        	boundaryRectangle = new Rectangle(0, 0, 0, 0);
        }

        exportSVGHeader(drawing, output, charset, boundaryRectangle);
        writeLayersDefs(drawing.getLayerMap().values(), output, charset);

        ElementEPLoader elementEPLoader = new ElementEPLoader();
        ElementExporterEPLoader exporterLoader = new ElementExporterEPLoader();

        for (Layer layer : drawing.getLayerMap().values()) {

        	String header = "<g class=\"" + escapeName(layer.getName()) + "\" fill=\"none\">\n";
            output.write(header.getBytes(charset));

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

            output.write("</g>\n".getBytes(charset));
        }
        output.write(("</svg>" + "\n").getBytes(charset)); //$NON-NLS-1$ //$NON-NLS-2$
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
                + (int) Math.ceil(boundaryRectangle.getHeight()) + "\">");

        output.write(drawingTag.toString().getBytes(charset));
    }
    
    private void writeLayersDefs (Collection<Layer> layers, OutputStream output, String encoding) throws IOException {

    	StringBuilder builder = new StringBuilder();
        builder.append("<defs>\n" + "<style type=\"text/css\">\n" + "<![CDATA[\n");
        for (Layer layer : layers) {
            String printColor = layer.getPrintColor().toHexString();
            builder.append("." + escapeName(layer.getName()) + " {stroke:#" + printColor + ";stroke-width:"
                    + ((int) layer.getThickness()) + "}\n");
        }
        builder.append("]]>\n" + "</style>\n" + "</defs>\n");
        output.write(builder.toString().getBytes(encoding));
    }
    
    private String escapeName(String name) {
    	return name.replaceAll(" ", "-_-");
    }
}
