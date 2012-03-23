/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * xp.bruno.hora - initial API and implementation<br>
 * <br>
 * This file was created on 31/03/2009, 15:14:58.<br>
 * It is part of br.org.archimedes.io.svg on the br.org.archimedes.io.svg.tests project.<br>
 */

package br.org.archimedes.io.svg;

import br.org.archimedes.Tester;
import br.org.archimedes.model.Point;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Bruno da Hora, Luiz Real
 */
public class SVGExporterHelperTest extends Tester {

    @Test
    public void verifyIfStringReturnedIsSVG () throws Exception {

        assertEquals("1,-2", SVGExporterHelper.svgFor(new Point(1.0, 2.0)));

    }

}
