package br.org.archimedes.io.dxf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Display;
import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFViewport;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.ParseException;
import org.kabeja.parser.Parser;
import org.kabeja.parser.ParserBuilder;
import org.kabeja.parser.entities.DXFViewportHandler;

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
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;
import br.org.archimedes.model.Vector;
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
		
		Drawing drawing = new Drawing("Imported drawing", importedLayers);
		
		Collection<Element> elements = importedLayers.get(dxfLayer0.getName()).getElements();
		fixCenterView(drawing, elements);
				
		return drawing;
	}

    private void fixCenterView (Drawing drawing, Collection<Element> elements) {
        double maxX = Double.MIN_VALUE;
		double minX = Double.MAX_VALUE;
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;

		if(elements.size() > 1) {
    		for (Element element : elements) {
    		    for (Point p : element.getPoints()) {
    		        maxX = p.getX() > maxX ? p.getX() : maxX;
    		        minX = p.getX() < minX ? p.getX() : minX;
    		        
    		        maxY = p.getY() > maxY ? p.getY() : maxY;
    		        minY = p.getY() < minY ? p.getY() : minY;
    		    }
    		}
    		Point drawingCenter = new Point(minX + (maxX - minX)/2, minY + (maxY - minY)/2);
    		drawing.setViewportPosition(drawingCenter);
    		//TODO ver a implementacao do comando zoom
    		drawing.setZoom(1000*Math.max(maxX - minX, maxY - minY));
		}
		else if (elements.size() == 1) {
		    Element element = elements.iterator().next();
		    
		    Rectangle boundary = element.getBoundaryRectangle();
		    Point lowerLeft = boundary.getLowerLeft();
            Point upperRight = boundary.getUpperRight();
            Point lowerRight = boundary.getLowerRight();
            
            Vector vector = new Vector(lowerLeft, upperRight);
		    Vector normalized = vector.normalized();
		    vector = normalized.multiply(vector.getNorm()/2);
		    
		    Point center = lowerLeft;
		    center = center.addVector(vector);
		     		    
            drawing.setViewportPosition(center);
		}
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
