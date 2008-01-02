package br.org.archimedes.io.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

import br.org.archimedes.io.xml.XMLExporterHelper;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

/**
 * Testes do XMLExporterHelper
 * 
 * @author julien
 */
public class XMLExporterHelperTest {

	/**
	 * Testa para write(Point, OutputStream)
	 * @throws IOException Se fracassar...
	 *
	 */
	@Test
	public void testPoint() throws IOException {
		Point point = new Point(0, 0);
		OutputStream output = new ByteArrayOutputStream();
		
		XMLExporterHelper.write(point, output);
		String result = output.toString();		
		Assert.assertEquals("<point x=\"0.0\" y=\"0.0\" />", result);
	}
	
	/**
	 * Testa para write(Point, String, OutputStream)
	 * @throws IOException Se fracassar...
	 *
	 */
	@Test
	public void testCustomPoint() throws IOException {
		Point point = new Point(0, 0);
		OutputStream output = new ByteArrayOutputStream();
		
		XMLExporterHelper.write(point, "lalala", output);
		String result = output.toString();		
		Assert.assertEquals("<lalala x=\"0.0\" y=\"0.0\" />", result);
	}
	
	/**
	 * Testa para write(Vector, OutputStream)
	 * @throws IOException Se fracassar...
	 *
	 */
	@Test
	public void testVector() throws IOException {
		Point point = new Point(1, 1);
		Vector vector = new Vector(point);  
		
		OutputStream output = new ByteArrayOutputStream();
		
		XMLExporterHelper.write(vector, output);
		String result = output.toString();
		Assert.assertEquals("<vector>\n\t<point x=\"1.0\" y=\"1.0\" />\n</vector>", result);
	}
	
	/**
	 * Testa para write(Vector, String, OutputStream)
	 * @throws IOException Se fracassar...
	 *
	 */
	@Test
	public void testCustomVector() throws IOException {
		Point point = new Point(1, 1);
		Vector vector = new Vector(point);  
		
		OutputStream output = new ByteArrayOutputStream();
		
		XMLExporterHelper.write(vector, "lalala", output);
		String result = output.toString();
		Assert.assertEquals("<lalala>\n\t<point x=\"1.0\" y=\"1.0\" />\n</lalala>", result);
	}
	
}
