/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Hugo Corbucci, Bruno Klava, Kenzo Yamada - later contributions<br>
 * <br>
 * This file was created on 2008/07/03, 10:03:33, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.extend;

import java.util.Collection;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.ExtendManager;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

public class NullExtendManager implements ExtendManager {

    public Element extend (Element element, Collection<Element> references, Point click)
            throws NullArgumentException {
        
        return element;
    }

    public Collection<Element> getInfiniteExtensionElements (Element element) {

        return null;
    }

}
