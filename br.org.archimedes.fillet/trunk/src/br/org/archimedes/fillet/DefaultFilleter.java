
package br.org.archimedes.fillet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.arc.Arc;
import br.org.archimedes.circle.Circle;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

public class DefaultFilleter implements Filleter {

    public List<UndoableCommand> fillet (Element e1, Point click1, Element e2, Point click2) {

        if (isOpen(e1) && isOpen(e2)) {
            return filletOpenElements(e1, click1, e2, click2);
        }
        else if (isOpen(e1)) {
            return filletClosedAndOpenElements(e2, click2, e1, click1);
        }
        else if (isOpen(e2)) {
            return filletClosedAndOpenElements(e1, click1, e2, click2);
        }
        else {
            return filletClosedElements(e1, click1, e2, click2);
        }
    }

    private boolean isOpen (Element element) {

        if (element instanceof Circle) {
            return false;
        }
        else if (element instanceof Polyline) {
            Polyline polyline = (Polyline) element;
            return !polyline.isClosed();
        }

        return true;
    }

    private List<UndoableCommand> filletClosedElements (Element e1, Point click1, Element e2,
            Point click2) {

        // TODO implement it
        return null;
    }

    private List<UndoableCommand> filletClosedAndOpenElements (Element e1, Point click1,
            Element e2, Point click2) {

        // TODO implement it
        return null;
    }

    private List<UndoableCommand> filletOpenElements (Element e1, Point click1, Element e2,
            Point click2) {

        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();

        ExtendManager extendManager = new br.org.archimedes.extend.ExtendManager();

        Collection<Element> extensions1 = extendManager.getInfiniteExtensionElements(e1);
        Collection<Element> extensions2 = extendManager.getInfiniteExtensionElements(e2);

        Collection<Point> intersections = new ArrayList<Point>();

        try {

            for (Element ext1 : extensions1) {
                intersections
                        .addAll(intersectionManager.getIntersectionsBetween(ext1, extensions2));
            }

            Collection<Point> closestIntersections = new ArrayList<Point>();
            double minDist = Double.MAX_VALUE;
            for (Point intersection : intersections) {
                double dist = Geometrics.calculateDistance(intersection, click1)
                        + Geometrics.calculateDistance(intersection, click2);
                if (dist < minDist) {
                    closestIntersections.clear();
                    minDist = dist;
                    closestIntersections.add(intersection);
                }
                else if (dist == minDist) {
                    closestIntersections.add(intersection);
                }
            }

            HashMap<Point, ArrayList<Point>> possibleMoves1 = getPossibleMoves(e1, click1,
                    closestIntersections);
            HashMap<Point, ArrayList<Point>> possibleMoves2 = getPossibleMoves(e2, click2,
                    closestIntersections);

            possibleMoves1 = cleanMap(possibleMoves1);
            possibleMoves2 = cleanMap(possibleMoves2);

            for (Point intersection : closestIntersections) {
                boolean possibleMoves1Contains = possibleMoves1.containsKey(intersection);
                boolean possibleMoves2Contains = possibleMoves2.containsKey(intersection);
                if (possibleMoves1Contains != possibleMoves2Contains) {
                    if (possibleMoves1Contains) {
                        possibleMoves1.remove(intersection);
                    }
                    else {
                        possibleMoves2.remove(intersection);
                    }
                }
            }

            Collection<ArrayList<Point>> minimumMoves = new ArrayList<ArrayList<Point>>();
            minDist = Double.MAX_VALUE;
            // TODO ***
            // for (Point intersection : intersections) {
            // double dist = Geometrics.calculateDistance(intersection, click1)
            // + Geometrics.calculateDistance(intersection, click2);
            // if (dist < minDist) {
            // closestIntersections.clear();
            // minDist = dist;
            // closestIntersections.add(intersection);
            // }
            // else if (dist == minDist) {
            // closestIntersections.add(intersection);
            // }
            // }

        }

        catch (NullArgumentException e) {
            // will not reach here
        }

        // TODO return commands
        return null;

    }

    private HashMap<Point, ArrayList<Point>> cleanMap (
            HashMap<Point, ArrayList<Point>> possibleMoves) {

        for (Point key : possibleMoves.keySet()) {
            if (possibleMoves.get(key).isEmpty()) {
                possibleMoves.remove(key);
            }
        }

        return possibleMoves;

    }

    private HashMap<Point, ArrayList<Point>> getPossibleMoves (Element element, Point click,
            Collection<Point> closestIntersections) throws NullArgumentException {

        HashMap<Point, ArrayList<Point>> possibleMoves = new HashMap<Point, ArrayList<Point>>();

        for (Point intersection : closestIntersections) {
            possibleMoves.put(intersection, new ArrayList<Point>());
        }

        if (element instanceof Polyline) {
            Polyline polyline = (Polyline) element;
            for (Point intersection : closestIntersections) {

                for (Line line : polyline.getLines()) {
                    if (line.contains(click) && line.contains(intersection)) {
                        if (isInsideLine(line.getInitialPoint(), intersection, click)) {
                            Point oppositeExtreme = polyline.getExtremePoints().get(1);
                            possibleMoves.get(intersection).add(oppositeExtreme);
                            break;
                        }
                        else {
                            Point extreme = polyline.getExtremePoints().get(0);
                            possibleMoves.get(intersection).add(extreme);
                            break;
                        }
                    }
                    else if (line.contains(click)) {
                        Point oppositeExtreme = polyline.getExtremePoints().get(1);
                        possibleMoves.get(intersection).add(oppositeExtreme);
                        break;
                    }
                    else if (line.contains(intersection)) {
                        Point extreme = polyline.getExtremePoints().get(0);
                        possibleMoves.get(intersection).add(extreme);
                        break;
                    }
                }
            }
        }
        else if (element instanceof Arc) {
            Arc arc = (Arc) element;
            for (Point intersection : closestIntersections) {
                if (arc.contains(intersection)) {
                    for (Point extreme : arc.getExtremePoints()) {
                        possibleMoves.get(intersection).add(extreme);
                    }
                }
                else {
                    Collection<Point> initial = new ArrayList<Point>();
                    initial.add(arc.getInitialPoint());
                    arc.move(initial, new Vector(arc.getInitialPoint(), intersection));
                    if (arc.contains(click)) {
                        possibleMoves.get(intersection).add(arc.getInitialPoint());
                    }
                    else {
                        possibleMoves.get(intersection).add(arc.getEndingPoint());
                    }
                }
            }
        }
        else {
            for (Point extreme : element.getExtremePoints()) {
                for (Point intersection : closestIntersections) {

                    if ( !isInsideLine(extreme, intersection, click)) {
                        possibleMoves.get(intersection).add(extreme);
                    }
                }
            }
        }

        return possibleMoves;

    }

    private boolean isInsideLine (Point p1, Point p2, Point click) {

        try {
            Line line = new Line(p1, p2);
            return line.contains(click);
        }
        catch (NullArgumentException e) {
            // Will not reach here
        }
        catch (InvalidArgumentException e) {
            // Will not reach here
        }

        return false;

    }

}
