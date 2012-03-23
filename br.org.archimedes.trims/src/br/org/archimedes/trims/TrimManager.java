/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Cecilia Fernandes - initial API and implementation<br>
 * Jonas K. Hirata, Hugo Corbucci, Bruno V. da Hora - later contributions<br>
 * <br>
 * This file was created on 2008/06/05, 10:11:16, by Cecilia Fernandes.<br>
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims project.<br>
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
            Collection<Point> cutPoints, Point click)
            throws NullArgumentException {

        return getTrimmerFor(element).trim(element, cutPoints, click);
    }

    private Trimmer getTrimmerFor (Element element) {

        Class<? extends Element> elementClass = element.getClass();
        Trimmer trimmer = loader.get(elementClass);
        return trimmer == null ? NULL_TRIMMER : trimmer;
    }
}
