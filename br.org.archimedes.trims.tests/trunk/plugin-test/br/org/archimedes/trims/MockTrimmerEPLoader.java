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
 * It is part of package br.org.archimedes.trims on the br.org.archimedes.trims.tests project.<br>
 */

package br.org.archimedes.trims;

import br.org.archimedes.model.Element;
import br.org.archimedes.trims.interfaces.Trimmer;
import br.org.archimedes.trims.rcp.TrimmerEPLoader;

import java.util.Map;

public class MockTrimmerEPLoader extends TrimmerEPLoader {

    private final Map<Class<? extends Element>, Trimmer> trimmerOptions;


    public MockTrimmerEPLoader (Map<Class<? extends Element>, Trimmer> trimmerOptions) {

        this.trimmerOptions = trimmerOptions;
    }

    @Override
    public Trimmer get (Class<? extends Element> elementClass) {

        return trimmerOptions.get(elementClass);
    }

}
