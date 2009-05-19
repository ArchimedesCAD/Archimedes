
package br.org.archimedes.fillet;

import br.org.archimedes.interfaces.Filleter;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.List;

public class DefaultFilleter implements Filleter {

    public List<UndoableCommand> fillet (Element e1, Point click, Element e2, Point click2) {

//        IntersectionManager intersectionManager = new IntersectionManagerEPLoader()
//                .getIntersectionManager();
//        
//        Element support1 = getSupportElement(e1);
//        Element support2 = getSupportElement(e2);
//        
//        List<Point> intersections = intersectionManager.getIntersectionsBetween(support1, support2);
//        
        // TODO Auto-generated method stub
        System.out.println("Called DefaultFilleter.fillet");
        return null;
    }

//    /**
//     * @param e1
//     * @return the extension element of element
//     */
//    private Element getSupportElement (Element element) {
//        
//        if (element instanceof Line) {
//            Line line = (Line) element;
//            return new InfiniteLine(line.getInitialPoint(), line.getEndingPoint());
//        } else if( element instanceof Semiline){
//            Semiline semiline  = (Semiline) element;
//            return new InfiniteLine(semiline.getInitialPoint(), semiline.getDirectionPoint());
//        }
//        // ...
//    }

}
