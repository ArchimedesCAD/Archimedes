package br.org.archimedes.trim;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;
import br.org.archimedes.trims.interfaces.Trimmer;

public class SemilineTrimmer implements Trimmer {

    /* (non-Javadoc)
     * @see br.org.archimedes.trims.interfaces.Trimmer#trim(br.org.archimedes.model.Element, java.util.Collection, br.org.archimedes.model.Point)
     */
    public Collection<Element> trim (Element element,
            Collection<Element> references, Point click)
            throws NullArgumentException {

        // TODO Auto-generated method stub
        return null;
    }

}
