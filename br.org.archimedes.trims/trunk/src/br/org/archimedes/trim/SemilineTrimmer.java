/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2009/01/10, 11:16:48, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.trim on the br.org.archimedes.trims project.<br>
 */
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
