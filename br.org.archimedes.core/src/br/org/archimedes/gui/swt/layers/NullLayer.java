/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2007/04/07, 21:09:55, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.gui.swt.layers on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.gui.swt.layers;

import br.org.archimedes.model.Layer;
import br.org.archimedes.model.LineStyle;

/**
 * Belongs to package br.org.archimedes.gui.swt.
 * 
 * @author nitao
 */
public class NullLayer extends Layer {

    /**
     * Default constructor
     */
    public NullLayer () {

        super(null, "", LineStyle.CONTINUOUS, 0); //$NON-NLS-1$
    }
}
