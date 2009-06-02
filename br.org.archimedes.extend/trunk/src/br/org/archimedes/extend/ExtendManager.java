/**
 * Copyright (c) 2008, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz C. Real - initial API and implementation<br>
 * Bruno V. da Hora - later contributions<br>
 * <br>
 * This file was created on 2008/07/03, 10:30:03, by Luiz C. Real.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend project.<br>
 */

package br.org.archimedes.extend;

import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.extend.rcp.ExtenderEPLoader;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

import java.util.Collection;

public class ExtendManager implements br.org.archimedes.interfaces.ExtendManager {

    private static final Extender NULL_EXTENDER = new NullExtender();

    private ExtenderEPLoader loader;


    public ExtendManager () {

        loader = new ExtenderEPLoader();
    }

    public ExtendManager (ExtenderEPLoader loader) {

        this.loader = loader;
    }

    public Element extend (Element element, Collection<Element> references, Point click)
            throws NullArgumentException {

        return getExtenderFor(element).extend(element, references, click);
    }

    private Extender getExtenderFor (Element element) {

        Class<? extends Element> elementClass = element.getClass();
        Extender extender = loader.get(elementClass);
        return extender == null ? NULL_EXTENDER : extender;
    }

    public Collection<Element> getInfiniteExtensionElements (Element element) {

        return getExtenderFor(element).getInfiniteExtensionElements(element);
    }

}
