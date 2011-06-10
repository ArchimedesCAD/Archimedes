package br.org.archimedes.io.dxf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.kabeja.dxf.DXFColor;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.io.dxf.parsers.ElementParser;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

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
		
		Drawing drawing = new Drawing("Imported drawing", importedLayers, true);
		//Collection<Element> elements = importedLayers.get(dxfLayer0.getName()).getElements();

		return drawing;
	}

	private Color getLayerColor(DXFLayer dxfLayer) {
	    
	    //The default color is black
	    int rgb[] = {0, 0, 0};
	    String rgbColor = DXFColor.getRGBString(dxfLayer.getColor());
	    
	    if (rgbColor != null) {
    	    int i = 0;
            for (String s : rgbColor.split(",")) 
                rgb[i++] = Integer.valueOf(s);
	    }
	    return new Color(rgb[0], rgb[1], rgb[2]);
	    
	}
	
	private Layer addParsedElementsFrom(DXFLayer dxfLayer) {
	    
	    Color layerBackgroundColor = getLayerColor(dxfLayer);
	    Layer archLayer = new Layer(layerBackgroundColor, dxfLayer.getName(), LineStyle.CONTINUOUS, dxfLayer.getLineWeight());
		Collection<ElementParser> parsers = ElementParser.getParserMap().values();
		
		for (ElementParser parser : parsers) {
		    System.out.println("Classe " + parser.getClass().getName());
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
