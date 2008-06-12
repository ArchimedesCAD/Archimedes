
package br.org.archimedes.io.xml.parsers;

import static junit.framework.Assert.assertNull;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.org.archimedes.exceptions.ElementCreationException;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 */
public class NPointParserTest {

    protected ElementParser parser;


    protected Node getNodeLine (String xmlSample) throws Exception {

        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = null;

        docBuilder = docBuilderFactory.newDocumentBuilder();

        StringReader ir = new StringReader(xmlSample);
        InputSource is = new InputSource(ir);

        Document doc = docBuilder.parse(is);
        doc.normalize();

        return doc.getFirstChild();
    }

    /**
     * @param xml_code
     *            The xml to be parsed
     * @throws Exception
     *             Thrown if something unexpected happens
     */
    protected void testFail (final String xml_code) throws Exception {

        Node nodeLine = this.getNodeLine(xml_code);
        Element element = null;
        try {
            element = parser.parse(nodeLine);
        }
        catch (ElementCreationException e) {
            // OK
        }

        assertNull(element);
    }
}
