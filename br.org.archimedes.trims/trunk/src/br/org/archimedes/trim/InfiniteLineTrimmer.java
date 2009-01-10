
package br.org.archimedes.trim;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.infiniteline.InfiniteLine;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.model.ComparablePoint;
import br.org.archimedes.model.DoubleKey;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Vector;
import br.org.archimedes.rcp.extensionpoints.IntersectionManagerEPLoader;
import br.org.archimedes.semiline.Semiline;
import br.org.archimedes.trims.interfaces.Trimmer;

public class InfiniteLineTrimmer implements Trimmer {

    private IntersectionManager intersectionManager;


    public InfiniteLineTrimmer () {

        intersectionManager = new IntersectionManagerEPLoader()
                .getIntersectionManager();
    }

    public Collection<Element> trim (Element element,
            Collection<Element> references, Point click)
            throws NullArgumentException {

        if (element == null || references == null) {
            throw new NullArgumentException();
        }

        InfiniteLine xline = (InfiniteLine) element;
        Collection<Element> trimResult = new ArrayList<Element>();
        SortedSet<ComparablePoint> sortedPointSet = getSortedPointSet(xline,
                xline.getInitialPoint(), intersectionManager
                        .getIntersectionsBetween(xline, references));

        Vector direction = new Vector(xline.getInitialPoint(), xline
                .getEndingPoint());
        Vector clickVector = new Vector(xline.getInitialPoint(), click);
        double key = direction.dotProduct(clickVector);
        ComparablePoint clickPoint = null;
        try {
            clickPoint = new ComparablePoint(click, new DoubleKey(key));
        }
        catch (NullArgumentException e) {
            // Should never reach
            e.printStackTrace();
        }

        SortedSet<ComparablePoint> headSet = sortedPointSet.headSet(clickPoint);
        SortedSet<ComparablePoint> tailSet = sortedPointSet.tailSet(clickPoint);

        try {
            if (headSet.size() == 0 && tailSet.size() > 0) {
                Element trimmedLine = new Semiline(tailSet.first().getPoint(),
                        xline.getEndingPoint());
                trimmedLine.setLayer(xline.getLayer());

                trimResult.add(trimmedLine);
            }
            else if (tailSet.size() == 0 && headSet.size() > 0) {
                Element trimmedLine = new Semiline(xline.getInitialPoint(),
                        headSet.last().getPoint());
                trimmedLine.setLayer(xline.getLayer());

                trimResult.add(trimmedLine);
            }
            else if (headSet.size() > 0 && tailSet.size() > 0) {
                Element trimmedSemiLine1 = new Semiline(
                        headSet.last().getPoint(), xline.getInitialPoint());
                trimmedSemiLine1.setLayer(xline.getLayer());
                trimResult.add(trimmedSemiLine1);

                Element trimmedSemiLine2 = new Semiline(tailSet.first()
                        .getPoint(), xline.getEndingPoint());
                trimmedSemiLine2.setLayer(xline.getLayer());
                trimResult.add(trimmedSemiLine2);
            }
        }
        catch (Exception e) {
            // Should not catch any exception
            e.printStackTrace();
        }

        return trimResult;
    }

    public SortedSet<ComparablePoint> getSortedPointSet (InfiniteLine line,
            Point referencePoint, Collection<Point> intersectionPoints) {

        SortedSet<ComparablePoint> sortedPointSet = new TreeSet<ComparablePoint>();

        Point otherPoint = line.getInitialPoint();
        if (referencePoint.equals(line.getInitialPoint())) {
            otherPoint = line.getEndingPoint();
        }

        Vector direction = new Vector(referencePoint, otherPoint);
        for (Point point : intersectionPoints) {
            Vector pointVector = new Vector(referencePoint, point);
            double key = direction.dotProduct(pointVector);
            ComparablePoint element;
            try {
                element = new ComparablePoint(point, new DoubleKey(key));
                sortedPointSet.add(element);
            }
            catch (NullArgumentException e) {
                // Should never reach
                e.printStackTrace();
            }
        }

        return sortedPointSet;
    }
}
