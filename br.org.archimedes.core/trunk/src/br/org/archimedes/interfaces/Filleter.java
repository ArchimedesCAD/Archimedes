/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Luiz Real, Bruno Klava, Ricardo Sider - initial API and implementation<br>
 * <br>
 * This file was created on 2009/05/04, 20:02:00, by Luiz Real.<br>
 * It is part of package br.org.archimedes.interfaces on the br.org.archimedes.core project.<br>
 */

package br.org.archimedes.interfaces;

import java.util.List;

import br.org.archimedes.model.Element;
import br.org.archimedes.model.Point;

/**
 * Belongs to package br.org.archimedes.interfaces.
 * 
 * @author Luiz Real, Bruno Klava, Ricardo Sider
 */
public interface Filleter {

    /**
     * Generates the list of commands necessary to perform the fillet of e1 and e2
     * 
     * @param e1
     *            First element to be filleted
     * @param e1Click
     *            The click point that selected the first element
     * @param e2
     *            Second element to be filleted
     * @param e2Click
     *            The click point that selected the second element
     * @return A list of commands to be executed in order to perform the fillet
     */
    public List<? extends UndoableCommand> fillet (Element e1, Point e1Click, Element e2,
            Point e2Click);
}
