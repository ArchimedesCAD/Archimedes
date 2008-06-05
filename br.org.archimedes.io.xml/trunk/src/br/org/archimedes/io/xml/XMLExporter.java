/**
 * 
 */

package br.org.archimedes.io.xml;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.ElementExporter;
import br.org.archimedes.interfaces.Exporter;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.io.xml.
 * 
 * @author night
 */
public class XMLExporter implements Exporter {

    private final static Map<String, ElementExporter<Element>> exporters = getExporters();


    /**
     * (non-Javadoc).
     * 
     * @see br.org.archimedes.interfaces.Exporter#exportDrawing(br.org.archimedes.interfaces.Drawing,
     *      java.io.OutputStream)
     */
    public void exportDrawing (Drawing drawing, OutputStream output)
            throws IOException {

        String charset = "UTF-8"; //$NON-NLS-1$
        // TODO Forçar locale

        StringBuilder drawingTag = new StringBuilder(
                "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$

        drawingTag
                .append("<drawing xmlns=\"http://www.archimedes.org.br/xml/FileXMLSchema\" " //$NON-NLS-1$
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " //$NON-NLS-1$
                        + "xsi:schemaLocation=\"http://www.archimedes.org.br/xml/FileXMLSchema FileXMLSchema.xsd\">" //$NON-NLS-1$
                        + "\n");
        drawingTag.append("\t<zoom>"); //$NON-NLS-1$
        drawingTag.append(drawing.getZoom());
        drawingTag.append("</zoom>" + "\n"); //$NON-NLS-1$
        drawingTag.append("\t<viewport>"); //$NON-NLS-1$

        Point viewportPosition = drawing.getViewportPosition();
        drawingTag.append("\t" + "\t"
                + "<point x=\"" + viewportPosition.getX() + "\" y=\"" //$NON-NLS-1$ //$NON-NLS-2$
                + viewportPosition.getY() + "\" />" + "\n"); //$NON-NLS-1$
        drawingTag.append("\t" + "</viewport>" + "\n"); //$NON-NLS-1$

        output.write(drawingTag.toString().getBytes(charset));

        byte[] endContainerTagBytes = ("\t" + "</container>" + "\n")
                .getBytes(charset);

        for (Layer layer : drawing.getLayerMap().values()) {
            StringBuilder containerTag = new StringBuilder();
            containerTag.append("\t" + "<container name=\"" + layer.getName() //$NON-NLS-1$
                    + "\" lineStyle=\"" + layer.getLineStyle().ordinal() //$NON-NLS-1$
                    + "\" thickness=\"" + layer.getThickness() + "\" >" + "\n"); //$NON-NLS-1$ //$NON-NLS-2$
            writeColor(containerTag, layer.getColor());

            output.write(containerTag.toString().getBytes(charset));

            for (Element element : layer.getElements()) {
                ElementExporter<Element> exporter = exporters.get(element
                        .getClass().getCanonicalName());
                exporter.exportElement(element, output);
            }

            output.write(endContainerTagBytes);
        }
        output.write(("</drawing>" + "\n").getBytes(charset));
        output.close();
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param color
     *            The color that should be written
     */
    private void writeColor (StringBuilder builder, Color color) {

        builder.append("\t" + "\t" + "<color>" + "\n"); //$NON-NLS-1$
        writeComponent(builder, color.getRed());
        writeComponent(builder, color.getGreen());
        writeComponent(builder, color.getBlue());
        builder.append("\t" + "\t" + "</color>" + "\n"); //$NON-NLS-1$
    }

    /**
     * @param builder
     *            The stringbuilder that should be used to write
     * @param component
     *            The component to be written
     */
    private void writeComponent (StringBuilder builder, int component) {

        builder.append("\t" + "\t" + "\t" + "<unsignedByte>"); //$NON-NLS-1$
        builder.append(component);
        builder.append("</unsignedByte>" + "\n"); //$NON-NLS-1$
    }

    /**
     * inicializa o Map est�tico de
     * 
     * @return The map
     */
    @SuppressWarnings("unchecked")
    private static Map<String, ElementExporter<Element>> getExporters () {

        Map<String, String> idToClassNameMap = getIdToClassNameMap();

        IExtensionPoint exporterExtension = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.elementExporter");
        Map<String, ElementExporter<Element>> exporters = new HashMap<String, ElementExporter<Element>>();

        if (exporterExtension != null) {
            IExtension[] extensions = exporterExtension.getExtensions();
            for (IExtension extension : extensions) {

                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {

                    if ("exporter".equals(element.getName())) {
                        String exportType = element.getAttribute("extension");
                        if ("xml".equals(exportType)) {
                            String elementId = element
                                    .getAttribute("elementId");

                            ElementExporter<Element> exporter;
                            try {
                                exporter = (ElementExporter<Element>) element
                                        .createExecutableExtension("class");
                                exporters.put(idToClassNameMap.get(elementId),
                                        exporter);
                            }
                            catch (CoreException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }
            }
        }

        return exporters;
    }

    /**
     * @return A Map associating id to the class name of the element.
     */
    private static Map<String, String> getIdToClassNameMap () {

        IExtensionPoint elementExtension = Platform.getExtensionRegistry()
                .getExtensionPoint("br.org.archimedes.element");

        Map<String, String> elements = new HashMap<String, String>();

        if (elementExtension != null) {
            IExtension[] extensions = elementExtension.getExtensions();
            for (IExtension extension : extensions) {

                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement element : configElements) {
                    if ("element".equals(element.getName())) {
                        String id = element.getAttribute("id");
                        String clazz = element.getAttribute("class");
                        elements.put(id, clazz);
                    }
                }
            }
        }
        return elements;
    }
}
