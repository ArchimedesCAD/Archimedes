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
        
        double a = (ending.getY() - starting.getY()) / (ending.getX() - starting.getX());
        double b = ending.getY() - a*(ending.getX());
        
        ArrayList<Point> focusPoints = (ArrayList<Point>) ellipse.calculateFocusPoints();
        
        Point Fa = focusPoints.get(0);
        Point Fb = focusPoints.get(1);
        
        double A = 2.0 + 2*(a*a);
        double B = -2.0 * (Fa.getX() + Fb.getX() + Fa.getY() * a + Fb.getY() * a - 2 * a * b);
        double v = Fa.calculateDistance(Fb);
        double C = Fa.getY() * Fa.getY() - 2 * Fa.getY() * b + 2 * b * b + Fb.getY() * Fb.getY() + Fa.getX() * Fa.getX() + Fb.getX() * Fb.getX() - 2 * Fb.getY() * b - v  * v;
        
        try {
            Collection<Double> solutions = solve(A, B, C);
        }
        catch (InvalidArgumentException e) {
            // TODO Tratar excecao
            e.printStackTrace();
        }
        
        
        return intersectionPoints;
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
