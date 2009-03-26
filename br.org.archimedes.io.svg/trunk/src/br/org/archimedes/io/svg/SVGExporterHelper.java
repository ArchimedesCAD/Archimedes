package br.org.archimedes.io.svg;

import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;


public class SVGExporterHelper {
    private Rectangle documentArea;
    private double zoom;
    
    public SVGExporterHelper (Rectangle documentArea) {
        this.documentArea = documentArea;
    }
    
    public static String svgFor (final Point point) {

        int x, y;
        x = (int) point.getX();
        y = (int) point.getY();
        
        return String.format("%d,%d", x, -y);
    }
}
