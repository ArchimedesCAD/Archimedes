/*
 * Created on June 9, 2008 for br.org.archimedes
 */

package br.org.archimedes.trims;

import java.util.Collection;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author ceci
 */
public class TrimManager implements br.org.archimedes.interfaces.TrimManager {

    private final HashMap<Class<? extends Element>,Trimmer> trimmableElementsMap;

    private final static Trimmer NULL_TRIMMER = new NullTrimmer();


    /**
     * Default constructor.
     */
    public TrimManager () {

        trimmableElementsMap = new HashMap<Class<? extends Element>, Trimmer>();

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        if (registry != null) {
            fillTrimmersVersusElementsMap(registry);
        }
    }

    private void fillTrimmersVersusElementsMap (IExtensionRegistry registry) {

        IExtensionPoint extensionPoint = registry
                .getExtensionPoint("br.org.archimedes.trims.trimmer"); //$NON-NLS-1$
        if (extensionPoint != null) {
            IExtension[] extensions = extensionPoint.getExtensions();
            for (IExtension extension : extensions) {
                IConfigurationElement[] configElements = extension
                        .getConfigurationElements();
                for (IConfigurationElement elementTag : configElements) {
                    fillTrimmerMaps(elementTag);
                }
            }
        }

    }

    /**
     * Fills the trimmer map with the infos found on this element tag. If
     * there is an error, simply does nothing.
     * 
     * @param elementTag
     *            The tag that contains infos
     */
    private void fillTrimmerMaps (IConfigurationElement elementTag) {

        String elementId = elementTag.getAttribute("element"); //$NON-NLS-1$
        Class<? extends Element> element = Utils.getElementClass(elementId);
        Trimmer trimmer = null;
        if (element == null)
            return;
        try {
            trimmer = (Trimmer) elementTag.createExecutableExtension("class");
            trimmableElementsMap.put(element, trimmer);
        }
        catch (CoreException e) {
            // If this happens, ignores the element and move on.
            // Printing the stack trace might help developers to
            // create their elements correctly.
            e.printStackTrace();
        }

    }
    
	public Collection<Element> getTrimOf(Element element,
			Collection<Element> references, Point click) throws NullArgumentException {

        return getTrimmerFor(element).trim(element, references, click);
	}

	private Trimmer getTrimmerFor(Element element) {
        Class<? extends Element> elementClass = element.getClass();
        Trimmer trimmer = trimmableElementsMap.get(elementClass);
        return trimmer == null ? NULL_TRIMMER : trimmer;
	}
}
