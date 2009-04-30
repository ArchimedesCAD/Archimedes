/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 *<br>
 * Contributors:<br>
 * Luiz Real and Wesley Seidel - initial API and implementation<br>
 * <br>
 * This file was created on 23/04/2009, 07:58:34.<br>
 * It is part of br.org.archimedes.gui.actions on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.gui.actions;

import br.org.archimedes.Tester;
import br.org.archimedes.model.Drawing;

import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Luiz Real
 */
public class SaveCommandTest extends Tester {

    Drawing drawing = new Drawing("Teste");

    boolean throwException = false;


    private class MockedSaveCommand extends SaveCommand {

        /**
         * @param shell
         * @param showDialog
         * @param drawing
         */
        public MockedSaveCommand (Shell shell, boolean showDialog, Drawing drawing) {

            super(shell, showDialog, drawing);
        }

        @Override
        protected boolean writeFile (File file) throws IOException {

            if (throwException) {
                throw new IOException();
            }
            return true;
        }

        @Override
        protected File showDialog () {

            return new File("emptyDrawing.arc");
        }

    }


    @Test
    public void doesNotFailIfNameFileExists () throws Exception {

        drawing.setFile(new File("emptyDrawing.arc"));
        MockedSaveCommand command = new MockedSaveCommand(new Shell(), false, drawing);

        boolean result = command.execute();
        assertTrue(result);
    }

    @Test
    public void doesNotFailIfFileNameDoesntExist () throws Exception {

        drawing.setFile(null);
        MockedSaveCommand command = new MockedSaveCommand(new Shell(), false, drawing);

        boolean result = command.execute();
        assertTrue(result);
    }

    @Test
    public void throwsExceptionIfFileNull() throws Exception {

        throwException = true;
        drawing.setFile(null);
        MockedSaveCommand command = new MockedSaveCommand(new Shell(), false, drawing);

        boolean result = command.execute();
        assertFalse(result);
    }

}
