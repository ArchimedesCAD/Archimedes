package br.org.archimedes.io.dxf.parsers.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.org.archimedes.io.dxf.parsers.CircleParser;
import br.org.archimedes.io.dxf.parsers.ElementParser;

public class ElementParserTests {
	
	@Test
	public void shouldReturnCircleParser() throws Exception {
		ElementParser parser = ElementParser.getParser("circle");
		assertEquals(CircleParser.class, parser.getClass());
	}

}
