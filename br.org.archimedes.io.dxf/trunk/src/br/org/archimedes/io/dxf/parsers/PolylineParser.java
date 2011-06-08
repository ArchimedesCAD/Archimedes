package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.DXFPolyline;
import org.kabeja.dxf.DXFVertex;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;

public class PolylineParser extends ElementParser {

    @Override
    public Collection<Element> parse (DXFLayer layer) throws NullArgumentException,
            InvalidArgumentException {
        List<DXFPolyline> dxfAllPolylines = new ArrayList<DXFPolyline>();
        
        List<DXFPolyline> dxfPolylines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_POLYLINE);
        
        List<DXFPolyline> dxfLWPolylines = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_LWPOLYLINE);
        
        if (dxfPolylines != null)
            dxfAllPolylines.addAll(dxfPolylines);
        
        if (dxfLWPolylines != null)
            dxfAllPolylines.addAll(dxfLWPolylines);
            
        Collection<Element> archimedesPolylines = new ArrayList<Element>();
        for (DXFPolyline dxfPolyline : dxfAllPolylines) {
            List<Point> points = new ArrayList<Point>();
            for (int i = 0; i < dxfPolyline.getVertexCount(); i++) {
                DXFVertex vertex = dxfPolyline.getVertex(i);
                points.add(transformToArchimedesPoint(vertex.getPoint()));
            }
            points.add(transformToArchimedesPoint(dxfPolyline.getVertex(0).getPoint()));
            archimedesPolylines.add(new Polyline(points));
        }
        return archimedesPolylines;
    }
    
}
