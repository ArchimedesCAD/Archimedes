package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import br.org.archimedes.Constant;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;


public class EllipseInfiniteLineIntersector implements Intersector {

    public Collection<Point> getIntersections (Element element, Element otherElement)
            throws NullArgumentException {
        if (element == null || otherElement == null)
            throw new NullArgumentException();

        Ellipse ellipse;
        InfiniteLine infiniteLine;
        if (element.getClass() == Ellipse.class) {
            infiniteLine = (InfiniteLine) otherElement;
            ellipse = (Ellipse) element;
        }
        else {
            infiniteLine = (InfiniteLine) element;
            ellipse = (Ellipse) otherElement;
        }

        Collection<Point> intersectionPoints = new LinkedList<Point>();

        Point ending = infiniteLine.getEndingPoint();
        Point starting = infiniteLine.getInitialPoint();
        
        Double aReta = (ending.getY() - starting.getY()) / (ending.getX() - starting.getX());
        Double bReta = ending.getY() - aReta*(ending.getX());
                
        double aEllipse = ellipse.getCenter().calculateDistance(ellipse.getHeightPoint());
        double bEllipse = ellipse.getCenter().calculateDistance(ellipse.getWidthPoint());
        
        if(aEllipse < bEllipse){
        	double temp = aEllipse;
        	aEllipse = bEllipse;
        	bEllipse = temp;
        }
        System.out.println("aEllipse: "+aEllipse+" bEllipse: "+bEllipse);
        
        
        //System.out.println("ending.getY(): "+ending.getY()+" starting.getY(): "+starting.getY());
        //System.out.println("ending.getX(): "+ending.getX()+" starting.getX(): "+starting.getX());
        System.out.println("aReta: "+aReta+" bReta: "+bReta);
        
        if(aReta.isInfinite()){
        	Double x = starting.getX();
        	Double y = Math.sqrt( (1 - (x - ellipse.getCenter().getX())*(x - ellipse.getCenter().getX()) / (aEllipse*aEllipse) ) * (bEllipse*bEllipse) ) + ellipse.getCenter().getY();
        	//System.out.println("x: "+x+" y: "+y);
        	if(!y.isNaN()){
        		Point p = new Point(x,y);
        		intersectionPoints.add(p);
        	}
        	return intersectionPoints;
        }
        
        ArrayList<Point> focusPoints = (ArrayList<Point>) ellipse.calculateFocusPoints();
        
        Point Fa = focusPoints.get(0);
        Point Fb = focusPoints.get(1);
        
        
        double x0 = ellipse.getCenter().getX();
        double y0 = ellipse.getCenter().getY();
        System.out.println("x0: "+x0+" y0: "+y0);
        double A = bEllipse*bEllipse + aEllipse*aEllipse*aReta*aReta;
        double B = -2*x0*bEllipse*bEllipse + 2*aEllipse*aEllipse*aReta*bReta - 2*aReta*y0*aEllipse*aEllipse;
        double C = bEllipse*bEllipse*x0*x0 + aEllipse*aEllipse*bReta*bReta - 2*bReta*y0*aEllipse*aEllipse + aEllipse*aEllipse*y0*y0 - aEllipse*aEllipse*bEllipse*bEllipse; 
        System.out.println("A: "+A+" B: "+B+" C:"+C);
        /*double A = 2.0 + 2*(aReta*aReta);
        double B = -2.0 * (Fa.getX() + Fb.getX() + Fa.getY() * aReta + Fb.getY() * aReta - 2 * aReta * bReta);
        double v = Fa.calculateDistance(Fb);
        double C = Fa.getY() * Fa.getY() - 2 * Fa.getY() * bReta + 2 * bReta * bReta + Fb.getY() * Fb.getY() + Fa.getX() * Fa.getX() + Fb.getX() * Fb.getX() - 2 * Fb.getY() * bReta - v  * v;
        */
        
        try {
            Collection<Double> solutions = solve(A, B, C);
            for(Double sol : solutions) {
				Point p = new Point(sol,aReta*sol + bReta);
				intersectionPoints.add(p);
			}
        }
        catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
        
        System.out.println("FIM");
        System.out.println();
        return intersectionPoints;
    }

    private Collection<Double> solve(double a, double b, double c) throws InvalidArgumentException
    {
    	ArrayList<Double> solutions = new ArrayList<Double>();
    	if (Math.abs(a) <= 0)
            throw new InvalidArgumentException();
    	
        Double delta = delta(a, b, c);
        System.out.println("nosso delta: "+delta);
        if (delta.isNaN() || delta < 0)
        	return solutions;
        
        solutions.add((-b + Math.sqrt(delta))/2*a);
        
        if (delta != 0){
            solutions.add((-b - Math.sqrt(delta))/2*a);
            System.out.println("Delta2 "+ ((-b - Math.sqrt(delta))/2*a));
        }
        
        return solutions;
    }
    
    private double delta(double a, double b, double c)
    {
        return b*b - 4*a*c;
    }
    
}
