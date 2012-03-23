/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */

package br.org.archimedes.trimmers;

import br.org.archimedes.Constant;
import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.trims.interfaces.Trimmer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PolylineTrimmer implements Trimmer {

    public PolylineTrimmer () {

    }
    public Collection<Element> trim (Element element, Collection<Point> cutPoints, Point click)
            throws NullArgumentException {

        if (element == null || cutPoints == null) {
            throw new NullArgumentException();
        }

        Polyline polyline = (Polyline) element;

        Collection<Element> trimResult = new ArrayList<Element>();

        if (cutPoints.isEmpty()) {
            return Collections.singleton(element);
        }

        int clickSegmentIndex = polyline.getNearestSegment(click);

        List<Line> lines = polyline.getLines();
        int negativeIntersectedIndex = 0;
        int positiveIntersectedIndex = 0;
        Point nearestNegativeIntersectionPoint = null;
        Point nearestPositiveIntersectionPoint = null;

        Line clickedSegment = lines.get(clickSegmentIndex);
        Line halfSegmentBeforeClick = null;
        try {
            halfSegmentBeforeClick = new Line(clickedSegment.getInitialPoint(), click);
        }
        catch (InvalidArgumentException e2) {
            // user clicked on a joint; no problem :)
        }

        if (halfSegmentBeforeClick != null) {
            nearestNegativeIntersectionPoint = getNearestIntersectionPoint(cutPoints,
                    halfSegmentBeforeClick, click);
            negativeIntersectedIndex = clickSegmentIndex;
        }

        if (nearestNegativeIntersectionPoint == null) {        
            if(!polyline.isClosed()){           
                for (negativeIntersectedIndex = clickSegmentIndex - 1; negativeIntersectedIndex >= 0; negativeIntersectedIndex--) {
                    Line line = lines.get(negativeIntersectedIndex);
                    
                    nearestNegativeIntersectionPoint = getNearestIntersectionPoint(cutPoints, line, line.getEndingPoint());

                    if (nearestNegativeIntersectionPoint != null) {
                        break;
                    }
                }
            }
            // se o poligono for fechado
            else {
                int numberOfLines = lines.size();
                int cont=0;
                //procura por intersecoes em sentido anti-horario, passando inclusive pela origem  e para os segmentos anteriores ate olhar todo poligono
                for (negativeIntersectedIndex = clickSegmentIndex - 1; cont < numberOfLines; negativeIntersectedIndex--, cont++) {
                    negativeIntersectedIndex = mod(negativeIntersectedIndex,numberOfLines);
                    Line line = lines.get(negativeIntersectedIndex);
                 
                    nearestNegativeIntersectionPoint = getNearestIntersectionPoint(cutPoints, line,line.getEndingPoint());
                    if (nearestNegativeIntersectionPoint != null) {
                        break;
                    }
                }                          
            }
      }
        

        Line halfSegmentAfterClick = null;
        try {
            halfSegmentAfterClick = new Line(clickedSegment.getEndingPoint(), click);
        }
        catch (InvalidArgumentException e2) {
            // user clicked on a joint; no problem :)
        }

        if (halfSegmentAfterClick != null) {
            nearestPositiveIntersectionPoint = getNearestIntersectionPoint(cutPoints,
                    halfSegmentAfterClick, click);
            positiveIntersectedIndex = clickSegmentIndex;
        }

        if (nearestPositiveIntersectionPoint == null) {
            if(!polyline.isClosed()){  
                for (positiveIntersectedIndex = clickSegmentIndex + 1; positiveIntersectedIndex < lines.size(); positiveIntersectedIndex++) {
                    Line line = lines.get(positiveIntersectedIndex);

                    nearestPositiveIntersectionPoint = getNearestIntersectionPoint(cutPoints, line,line.getInitialPoint());

                    if (nearestPositiveIntersectionPoint != null) {
                        break;
                    }
                }
            }      
            else {
                // se o poligono for fechado
                int numberOfLines = lines.size();
                int cont=0;
               //procura por intersecoes em sentido horario, passando inclusive pela origem  e continuando para os proximos segmentos ate olhar todo poligono
                for (positiveIntersectedIndex = clickSegmentIndex + 1; cont < numberOfLines; positiveIntersectedIndex++, cont++) {
                    positiveIntersectedIndex = mod(positiveIntersectedIndex,numberOfLines);
                    Line line = lines.get(positiveIntersectedIndex);
                 
                    nearestPositiveIntersectionPoint = getNearestIntersectionPoint(cutPoints, line,line.getInitialPoint());

                    if (nearestPositiveIntersectionPoint != null) {
                        break;
                    }
                }                          
            }
            
        }

        Collection<Polyline> polyLines = new ArrayList<Polyline>();
        List<Point> polyPoints = polyline.getPoints();
        
        //constroi o poligono a ser devolvido, com o trim passando pelos segmentos que fecham o poligono
        if(negativeIntersectedIndex > clickSegmentIndex) {
            List<Point> points = new ArrayList<Point>();          
            points.add(nearestPositiveIntersectionPoint);
            points.addAll(polyPoints.subList(positiveIntersectedIndex + 1, negativeIntersectedIndex + 1));
            points.add(nearestNegativeIntersectionPoint);
            trimResult.clear();
            try {
                trimResult.add(new Polyline(points));
            }
            catch (InvalidArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
         }
        //constroi o poligono a ser devolvido, com o trim passando pelos segmentos que fecham o poligono
        else if (positiveIntersectedIndex < clickSegmentIndex) {
            List<Point> points = new ArrayList<Point>(); 
            
            points.add(nearestPositiveIntersectionPoint);
            points.addAll(polyPoints.subList(positiveIntersectedIndex + 1, negativeIntersectedIndex + 1));
            points.add(nearestNegativeIntersectionPoint);
            trimResult.clear();
            try {
                trimResult.add(new Polyline(points));
            }
            catch (InvalidArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
        if (nearestNegativeIntersectionPoint != null) {
            List<Point> polylinePoints1 = new ArrayList<Point>();          
            polylinePoints1.addAll(polyPoints.subList(0, negativeIntersectedIndex + 1));
            polylinePoints1.add(nearestNegativeIntersectionPoint);
            try {
                polyLines.add(new Polyline(polylinePoints1));
            }
            catch (InvalidArgumentException e1) {
                // should not happen
                e1.printStackTrace();
            }
        }

        if (nearestPositiveIntersectionPoint != null) {
            List<Point> polylinePoints2 = new ArrayList<Point>();
            polylinePoints2.add(nearestPositiveIntersectionPoint);
            polylinePoints2.addAll(polyPoints.subList(positiveIntersectedIndex + 1, polyPoints.size()));
            try {
                polyLines.add(new Polyline(polylinePoints2));
            }
            catch (InvalidArgumentException e1) {
                // should not happen
                e1.printStackTrace();
            }
        }

        for (Polyline polyLine : polyLines) {
            boolean clicked = false;
            try {
                clicked = polyLine.contains(click);
            }
            catch (NullArgumentException e) {
                // Should not happen
                e.printStackTrace();
            }
            if ( !clicked) {
                polyLine.setLayer(polyline.getLayer());
                trimResult.add(polyLine);
            }
        }

        if (trimResult.size() == 2) {
            
            Iterator<Element> iterator = trimResult.iterator();
            Polyline poly1 = (Polyline) iterator.next();
            Polyline poly2 = (Polyline) iterator.next();
            List<Point> poly1Points = poly1.getPoints();
            List<Point> poly2Points = poly2.getPoints();
            Point firstPoly2 = poly2Points.get(0);
            Point lastPoly1 = poly1Points.get(poly1Points.size() - 1);
            if (lastPoly1.equals(firstPoly2)) {
                trimResult.clear();
                List<Point> points = poly1Points;
                points.remove(points.size() - 1);
                Point last = points.get(points.size() - 1);
                try {
                    double determinant = Geometrics.calculateDeterminant(last, firstPoly2,
                            poly2Points.get(1));
                    if (Math.abs(determinant) < Constant.EPSILON) {
                        poly2Points.remove(0);
                    }
                    points.addAll(poly2Points);
                    trimResult.add(new Polyline(points));
                }
                catch (NullArgumentException e) {
                    // Should not happen
                    e.printStackTrace();
                }
                catch (InvalidArgumentException e) {
                    // Ignores it
                }
            }
        }
        }
        return trimResult;
    }

    /**
     * Get the nearest intersection point from a given reference that is inside line.
     * 
     * @param cutPoints
     *            Collection of all intersection points of polyline.
     * @param line
     *            A segment of polyline.
     * @param reference
     *            The point that will serve as reference for "close".
     * @return The point in cutPoints that is closest to reference and is inside line.
     * @throws NullArgumentException
     *             if cutPoints contains a null reference.
     * @author keizo
     * @author lreal
     */
    private Point getNearestIntersectionPoint (Collection<Point> cutPoints, Line line,
            Point reference) throws NullArgumentException {

        Point nearestIntersectionPoint = null;
        double distanceToNearestIntersectionPoint = 0.0;

        for (Point point : cutPoints) {
            if (line.contains(point)) {
                double distance = point.calculateDistance(reference);

                if (nearestIntersectionPoint == null
                        || distance < distanceToNearestIntersectionPoint) {
                    nearestIntersectionPoint = point;
                    distanceToNearestIntersectionPoint = distance;
                }
            }
        }

        return nearestIntersectionPoint;
    }
    
    private int mod(int x, int y)
    {
        int result = x % y;
        if (result < 0)
        {
            result += y;
        }
        return result;
    }

}
