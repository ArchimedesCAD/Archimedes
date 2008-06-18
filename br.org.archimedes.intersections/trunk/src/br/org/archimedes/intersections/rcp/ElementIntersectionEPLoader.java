/*
 * Created on Jun 18, 2008 for br.org.archimedes.io.xml
 */

package br.org.archimedes.intersections.rcp;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

import br.org.archimedes.intersections.NullIntersector;
import br.org.archimedes.intersections.interfaces.Intersector;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.PairOfElementClasses;
import br.org.archimedes.rcp.ExtensionLoader;
import br.org.archimedes.rcp.ExtensionTagHandler;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;

/**
 * Belongs to package br.org.archimedes.io.xml.extension.
 * 
 * @author night
 */
public class ElementIntersectionEPLoader implements ExtensionTagHandler {

    private static final String CLASS_ATTRIBUTE_NAME = "class";

    private static final String SECOND_ELEMENT_ATTRIBUTE_NAME = "otherElement";

    private static final String FIRST_ELEMENT_ATTRIBUTE_NAME = "element";

    private final static String ELEMENT_INTERSECTOR_EP_ID = "br.org.archimedes.intersections.elementsIntersector";

    private final static Intersector NULL_INTERSECTOR = new NullIntersector();

    private ElementEPLoader elementLoader;

    private final Map<PairOfElementClasses, Intersector> elementsToIntersectorMap = new HashMap<PairOfElementClasses, Intersector>();;


    /**
     * Default constructor.
     */
    public ElementIntersectionEPLoader () {

        elementLoader = new ElementEPLoader();
        if(elementsToIntersectorMap.isEmpty()) {
            ExtensionLoader loader = new ExtensionLoader(ELEMENT_INTERSECTOR_EP_ID);
            loader.loadExtension(this);
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
     * @see br.org.archimedes.rcp.ExtensionTagHandler#handleTag(org.eclipse.core.runtime.IConfigurationElement)
     */
    public void handleTag (IConfigurationElement elementTag)
            throws CoreException {

        String elementId = elementTag
                .getAttribute(FIRST_ELEMENT_ATTRIBUTE_NAME);
        String otherElementId = elementTag
                .getAttribute(SECOND_ELEMENT_ATTRIBUTE_NAME);
        Class<? extends Element> element = elementLoader
                .getElementClass(elementId);
        Class<? extends Element> otherElement = elementLoader
                .getElementClass(otherElementId);

        if (element != null && otherElement != null) {
            Intersector inter = null;
            inter = (Intersector) elementTag
                    .createExecutableExtension(CLASS_ATTRIBUTE_NAME);
            elementsToIntersectorMap.put(new PairOfElementClasses(element,
                    otherElement), inter);
        }
    }
}
