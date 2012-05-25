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

	public Collection<Point> getIntersections(Element element,
			Element otherElement) throws NullArgumentException {
		if (element == null || otherElement == null)
			throw new NullArgumentException();

		Ellipse ellipse;
		InfiniteLine infiniteLine;
		if (element.getClass() == Ellipse.class) {
			infiniteLine = (InfiniteLine) otherElement;
			ellipse = (Ellipse) element;
		} else {
			infiniteLine = (InfiniteLine) element;
			ellipse = (Ellipse) otherElement;
		}

		Collection<Point> intersectionPoints = new LinkedList<Point>();
		
		return intersectionPoints;
	}

	private Collection<Double> solve(double a, double b, double c)
			throws InvalidArgumentException {
		ArrayList<Double> solutions = new ArrayList<Double>();
		if (Math.abs(a) <= 0)
			throw new InvalidArgumentException();

		Double delta = delta(a, b, c);
		System.out.println("nosso delta: " + delta);
		if (delta.isNaN() || delta < 0)
			return solutions;

		double x = (-b + Math.sqrt(delta)) / (2 * a);
		solutions.add(x);
		System.out.println("x1 " + x);

		if (delta != 0) {
			x = (-b - Math.sqrt(delta)) / (2 * a);
			solutions.add(x);
			System.out.println("x2 " + x);
		}

		return solutions;
	}

	private double delta(double a, double b, double c) {
		return b * b - 4 * a * c;
	}

}
