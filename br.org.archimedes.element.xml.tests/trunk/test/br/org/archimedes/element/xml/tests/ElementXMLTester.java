package br.org.archimedes.element.xml.tests;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.interfaces.ElementExporter;

/**
 * Classe b�sica para testes de exporta��o de XML.
 * 
 * @author eclipse
 */
public class ElementXMLTester extends Tester {

	/**
	 * Creates a DOM tree for the XML Element Exporter.
	 * @param <T> Type for the element exporter. 
	 * @param exporter the Element exporter.
	 * @param element the Element.
	 * @return The DOM tree for the XML Element Exporter.
	 */
	public <T extends br.org.archimedes.model.Element> Document getElementXML(
			ElementExporter<T> exporter, T element) {

		OutputStream out = new ByteArrayOutputStream();
		try {
			exporter.exportElement(element, out);

			String result = out.toString();
			out.close();

			StringReader sr = new StringReader(result);
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document doc = parser.parse(new InputSource(sr));

			return doc;
		} catch (Exception ex) {
			Assert.fail("Should not throw IOException");
		}
		return null;
	}

	/**
	 * Verifies if the difference os v1 and expected is delimited by a delta.
	 * 
	 * @param expected
	 *            Value expected.
	 * @param v1
	 *            Value to be tested.
	 * @return True if delimited by a delta, False otherwise.
	 */
	public boolean isLimitedByDelta(double expected, String v1) {
		double value = Double.parseDouble(v1);
		return Math.abs(value - expected) <= Constant.EPSILON;
	}
}
