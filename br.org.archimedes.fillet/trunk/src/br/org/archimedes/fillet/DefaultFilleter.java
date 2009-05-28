
package br.org.archimedes.fillet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Geometrics;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;

public class DefaultFilleter implements Filleter {

    public List<UndoableCommand> fillet (Element e1, Point click1, Element e2, Point click2) {

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

            for (Point intersection : closestIntersections) {
                // try to select extremes that can be moved to this intersection, maintaining the
                // clicks!
            }
        }

        catch (NullArgumentException e) {
            // will not reach here
        }

        System.out.println("Called DefaultFilleter.fillet");
        return null;
    }

}
