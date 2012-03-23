package br.org.archimedes.io.dxf.parsers.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import br.org.archimedes.io.dxf.parsers.PolylineParser;
import br.org.archimedes.model.Element;
import br.org.archimedes.polyline.Polyline;

public class PolylineParserTests {
    
    private PolylineParser polylineParser;
    private FileInputStream file;

    @Before
    public void setUp() throws IOException {
        polylineParser = new PolylineParser();
        file = new FileInputStream(new File("./files/polylineTest.dxf"));
    }
    
    @Test
    public void shouldParsePolyline() throws Exception {
        DXFLayer layer = createDXFLayer();
        
        Collection<Element> parse = polylineParser.parse(layer);
        assertEquals(1, parse.size());
        Element[] elements = parse.toArray(new Element[0]);
        assertTrue(elements[0].getClass().equals(Polyline.class));
    }
    
    private DXFLayer createDXFLayer() throws ParseException {
        Parser kabejaParser = ParserBuilder.createDefaultParser();
        kabejaParser.parse(file, DXFParser.DEFAULT_ENCODING);
        
        DXFLayer layer = kabejaParser.getDocument().getDXFLayer("0");
        return layer;
    }
}


