/*
 * Created on June 9, 2008 for br.org.archimedes.trims
 */

package br.org.archimedes.trims;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;
import br.org.archimedes.trims.rcp.TrimmerEPLoader;

/**
 * Belongs to package br.org.archimedes.trims.
 * 
 * @author ceci
 */
public class TrimManager implements br.org.archimedes.interfaces.TrimManager {

    private static final Trimmer NULL_TRIMMER = new NullTrimmer();

    private TrimmerEPLoader loader;


    /**
     * Default constructor.
     */
    public TrimManager () {

        loader = new TrimmerEPLoader();
    }

    public TrimManager (TrimmerEPLoader loader) {
    	this.loader = loader;
    }
    
    public Collection<Element> getTrimOf (Element element,
            Collection<Element> references, Point click)
            throws NullArgumentException {

        return getTrimmerFor(element).trim(element, references, click);
    }

    private Trimmer getTrimmerFor (Element element) {

        Class<? extends Element> elementClass = element.getClass();
        Trimmer trimmer = loader.get(elementClass);
        return trimmer == null ? NULL_TRIMMER : trimmer;
    }
}
