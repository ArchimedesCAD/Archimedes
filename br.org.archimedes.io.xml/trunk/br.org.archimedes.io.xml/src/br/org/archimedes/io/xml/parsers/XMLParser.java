// TODO: salvar o ROTATE
/*
 * Created on 11/07/2006
 */

package br.org.archimedes.io.xml.parsers;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.org.archimedes.gui.rca.Activator;
import br.org.archimedes.gui.rca.Messages;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.Point;

/**
 * Belongs to package com.tarantulus.archimedes.xml.
 * 
 * @author fernandorb
 */
public class XMLParser {

    /**
     * Parse and Archimedes XML file
     * 
     * @param xmlInputStream
     *            The File to be Parsed
     * @return Returns the Drawing of the xmlFile or null if the file is
     *         invalid.
     */
    public Drawing parse (InputStream xmlInputStream) {

        Document doc = getDocument(xmlInputStream);
        Drawing drawing = null;
        if (doc != null && validadeXMLSchema(doc)) {
            NodeList windowList = doc.getFirstChild().getChildNodes();
            LayerParser parser = new LayerParser();

            Map<String, Layer> layerMap = new HashMap<String, Layer>();
            Double zoom = null;
            Point viewPoint = null;
            for (int i = 0; i < windowList.getLength(); i++) {
                Node parsingNode = windowList.item(i);

                if (parsingNode.getNodeName().equals("container")) { //$NON-NLS-1$
                    Layer layer = parser.parse(parsingNode);
                    if (layerMap.get(layer.getName()) == null) {
                        layerMap.put(layer.getName(), layer);
                    }
                    else {
                        Layer sameNameLayer = layerMap.get(layer.getName());
                        for (Element element : layer.getElements()) {
                            try {
                                sameNameLayer.putElement(element);
                            }
                            catch (Exception e) {
                                // ignores
                            }
                        }
                    }
                }
                else if (parsingNode.getNodeName().equals("zoom")) { //$NON-NLS-1$
                    zoom = XMLUtils.nodeToDouble(parsingNode);
                }
                else if (parsingNode.getNodeName().equals("viewport")) { //$NON-NLS-1$
                    NodeList list = ((org.w3c.dom.Element) parsingNode)
                            .getElementsByTagName("point"); //$NON-NLS-1$
                    if (list.getLength() > 0)
                        viewPoint = XMLUtils.nodeToPoint(list.item(0));
                }
            }

            // TODO verificar como fazer
            drawing = new Drawing(Messages.NewDrawingName, layerMap);
            drawing.setZoom(zoom);
            drawing.setViewportPosition(viewPoint);
            // drawing.setFile(xmlInputStream);
            drawing.setSaved(true);
        }

        return drawing;
    }

    /**
     * @param xmlInputStream
     *            The file to be parsed
     * @return The document
     */
    private Document getDocument (InputStream xmlInputStream) {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = null;
        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException e) {
            // The "Serious" configuration error, should never Happen
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = docBuilder.parse(xmlInputStream);
            doc.normalize();
        }
        catch (SAXException e) {
            // Can happen if the file is invalid
        }
        catch (IOException e) {
            // Can happen if it is the wrong file type
        }
        return doc;
    }

    /**
     * Validades a XML file based on his xsd Schema
     * 
     * @param doc
     *            The document to be validated
     * @return True if the file is valid, false otherwise
     */
    private boolean validadeXMLSchema (Document doc) {

        SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = null;

        try {
        	InputStream fileInput = Activator.locateFile("FileXMLSchema.xsd",br.org.archimedes.io.xml.Activator.getDefault().getBundle()); //$NON-NLS-1$
            Source schemaFile = new StreamSource(fileInput);
            schema = factory.newSchema(schemaFile);
            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(doc));
        }
        catch (SAXException e) {
            // Happens if the XML is not valid
            return false;
        }
        catch (IOException e) {
            // This should never happen
            e.printStackTrace();
        }

        return true;
    }
}
