
package br.org.archimedes.fillet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.org.archimedes.Geometrics;
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
import br.org.archimedes.move.MoveCommand;
import br.org.archimedes.polyline.Polyline;
import br.org.archimedes.rcp.extensionpoints.ExtendManagerEPLoader;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

public class DefaultFilleter implements Filleter {

    private IntersectionManager intersectionManager;

    private ExtendManager extendManager;


    public DefaultFilleter () {

        intersectionManager = new IntersectionManagerEPLoader().getIntersectionManager();
        extendManager = new ExtendManagerEPLoader().getExtendManager();
    }

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

        Collection<Element> extensions1 = extendManager.getInfiniteExtensionElements(e1);
        Collection<Element> extensions2 = extendManager.getInfiniteExtensionElements(e2);

        Collection<Point> intersections = new ArrayList<Point>();
        List<UndoableCommand> fillet = new ArrayList<UndoableCommand>();

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
            for (Point intersection : closestIntersections) {
                if (possibleMoves1.containsKey(intersection)
                        && possibleMoves2.containsKey(intersection)) {

                    removeFarthestIntersections(intersection, possibleMoves1);
                    removeFarthestIntersections(intersection, possibleMoves2);

                    for (Point point1 : possibleMoves1.get(intersection)) {
                        for (Point point2 : possibleMoves2.get(intersection)) {
                            double dist = Geometrics.calculateDistance(point1, intersection)
                                    + Geometrics.calculateDistance(point2, intersection);
                            if (dist == minDist) {
                                minimumMoves.add(createPossibleMoves(intersection, point1, point2));
                            }
                            else if (dist < minDist) {
                                minDist = dist;
                                minimumMoves.clear();
                                minimumMoves.add(createPossibleMoves(intersection, point1, point2));
                            }
                        }
                    }

                }
            }

            possibleMoves1.clear();
            possibleMoves2.clear();

            for (ArrayList<Point> x : minimumMoves) {
                addMapping(possibleMoves1, x.get(0), x.get(1));
                addMapping(possibleMoves2, x.get(0), x.get(2));
            }

            if (possibleMoves1.size() > 1 || possibleMoves2.size() > 1)
                return fillet; // more than one possible solution

            else {

                untie(possibleMoves1, click1, e1);
                untie(possibleMoves2, click2, e2);
            }

            fillet.add(generateCommand(possibleMoves1, e1));
            fillet.add(generateCommand(possibleMoves2, e2));
        }

        catch (NullArgumentException e) {
            // will not reach here
        }

        return fillet;

    }

    private UndoableCommand generateCommand (HashMap<Point, ArrayList<Point>> possibleMoves,
            Element element) {

        Point intersection = null;
        Point extreme = null;

        for (Point point : possibleMoves.keySet()) {
            intersection = point;
            extreme = possibleMoves.get(point).get(0);
        }
        try {
            HashMap<Element, Collection<Point>> pointsToMove = new HashMap<Element, Collection<Point>>();
            pointsToMove.put(element, Collections.singleton(extreme));
            return new MoveCommand(pointsToMove, new Vector(extreme, intersection));
        }

        catch (NullArgumentException e) {
            // won't happen
        }
        return null;
    }

    /**
     * Removes the extreme farther away from the intersection. If tie removes the initial point from
     * possibleMoves
     * 
     * @param possibleMoves
     * @param click
     * @param element
     */
    private void untie (HashMap<Point, ArrayList<Point>> possibleMoves, Point click, Element element) {

        for (Point point : possibleMoves.keySet()) {
            if (possibleMoves.get(point).size() > 1) {
                try {
                    double dist0 = Geometrics.calculateDistance(click, possibleMoves.get(point)
                            .get(0));
                    double dist1 = Geometrics.calculateDistance(click, possibleMoves.get(point)
                            .get(1));

                    if (dist0 > dist1) {
                        possibleMoves.get(point).remove(0);
                    }

                    else if (dist0 < dist1) {
                        possibleMoves.get(point).remove(1);
                    }

                    else {
                        if (possibleMoves.get(point).get(0).equals(
                                element.getExtremePoints().get(0)))
                            possibleMoves.get(point).remove(0);
                        else
                            possibleMoves.get(point).remove(1);
                    }

                }
                catch (NullArgumentException e) {
                    // won't happen
                }
            }
        }
    }

    private void addMapping (HashMap<Point, ArrayList<Point>> possibleMoves, Point intersection,
            Point extreme) {

        if (possibleMoves.containsKey(intersection)) {
            if ( !possibleMoves.get(intersection).contains(extreme))
                possibleMoves.get(intersection).add(extreme);
        }
        else {
            ArrayList<Point> aux = new ArrayList<Point>();
            aux.add(extreme);
            possibleMoves.put(intersection, aux);
        }
    }

    private ArrayList<Point> createPossibleMoves (Point intersection, Point point1, Point point2) {

        ArrayList<Point> chosen = new ArrayList<Point>(3);
        chosen.add(intersection);
        chosen.add(point1);
        chosen.add(point2);
        return chosen;
    }

    private void removeFarthestIntersections (Point intersection,
            HashMap<Point, ArrayList<Point>> possibleMoves) {

        ArrayList<Point> extremes = possibleMoves.get(intersection);
        if (extremes.size() > 1) {
            try {
                if (Geometrics.calculateDistance(extremes.get(0), intersection) < Geometrics
                        .calculateDistance(extremes.get(1), intersection))
                    extremes.remove(1);
                else if (Geometrics.calculateDistance(extremes.get(0), intersection) > Geometrics
                        .calculateDistance(extremes.get(1), intersection))
                    extremes.remove(0);
            }
            catch (NullArgumentException e) {
                // wont ever happen
            }
        }
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
        else {
            for (Point extreme : element.getExtremePoints()) {
                for (Point intersection : closestIntersections) {
                    // TODO verify if the click coordinates are on one of the extreme points
                    // if ( !isInsideLine(extreme, intersection, click)) {
                    // possibleMoves.get(intersection).add(extreme);
                    // }
                    if (moveContainsClick(element, extreme, intersection, click)) {
                        possibleMoves.get(intersection).add(extreme);
                    }
                }
            }
        }

        return possibleMoves;

    }

    private boolean moveContainsClick (Element element, Point extreme, Point intersection,
            Point click) {

        if (intersection.equals(extreme)) {
            return true;
        }

        try {
            Vector vector = new Vector(extreme, intersection);
            element.move(Collections.singleton(extreme), vector);
            boolean contains = element.contains(click);
            element.move(Collections.singleton(extreme), vector.multiply( -1));
            return contains;
        }
        catch (NullArgumentException e) {
            // wont reach here
        }

        return false;
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
