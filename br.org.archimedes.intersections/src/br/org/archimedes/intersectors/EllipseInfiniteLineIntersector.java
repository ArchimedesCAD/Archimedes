package br.org.archimedes.intersectors;

import java.util.ArrayList;
import java.util.Collection;

import br.org.archimedes.Constant;
import br.org.archimedes.ellipse.Ellipse;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;

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

		// Ellipse description: (x - x0)²/a² + (y - y0)²/b² = 1
		// Line: p + v * d, d > 0
		// A point L is in the Line-Ellipse intersection iff it satisfies both equations
		
		Collection<Point> intersections = new ArrayList<Point>();
		double theta = ellipse.getFi();

		Point lineP = infiniteLine.getInitialPoint();
		Vector lineD = new Vector(lineP, infiniteLine.getEndingPoint())
				.normalized();

		double ellipseA = ellipse.getSemiMajorAxis().getNorm();
		double ellipseB = ellipse.getSemiMinorAxis().getNorm();
		Point ellipseCenter = ellipse.getCenter();

		double ellipseAsqr = ellipseA * ellipseA;
		double ellipseBsqr = ellipseB * ellipseB;

		double Ox = lineP.getX() - ellipseCenter.getX();
		double Oy = lineP.getY() - ellipseCenter.getY();

		double sinTheta = Math.sin(theta);
		double cosTheta = Math.cos(theta);

		double L1 = ellipseBsqr * cosTheta * cosTheta + ellipseAsqr * sinTheta
				* sinTheta; // L1 = b² (se Fi = 0)
		double L2 = ellipseBsqr * sinTheta * sinTheta + ellipseAsqr * cosTheta
				* cosTheta; // L2 = a² (se Fi = 0)

		double Dx = lineD.getX();
		double Dy = lineD.getY();

		double solutionA = (Dx * Dx * L1)
				+ (Dy * Dy * L2)
				+ (2 * Dx * Dy * (ellipseAsqr - ellipseBsqr) * sinTheta * cosTheta);
		double solutionB = (2 * Ox * Dx * L1)
				+ (2 * Oy * Dy * L2)
				+ (2 * (Ox * Dy + Oy * Dx) * (ellipseAsqr - ellipseBsqr)
						* sinTheta * cosTheta);
		double solutionC = (Ox * Ox * L1)
				+ (Oy * Oy * L2)
				+ (2 * Ox * Oy * (ellipseAsqr - ellipseBsqr) * sinTheta * cosTheta)
				- ellipseAsqr * ellipseBsqr;

		try {
			ArrayList<Double> solutions = (ArrayList<Double>) solve(solutionA,
					solutionB, solutionC);
			for (Double sol : solutions) {
				Point p = lineP.addVector(lineD.multiply(sol));
				// if(line.contains(p))
				intersections.add(p);
			}

		} catch (InvalidArgumentException e) {
			e.printStackTrace();
		}
		return intersections;
	}

	private Collection<Double> solve(double a, double b, double c)
			throws InvalidArgumentException {

		if (Math.abs(a) < Constant.EPSILON)
			throw new InvalidArgumentException();
		
		ArrayList<Double> solutions = new ArrayList<Double>();
		
		double delta = delta(a, b, c);
		if (delta < -Constant.EPSILON) {
			// delta < 0
		} else if (delta < Constant.EPSILON) {
			// delta == 0
			solutions.add(-b / (2.0 * a));
		} else {
			// delta > 0
			solutions.add((-b + Math.sqrt(delta)) / (2.0 * a));
			solutions.add((-b - Math.sqrt(delta)) / (2.0 * a));
		}
		return solutions;
	}

	private double delta(double a, double b, double c) {
		return b * b - 4 * a * c;
	}

}
