package br.org.archimedes.io.dxf.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.circle.Circle;
import br.org.archimedes.io.dxf.DXFImporter;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;

public class DXFImporterTests {
	
	private FileInputStream file;
	private DXFImporter importer;

	@Before
	public void setUp() throws IOException {
		//TODO colocar um arquivo dxf que contenha apenas um circulo (esse tem uma elipse tambem).
		file = new FileInputStream(new File("./files/circleTest.dxf"));
		importer = new DXFImporter();
	}
	
	@Test
	public void shouldAddParsedCircleToAnArchimedesLayer() throws Exception {
		Drawing drawing = importer.importDrawing(file);
		Collection<Element> elements = drawing.getCurrentLayer().getElements();
		
		assertEquals(1, drawing.getLayerMap().values().size());
		assertEquals(1, drawing.getCurrentLayer().getElements().size());
		
		for (Element element : elements) {
			assertEquals(Circle.class, element.getClass());
		}
	}
}
