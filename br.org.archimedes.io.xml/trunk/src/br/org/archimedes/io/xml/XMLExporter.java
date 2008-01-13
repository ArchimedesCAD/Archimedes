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
	
	
	private static Map<String, ElementExporter<Element>> EXPORTERS; 
	
    /**
     * Constructor.<BR>
     * Registers the existing XML exporter for all elements.
     */
    public XMLExporter () {

    }

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
                "<?xml version=\"1.0\" encoding=\"" + charset + "\"?>" + '\n'); //$NON-NLS-1$ //$NON-NLS-2$
        drawingTag
                .append("<drawing xmlns=\"http://www.archimedes.org.br/xml/FileXMLSchema\" " //$NON-NLS-1$
                        + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " //$NON-NLS-1$
                        + "xsi:schemaLocation=\"http://www.archimedes.org.br/xml/FileXMLSchema FileXMLSchema.xsd\">" //$NON-NLS-1$
                        + '\n');
        drawingTag.append("\t<zoom>"); //$NON-NLS-1$
        drawingTag.append(drawing.getZoom());
        drawingTag.append("</zoom>" + '\n'); //$NON-NLS-1$
        drawingTag.append("\t<viewport>"); //$NON-NLS-1$
        Point viewportPosition = drawing.getViewportPosition();
        drawingTag.append("<point x=\"" + viewportPosition.getX() + "\" y=\"" //$NON-NLS-1$ //$NON-NLS-2$
                + viewportPosition.getY() + "\" />"); //$NON-NLS-1$
        drawingTag.append("</viewport>" + '\n'); //$NON-NLS-1$
        for (Layer layer : drawing.getLayerMap().values()) {
            drawingTag.append("\t<container name=\"" + layer.getName() //$NON-NLS-1$
                    + "\" lineStyle=\"" + layer.getLineStyle().ordinal() //$NON-NLS-1$
                    + "\" thickness=\"" + layer.getThickness() + "\" >\n"); //$NON-NLS-1$ //$NON-NLS-2$
            writeColor(drawingTag, layer.getColor());

            //precisamos escrever o conteudo atual no outrputStream
            //pois os exporters recebem o stream e escrevem diretamente nele
            //logo para nao prejudicar a ordem dos elementos vamos escrever
            output.write(drawingTag.toString().getBytes());
            drawingTag.delete(0, drawingTag.length());
            
            //devemos percorrer os elementos do Layer e achar o ElementExporter
            //correspondente.
            for (Element element : layer.getElements()) {
                //TODO onde fica o Map de formato para elemento para exporter?
            	ElementExporter<Element> exporter = getEXPORTERS().get(element.getClass().getCanonicalName());
            	exporter.exportElement(element, output);
			}
            
            
            drawingTag.append("\t</container>");
        }
        drawingTag.append("</drawing>\n");
        output.write(drawingTag.toString().getBytes(charset));
        output.close();
    }

    /**
     * @param drawingTag
     *            The stringbuilder that should be used to write
     * @param color
     *            The color that should be written
     */
    private void writeColor (StringBuilder drawingTag, Color color) {

        drawingTag.append("<color>"); //$NON-NLS-1$
        writeComponent(drawingTag, color.getRed());
        writeComponent(drawingTag, color.getGreen());
        writeComponent(drawingTag, color.getBlue());
        drawingTag.append("</color>"); //$NON-NLS-1$
    }

    /**
     * @param drawingTag
     *            The stringbuilder that should be used to write
     * @param component
     *            The component to be written
     */
    private void writeComponent (StringBuilder drawingTag, int component) {

        drawingTag.append("<unsignedByte>"); //$NON-NLS-1$
        drawingTag.append(component);
        drawingTag.append("</unsignedByte>"); //$NON-NLS-1$
    }

    //FIXME Descobrir o que é isso e se ainda é necessário
	private Map<String, ElementExporter<Element>> getEXPORTERS() {
		if (EXPORTERS == null) {
			initEXPORTERS();
		}
		return EXPORTERS;
	}

	/**
	 * inicializa o Map est�tico de 
	 *
	 */
	@SuppressWarnings("unchecked")
	private void initEXPORTERS() {
		
		IExtensionPoint elementExtension = Platform.getExtensionRegistry()
			.getExtensionPoint("br.org.archimedes.element");
		IExtensionPoint exporterExtension = Platform.getExtensionRegistry()
		.getExtensionPoint("br.org.archimedes.elementExporter");
		
		//vamos criar um Map auxiliar de String para Class
		//este associa um Class de um Element ao seu ID
		Map<String, String> elements = new HashMap<String, String>();		
		if (elementExtension != null) {
			IExtension[] extensions = elementExtension.getExtensions();
			for (IExtension extension : extensions) {

				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement element : configElements) {
					//aqui vou pegar o id do element e seu class
					if("element".equals(element.getName())) {
						String id = element.getAttribute("id");
						String clazz = element.getAttribute("class");
						elements.put(id, clazz);
					}
				}
			}
		}
		
		//agora, vamos criar o Map que associa um nome canonico de class a um exporter
		EXPORTERS = new HashMap<String, ElementExporter<Element>>();
		if (exporterExtension != null) {
			IExtension[] extensions = exporterExtension.getExtensions();
			for (IExtension extension : extensions) {

				IConfigurationElement[] configElements = extension
						.getConfigurationElements();
				for (IConfigurationElement element : configElements) {
					//aqui vou pegar o id do element e seu class
					if("exporter".equals(element.getName())) {						
						String exportType = element.getAttribute("extension");
						if ("xml".equals(exportType)) {
							String elementId = element.getAttribute("elementId");
														
							ElementExporter<Element> exporter;
							try {								
								exporter = (ElementExporter<Element>) element.createExecutableExtension("class");
								EXPORTERS.put(elements.get(elementId), exporter);
							} catch (CoreException e) {							
								e.printStackTrace();
							}
							
							
						}
						
					}
				}
			}
		}
	}
}
