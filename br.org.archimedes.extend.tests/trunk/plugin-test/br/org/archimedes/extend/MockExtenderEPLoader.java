/**
 * Copyright (c) 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Bruno V. da Hora - initial API and implementation<br>
 * <br>
 * This file was created on 2009/03/24, 16:01:03, by Bruno V. da Hora.<br>
 * It is part of package br.org.archimedes.extend on the br.org.archimedes.extend.tests project.<br>
 */

package br.org.archimedes.extend;

import br.org.archimedes.extend.interfaces.Extender;
import br.org.archimedes.extend.rcp.ExtenderEPLoader;
import br.org.archimedes.model.Element;

import java.util.Map;

public class MockExtenderEPLoader extends ExtenderEPLoader {

    private final Map<Class<? extends Element>, Extender> extenderOptions;


    public MockExtenderEPLoader (Map<Class<? extends Element>, Extender> extenderOptions) {

        this.extenderOptions = extenderOptions;
    }

    @Override
    public Extender get (Class<? extends Element> elementClass) {

        return extenderOptions.get(elementClass);
    }

}
