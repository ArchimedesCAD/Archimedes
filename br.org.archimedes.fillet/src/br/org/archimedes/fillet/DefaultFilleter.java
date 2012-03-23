
package br.org.archimedes.fillet;

import java.util.ArrayList;
import java.util.Collection;


import java.util.List;


import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Filletable;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.rcp.extensionpoints.ExtendManagerEPLoader;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

public class DefaultFilleter implements Filleter {

    private IntersectionManager intersectionManager;

    private ExtendManager extendManager;

	private double radius;


    public DefaultFilleter (double radius) {
    	this.radius = radius;
        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();
        extendManager = new ExtendManagerEPLoader().getExtendManager();
    }

    public List<UndoableCommand> fillet (Element e1, Point click1, Element e2, Point click2) {
        return filletOpenElements(e1, click1, e2, click2);
        
    }
    
    
    class CircleInformation {
    	public Point center;
    	public Point intersectionWithElement1;
    	public Point intersectionWithElement2;
    	
    	public CircleInformation(Point center, Point intersectionWithElement1, Point intersectionWithElement2) {
    		this.center = center;
    		this.intersectionWithElement1 = intersectionWithElement1;
    		this.intersectionWithElement2 = intersectionWithElement2;
    	}
    }
    
    private CircleInformation filletCircleCenter(Point click1, Point click2, Point intersection, double radius) {
    	
    	Vector direction1 = (new Vector(intersection, click1)).normalized();
    	Vector direction2 = (new Vector(intersection, click2)).normalized();
    	
    	Vector bissectrix = direction1.clone(); 
    	bissectrix.add(direction2);
    	bissectrix = bissectrix.normalized();
    	
    	double angle = Math.acos(direction1.dotProduct(direction2));
    	angle = angle/2; 
    	
    	
    	Point center = intersection.addVector(bissectrix.multiply(radius/Math.sin(angle)));
    	Point intersectionWithElement1 = intersection.addVector(direction1.multiply(radius/Math.tan(angle)));
    	Point intersectionWithElement2 = intersection.addVector(direction2.multiply(radius/Math.tan(angle)));
    	
    	return new CircleInformation(center, intersectionWithElement1, intersectionWithElement2);
    	
    }
    
    
    private List<UndoableCommand> filletOpenElements (Element e1, Point click1, Element e2,
            Point click2) {
    	
    	
        Collection<Element> extensions1 = extendManager.getInfiniteExtensionElements(e1);
        Collection<Element> extensions2 = extendManager.getInfiniteExtensionElements(e2);
        

        Collection<Point> intersections = new ArrayList<Point>();
        List<UndoableCommand> fillet = new ArrayList<UndoableCommand>();

        try {
        	// TODO: Talvez priorizar interseções que estejam nos pŕoprios elementos (?)
        	intersections.addAll(intersectionManager.getIntersectionsBetween(e2, extensions1));
        	intersections.addAll(intersectionManager.getIntersectionsBetween(e1, e2));
        	intersections.addAll(intersectionManager.getIntersectionsBetween(e1, extensions2));
        	
            for (Element ext1 : extensions1) {
                intersections
                        .addAll(intersectionManager.getIntersectionsBetween(ext1, extensions2));
            }
            
            if (intersections.isEmpty()) { 
            	throw new InvalidArgumentException();
            }
            
            
            Point closestIntersection = null;
            double minDist = Double.MAX_VALUE;
            for (Point intersection : intersections) {
                double dist = Geometrics.calculateDistance(intersection, click1)
                        + Geometrics.calculateDistance(intersection, click2);
            
                if (dist < minDist) {
                    closestIntersection = intersection;
                    minDist = dist;
                }
            }
            
            
            Filletable elem1 = (Filletable) e1;
            Filletable elem2 = (Filletable) e2;  
            
            CircleInformation circleInfo;
            if (radius == 0) {
            	circleInfo = filletCircleCenter(elem1.getTangencyLinePoint(closestIntersection, click1), elem2.getTangencyLinePoint(closestIntersection, click2), closestIntersection, .5);
            	fillet.addAll(elem1.getFilletCommands(circleInfo.center, circleInfo.intersectionWithElement1, circleInfo.intersectionWithElement2, closestIntersection));
                fillet.addAll(elem2.getFilletCommands(circleInfo.center, circleInfo.intersectionWithElement2, circleInfo.intersectionWithElement1, closestIntersection));
            } else {
            	circleInfo = filletCircleCenter(elem1.getTangencyLinePoint(closestIntersection, click1), elem2.getTangencyLinePoint(closestIntersection, click2), closestIntersection, radius);
            	PutOrRemoveElementCommand arc = new PutOrRemoveElementCommand(new Arc(circleInfo.intersectionWithElement1, circleInfo.intersectionWithElement2, circleInfo.center, 
                		Geometrics.calculateSignedTriangleArea(circleInfo.center, circleInfo.intersectionWithElement1, circleInfo.intersectionWithElement2) > 0), false);
            	
            	fillet.add(arc);
            	fillet.addAll(elem1.getFilletCommands(circleInfo.center, circleInfo.intersectionWithElement1, circleInfo.intersectionWithElement2, null));
                fillet.addAll(elem2.getFilletCommands(circleInfo.center, circleInfo.intersectionWithElement2, circleInfo.intersectionWithElement1, null));
            }
            
            
            
        }
        catch (NullArgumentException e) {
            // will not reach here
        } catch (InvalidArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return fillet;
    }

}
