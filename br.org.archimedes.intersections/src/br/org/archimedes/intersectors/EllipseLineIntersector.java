package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

public class EllipseLineIntersector implements Intersector {

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		
		if (element == null || otherElement == null)
			throw new NullArgumentException();
		
		Collection<Point> intersections = new ArrayList<Point>();

		Line line;
		Ellipse ellipse;

		if (element.getClass() == Line.class) {
			line = (Line) element;
			ellipse = (Ellipse) otherElement;
		} else {
			line = (Line) otherElement;
			ellipse = (Ellipse) element;
		}
				
		// Ellipse description: (x - x0)²/a² + (y - y0)²/b² = 1
		// Line: p + v * d, d > 0
		// A point L is in the Line-Ellipse intersection iff it satisfies both equations
		
		double fi = ellipse.getFi();
		
		Point lineP = line.getInitialPoint().clone();
		lineP.rotate(new Point(0, 0), fi);
		
		Point endP = line.getEndingPoint().clone();
		endP.rotate(new Point(0, 0), fi);
		
		Vector lineV = new Vector(lineP, endP).normalized();
		
		double ellipseA = ellipse.getSemiMajorAxis().getNorm();
		double ellipseB = ellipse.getSemiMinorAxis().getNorm();
		Point ellipseCenter = ellipse.getCenter();
		
		double Xr = lineV.getX(), Yr = lineV.getY();
		double Xdiff = lineP.getX() - ellipseCenter.getX(), Ydiff = lineP.getY() - ellipseCenter.getY();
		
		double solutionA = ellipseB*ellipseB * Xr*Xr + ellipseA*ellipseA * Yr*Yr;
		double solutionB = 2*(ellipseB*ellipseB * Xr * Xdiff + ellipseA*ellipseA * Yr * Ydiff);
		double solutionC = ellipseB*ellipseB * Xdiff*Xdiff + ellipseA*ellipseA * Ydiff*Ydiff - ellipseA*ellipseA * ellipseB*ellipseB;

		try {
			ArrayList<Double> solutions = (ArrayList<Double>) solve(solutionA, solutionB, solutionC);
			for(Double sol : solutions) {
				Point p = lineP.addVector(lineV.multiply(sol));
				if(line.contains(p))
					intersections.add(p);
			}
			
		} catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
		return intersections;
	}
	
    private Collection<Double> solve(double a, double b, double c) throws InvalidArgumentException
    {
        if (Math.abs(a) < Constant.EPSILON)
            throw new InvalidArgumentException();
        double delta = delta(a, b, c);
        ArrayList<Double> solutions = new ArrayList<Double>();
        if (delta < -Constant.EPSILON)
            return solutions;
        solutions.add((-b + Math.sqrt(delta))/2*a);
        if (delta > Constant.EPSILON)
            solutions.add((-b - Math.sqrt(delta))/2*a);
        return solutions;
    }
    
    private double delta(double a, double b, double c)
    {
        return b*b - 4*a*c;
    }

}
