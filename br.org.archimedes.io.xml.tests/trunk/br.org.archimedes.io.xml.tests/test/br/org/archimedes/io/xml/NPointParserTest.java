package br.org.archimedes.io.xml;

import static junit.framework.Assert.assertNull;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import br.org.archimedes.io.xml.parsers.ElementParser;
import br.org.archimedes.model.Element;

/**
 * @author vidlopes
 * 
 */
public class NPointParserTest {
	protected ElementParser parser;

	protected Node getNodeLine(String xmlSample) throws Exception {
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
	 * @param XML_LINE
	 * @throws Exception
	 */
	protected void testFail(final String XML_LINE) throws Exception {
		Node nodeLine = this.getNodeLine(XML_LINE);
		Element element = parser.parse(nodeLine);

		assertNull(element);
	}
}
