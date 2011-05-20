package br.org.archimedes.io.dxf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.io.dxf.parsers.CircleParser;
import br.org.archimedes.io.dxf.parsers.ElementParser;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;
import br.org.archimedes.gui.opengl.Color;

public class DXFImporter implements Importer {
	
	Parser kabejaParser = ParserBuilder.createDefaultParser();
	Map<String, Layer> importedLayers = new HashMap<String, Layer>();
		
	@Override
	public Drawing importDrawing(InputStream input)
			throws InvalidFileFormatException, IOException {
		Integer i = 0;
		
		try {
			kabejaParser.parse(input, DXFParser.DEFAULT_ENCODING);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		DXFLayer dxfLayer0 = kabejaParser.getDocument().getDXFLayer("0");
		DXFLayer dxfLayer = dxfLayer0;
		
		do {
			Layer archLayer = addParsedElementsFrom(dxfLayer);
			
            importedLayers.put(dxfLayer.getName(), archLayer);
			
			i++;
			dxfLayer = kabejaParser.getDocument().getDXFLayer(i.toString());
		} while (!dxfLayer0.equals(dxfLayer));
		
		return new Drawing("Imported drawing", importedLayers);
	}

	private Layer addParsedElementsFrom(DXFLayer dxfLayer) {
		Layer archLayer = new Layer(new Color(255,255,255), dxfLayer.getName(), LineStyle.CONTINUOUS, dxfLayer.getLineWeight());
		Collection<ElementParser> parsers = ElementParser.getParserMap().values();
		for (ElementParser parser : parsers) {
			try {
				for (Element element : parser.parse(dxfLayer)) {
					archLayer.putElement(element);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return archLayer;
	}

}
