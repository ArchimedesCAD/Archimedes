/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/23, 10:14:27, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.parser;

import br.org.archimedes.factories.CommandFactory;

import org.junit.Test;

import static org.junit.Assert.assertNull;

public class CommandParserTest {

    /**
     * Tests: the recognition of the commands (String).
     */
    @Test
    public void test () {

        CommandParser cp = new CommandParser();
        CommandFactory com = cp.getCommand("invalid");
        assertNull(com);
    }
}
