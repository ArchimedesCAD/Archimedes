package br.org.archimedes.io.dxf.parsers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kabeja.dxf.DXFConstants;
import org.kabeja.dxf.DXFEllipse;
import org.kabeja.dxf.DXFLayer;
import org.kabeja.dxf.helpers.Vector;

import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;


public class EllipseParser extends ElementParser{

    @Override
    public Collection<Element> parse(DXFLayer layer) throws NullArgumentException, InvalidArgumentException {
        
        Collection<Element> archimedesEllipses = new ArrayList<Element>();
        @SuppressWarnings("unchecked")
        List<DXFEllipse> dxfEllipses = layer.getDXFEntities(DXFConstants.ENTITY_TYPE_ELLIPSE);
        
        if(dxfEllipses != null) {
            for (DXFEllipse dxfEllipse : dxfEllipses) {
                org.kabeja.dxf.helpers.Point centerPoint = dxfEllipse.getCenterPoint();
                Point archimedesCenter = transformToArchimedesPoint(centerPoint);
                Point widthPoint = transformToArchimedesPoint(centerPoint);
                Point heightPoint = transformToArchimedesPoint(centerPoint);
                
                Vector majorAxis = dxfEllipse.getMajorAxisDirection();
                double l = dxfEllipse.getHalfMajorAxisLength();
                majorAxis.normalize();
                majorAxis.setX(majorAxis.getX() * l);
                majorAxis.setY(majorAxis.getY() * l);
                
                widthPoint.move(majorAxis.getX(), majorAxis.getY());
                
                double ratio = dxfEllipse.getRatio();
                
                heightPoint.move(majorAxis.getY() * ratio, (-1.0) * majorAxis.getX() * ratio);
                
                archimedesEllipses.add(new Ellipse(archimedesCenter, widthPoint, heightPoint));
                
            }
        }
        return archimedesEllipses;
    }
    
}
