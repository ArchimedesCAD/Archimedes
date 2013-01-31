package br.org.archimedes.ellipticarc;

import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.model.Point;

public class EllipticArc {

	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private Point initialPoint;
	private Point endPoint;

	public EllipticArc(Point center, Point widthPoint, Point heightPoint,
			Point initialPoint, Point endPoint) throws InvalidArgumentException {
		if(center == null || widthPoint == null || heightPoint == null ||
				initialPoint == null || endPoint == null) 
			throw new InvalidArgumentException(); 
		
		this.center = center;
		this.widthPoint = widthPoint;
		this.heightPoint = heightPoint;
		this.initialPoint = initialPoint;
		this.endPoint = endPoint;
		
		
		// TODO Auto-generated constructor stub
	}

	public boolean equals(EllipticArc ellipticArc) {

		boolean result = true;

		if (ellipticArc == null) {
			result = false;
		} else if (!this.center.equals(ellipticArc.getCenter())
				|| !this.widthPoint.equals(ellipticArc.getWidthPoint())
				|| !this.heightPoint.equals(ellipticArc.getHeightPoint())
				|| !this.endPoint.equals(ellipticArc.getEndPoint())
				|| !this.initialPoint.equals(ellipticArc.getInitialPoint())) {
			result = false;
		}
		
		return result;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public Point getWidthPoint() {
		return widthPoint;
	}

	public void setWidthPoint(Point widthPoint) {
		this.widthPoint = widthPoint;
	}

	public Point getHeightPoint() {
		return heightPoint;
	}

	public void setHeightPoint(Point heightPoint) {
		this.heightPoint = heightPoint;
	}

	public Point getInitialPoint() {
		return initialPoint;
	}

	public void setInitialPoint(Point initialPoint) {
		this.initialPoint = initialPoint;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}
}
