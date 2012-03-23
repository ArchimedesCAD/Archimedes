package br.org.archimedes.io.dxf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.kabeja.dxf.DXFBlock;
import org.kabeja.dxf.DXFColor;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.gui.opengl.Color;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.io.dxf.parsers.BlockParser;
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
		
		parseBlocksFrom(dxfLayer);
			
		do {
			Layer archLayer = addParsedElementsFrom(dxfLayer);
			
			importedLayers.put(dxfLayer.getName(), archLayer);
			
			i++;
			dxfLayer = kabejaParser.getDocument().getDXFLayer(i.toString());
		} while (!dxfLayer0.equals(dxfLayer));
		
		
		Drawing drawing = new Drawing("Imported drawing", importedLayers, true);

		return drawing;
	}

	private void parseBlocksFrom(DXFLayer dxfLayer) {
		Iterator blockIterator = kabejaParser.getDocument().getDXFBlockIterator();
		
		while (blockIterator.hasNext()) {
			DXFBlock block = (DXFBlock) blockIterator.next();
			
			dxfLayer = (new BlockParser()).createLayerWithoutBlocksFrom(block);
			
			Layer archLayerWithoutBlocks = addParsedElementsFrom(dxfLayer);
			
			importedLayers.put(dxfLayer.getName(), archLayerWithoutBlocks);
		}
	}

	private Color getInvertedBackgroundColor() {
		Color bgColor = Utils.getWorkspace().getBackgroundColor();
	    return new Color(255 - bgColor.getRed(), 255 - bgColor.getGreen(), 255 - bgColor.getBlue());
	}
	
	private Layer addParsedElementsFrom(DXFLayer dxfLayer) {
	    
	    Color layerBackgroundColor = getInvertedBackgroundColor();
	    Layer archLayer = new Layer(layerBackgroundColor, dxfLayer.getName(), LineStyle.CONTINUOUS, dxfLayer.getLineWeight());
	    archLayer.setPrintColor(new Color(0, 0, 0));
		Collection<ElementParser> parsers = ElementParser.getParserMap().values();
		
		for (ElementParser parser : parsers) {
			try {
				for (Element element : parser.parse(dxfLayer)) {
					try {
						archLayer.putElement(element);
					} catch (IllegalActionException e) {
						System.out.println("Elemento parseado já está na Layer e não será inserido novamente.");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return archLayer;
	}

}
