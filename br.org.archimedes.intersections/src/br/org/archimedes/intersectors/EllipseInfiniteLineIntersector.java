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
import br.org.archimedes.line.Line;
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

        //duplicate elements
        double fi = ellipse.getFi();
        Point oldCenter = ellipse.getCenter();
		InfiniteLine infiniteLineClone = (InfiniteLine) infiniteLine.clone();
		Ellipse ellipseClone = (Ellipse) ellipse.clone();
		
		//System.out.println("infiniteLine_point1XX: ("+infiniteLineClone.getInitialPoint().getX()+", "+infiniteLineClone.getInitialPoint().getY()+")");
        //System.out.println("infiniteLine_point2XX: ("+infiniteLineClone.getEndingPoint().getX()+", "+infiniteLineClone.getEndingPoint().getY()+")");
        
		//translate and rotate
		ellipseClone.rotate(ellipse.getCenter(), -fi);//ellipse.getCenter()
		infiniteLineClone.rotate(ellipse.getCenter(), -fi);//deve ser a origem!!! infiniteLine.getInitialPoint()    new Point(0,0) 
		ellipseClone.getWidthPoint().setY(ellipseClone.getCenter().getY());
		ellipseClone.getHeightPoint().setX(ellipseClone.getCenter().getX());
		//auxiliary variables
		Point starting = infiniteLine.getInitialPoint();
        Point ending = infiniteLine.getEndingPoint();
        Double aReta = (ending.getY() - starting.getY()) / (ending.getX() - starting.getX());
        Double bReta = ending.getY() - aReta*(ending.getX());
        //System.out.println("infiniteLine_point1: ("+starting.getX()+", "+starting.getY()+")");
        //System.out.println("infiniteLine_point2: ("+ending.getX()+", "+ending.getY()+")");
        System.out.println("aReta: "+aReta+" bReta: "+bReta);
        
        Point pInitial= infiniteLineClone.getInitialPoint();
        Point pFinal = infiniteLineClone.getEndingPoint();
        Double diffY = (pFinal.getY() - pInitial.getY()) ;
        Double diffX = (pFinal.getX() - pInitial.getX());
        if(Math.abs(diffY) <= 0.00001){
        	aReta = 0.0;
        	bReta = infiniteLineClone.getInitialPoint().getY();
        } else {
        	aReta = diffY/diffX;
        	bReta = pFinal.getY() - aReta*(pFinal.getX());
        } 
        
        System.out.println("infiniteLineClone_point1: ("+infiniteLineClone.getInitialPoint().getX()+", "+infiniteLineClone.getInitialPoint().getY()+")");
        System.out.println("infiniteLineClone_point2: ("+infiniteLineClone.getEndingPoint().getX()+", "+infiniteLineClone.getEndingPoint().getY()+")");
        System.out.println("aReta: "+aReta+" bReta: "+bReta);
        //Double newAReta = aReta*Math.cos(-fi*180/Math.PI) - bReta*Math.sin(-fi*180/Math.PI);//*180/Math.PI
        //Double newBReta = aReta*Math.sin(-fi*180/Math.PI) + bReta*Math.cos(-fi*180/Math.PI);
        //System.out.println("aReta: "+newAReta+" bReta: "+newBReta);
        //bReta = newBReta;
        //aReta = newAReta;
        
        double aEllipse = ellipseClone.getCenter().calculateDistance(ellipseClone.getHeightPoint());
        double bEllipse = ellipseClone.getCenter().calculateDistance(ellipseClone.getWidthPoint());
        
        if(aEllipse < bEllipse){
        	double temp = aEllipse;
        	aEllipse = bEllipse;
        	bEllipse = temp;
        }
        
        double x0 = ellipseClone.getCenter().getX();
        double y0 = ellipseClone.getCenter().getY();
        
        System.out.println("aEllipse: "+aEllipse+" bEllipse: "+bEllipse +" fi:"+fi);
        System.out.println("centro: ("+x0+", "+y0+")");
        System.out.println("eixo1: ("+ellipseClone.getHeightPoint().getX()+", "+ellipseClone.getHeightPoint().getY()+")");
        System.out.println("eixo2: ("+ellipseClone.getWidthPoint().getX()+", "+ellipseClone.getWidthPoint().getY()+")");
        
        //System.out.println("ending.getY(): "+ending.getY()+" starting.getY(): "+starting.getY());
        //System.out.println("ending.getX(): "+ending.getX()+" starting.getX(): "+starting.getX());
        
        if(aReta.isInfinite()){
        	Double x = infiniteLineClone.getInitialPoint().getX();
        	Double y = Math.sqrt( (1 - (x - ellipseClone.getCenter().getX())*(x - ellipseClone.getCenter().getX()) / (aEllipse*aEllipse) ) * (bEllipse*bEllipse) ) + ellipseClone.getCenter().getY();
        	//System.out.println("x: "+x+" y: "+y);
        	if(!y.isNaN()){
        		Point p = new Point(x,y);
        		intersectionPoints.add(p);
        	}
        	return intersectionPoints;
        }
        
        //ArrayList<Point> focusPoints = (ArrayList<Point>) ellipse.calculateFocusPoints();
        
        //Point Fa = focusPoints.get(0);
        //Point Fb = focusPoints.get(1);
        
        
        double A = bEllipse*bEllipse + aEllipse*aEllipse*aReta*aReta;
        double B = -2*x0*bEllipse*bEllipse + 2*aEllipse*aEllipse*aReta*bReta - 2*aReta*y0*aEllipse*aEllipse;
        double diff = bReta - bEllipse;
        if(Math.abs(diff) <= 0.01){
        	bReta = bEllipse;
        }
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
            	//rotate
            	//p.move(-lineP.getInitialPoint().getX(), -lineP.getInitialPoint().getY());
            	/*Double newpX = p.getX()*Math.cos(fi*180/Math.PI) - p.getY()*Math.sin(fi*180/Math.PI);
                Double newpY = p.getX()*Math.sin(fi*180/Math.PI) + p.getY()*Math.cos(fi*180/Math.PI);
                p.setX(newpX);
                p.setY(newpY);*/
            	//p.rotate(new Point(0, 0), fi*180/Math.PI);
            	p.rotate(ellipse.getCenter(), fi);//deve ser a origem!!!  new Point(0, 0)
            	//p.move(lineP.getInitialPoint().getX(), lineP.getInitialPoint().getY());
            	
            	//translate
            	//p.move(infiniteLine.getInitialPoint().getX(), infiniteLine.getInitialPoint().getY());
				
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
        
        double x = (-b + Math.sqrt(delta))/(2*a);
        solutions.add(x);
        System.out.println("x1 "+ x);
        
        if (delta != 0){
        	x = (-b - Math.sqrt(delta))/(2*a);
            solutions.add(x);
            System.out.println("x2 "+ x);
        }
        
        return solutions;
    }
    
    private double delta(double a, double b, double c)
    {
        return b*b - 4*a*c;
    }
    
}
