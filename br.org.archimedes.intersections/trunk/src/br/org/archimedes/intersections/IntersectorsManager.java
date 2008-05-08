/*
 * Created on May 5, 2008 for br.org.archimedes.intersections
 */

package br.org.archimedes.intersections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.IntersectionManager;
import br.org.archimedes.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.PairOfElementClasses;
import br.org.archimedes.model.Point;
import br.org.archimedes.model.Rectangle;

/**
 * Belongs to package br.org.archimedes.intersections.
 * 
 * @author night
 */
public class IntersectorsManager implements IntersectionManager {

    private final Map<PairOfElementClasses, Intersector> elementsToIntersectorMap;

    private final static Intersector NULL_INTERSECTOR = new NullIntersector();


    /**
     * Default constructor.
     */
    public IntersectorsManager () {

        elementsToIntersectorMap = new HashMap<PairOfElementClasses, Intersector>();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        if (registry != null) {
            fillIntersectorVersusElementsMaps(registry);
        }
    }

    private void fillIntersectorVersusElementsMaps (IExtensionRegistry registry) {

        IExtensionPoint extensionPoint = registry
                .getExtensionPoint("br.org.archimedes.intersections.elementsIntersector"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {
                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement elementTag : configElements) {
                    fillIntersectorMaps(elementTag);
                }
            }
        }

    }

    /**
     * Fills the intersector maps with the infos found on this element tag. If
     * there is an error, just does nothing.
     * 
     * @param elementTag
     *            The tag that contains infos
     */
    private void fillIntersectorMaps (IConfigurationElement elementTag) {

        String elementId = elementTag.getAttribute("element"); //$NON-NLS-1$
        String otherElementId = elementTag.getAttribute("otherElement"); //$NON-NLS-1$
        Class<? extends Element> element = Utils.getElementClass(elementId);
        Class<? extends Element> otherElement = Utils
                .getElementClass(otherElementId);
        Intersector inter = null;
        if (element == null || otherElement == null)
            return;
        try {
            inter = (Intersector) elementTag.createExecutableExtension("class");
            elementsToIntersectorMap.put(new PairOfElementClasses(element,
                    otherElement), inter);
        }
        catch (CoreException e) {
            // If this happens, ignores the element and move on.
            // Printing the stack trace might help developers to
            // create their elements correctly.
            e.printStackTrace();
        }

    }

    public Intersector getIntersectorFor (Element element, Element otherElement) {

        Class<? extends Element> e1Class = element.getClass();
        Class<? extends Element> e2Class = otherElement.getClass();

        PairOfElementClasses pair = new PairOfElementClasses(e1Class, e2Class);
        Intersector intersector = elementsToIntersectorMap.get(pair);
        return intersector == null ? NULL_INTERSECTOR : intersector;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#intersects(br.org.archimedes.model.Rectangle,
     *      br.org.archimedes.model.Element)
     */
    public boolean intersects (Rectangle rect, Element element) {

        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.interfaces.IntersectionManager#getIntersectionsBetween(br.org.archimedes.model.Element,
     *      br.org.archimedes.model.Element)
     */
    public Collection<Point> getIntersectionsBetween (Element element,
            Element otherElement) throws NullArgumentException {

        return getIntersectorFor(element, otherElement).getIntersections(
                element, otherElement);
    }
}
