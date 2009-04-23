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
import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.model.Drawing;

import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * @author Luiz Real
 */
public class LoadCommandTest extends Tester {

    Drawing drawing = new Drawing("Teste");


    private class MockedLoadCommand extends LoadCommand {

        public MockedLoadCommand () {

            super(null);
        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#createFileDialog()
         */
        @Override
        protected FileDialog createFileDialog () {
            Shell shell;
            shell = new Shell();

            return new FileDialog(shell) {
                
                /* (non-Javadoc)
                 * @see org.eclipse.swt.widgets.Dialog#checkSubclass()
                 */
                @Override
                protected void checkSubclass () {
                }

                @Override
                public String open () {

                    return "emptyDrawing.arc";
                }

            };
        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#createErrorMessageBox()
         */
        @Override
        protected MessageBox createErrorMessageBox () {

            return null;

        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#getImporterFor(java.io.File)
         */
        @Override
        protected Importer getImporterFor (File file) {

            // return super.getImporterFor(file);

            return new Importer() {

                @Override
                public Drawing importDrawing (InputStream input) throws InvalidFileFormatException,
                        IOException {

                    return drawing;
                }

            };

        }
    }


    @Test
    public void shouldCreateAFileStreamForASelectedFileAndCallImporter () throws Exception {

        LoadCommand command = new MockedLoadCommand();
        Drawing result = command.execute();
        assertEquals(drawing, result);
        assertEquals("emptyDrawing.arc", result.getFile().getName());
    }

    
    
}
