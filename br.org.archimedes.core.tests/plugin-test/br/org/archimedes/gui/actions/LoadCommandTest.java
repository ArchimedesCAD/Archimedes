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

import br.org.archimedes.TestActivator;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidFileFormatException;
import br.org.archimedes.interfaces.Importer;
import br.org.archimedes.model.Drawing;
import br.org.archimedes.rcp.extensionpoints.NativeFormatEPLoader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.PlatformUI;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Luiz Real
 */
public class LoadCommandTest extends Tester {

    Drawing drawing = new Drawing("Teste");


    private class MockedLoadCommand extends LoadCommand {

        int testCount = 0;

        boolean throwException = false;

        boolean showedMessage = false;


        public MockedLoadCommand () {

            super(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#createFileDialog()
         */
        @Override
        public FileDialog createFileDialog () {

            return new FileDialog(parent) {

                /*
                 * (non-Javadoc) Overriden to allow us to mock this class
                 * @see org.eclipse.swt.widgets.Dialog#checkSubclass()
                 */
                @Override
                protected void checkSubclass () {

                }

                @Override
                public String open () {

                    File file;
                    try {
                        file = TestActivator.resolveFile("emptyDrawing.arc", TestActivator
                                .getDefault().getBundle());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        throw new AssertionError("The emptyDrawing.arc file couldn't be loaded");
                    }
                    return file.getAbsolutePath();
                }

            };
        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#getImporterFor(java.io.File)
         */
        @Override
        protected Importer getImporterFor (File file) {

            return new Importer() {

                public Drawing importDrawing (InputStream input) throws InvalidFileFormatException,
                        IOException {

                    if (throwException) {
                        throwException = false;
                        throw new InvalidFileFormatException();
                    }
                    if (0 == testCount++)
                        return null;
                    return drawing;
                }

            };

        }

        /*
         * (non-Javadoc)
         * @see br.org.archimedes.gui.actions.LoadCommand#createErrorMessageBox()
         */
        @Override
        protected MessageBox createErrorMessageBox () {

            return new MessageBox(parent) {

                /*
                 * (non-Javadoc) Overriden to allow us to mock this class
                 * @see org.eclipse.swt.widgets.Dialog#checkSubclass()
                 */
                @Override
                protected void checkSubclass () {

                }

                /*
                 * (non-Javadoc)
                 * @see org.eclipse.swt.widgets.MessageBox#open()
                 */
                @Override
                public int open () {

                    showedMessage = true;
                    return SWT.OK;
                }
            };
        }
    }


    @Test
    public void doesNotFailIfImporterFailsAndReturnsImportedDrawingWhenSuccessful ()
            throws Exception {

        MockedLoadCommand command = new MockedLoadCommand();
        Drawing result = command.execute();
        assertTrue(command.showedMessage);
        assertEquals(drawing, result);
        assertEquals("emptyDrawing.arc", result.getFile().getName());
    }

    @Test
    public void doesNotFailIfFileHasInvalidFormat () throws Exception {

        MockedLoadCommand command = new MockedLoadCommand();
        command.testCount = 1;
        command.throwException = true;
        command.execute();
        assertTrue(command.showedMessage);
    }

    @Test
    public void createdFileDialogFiltersCorrectExtensions () throws Exception {

        LoadCommand command = new LoadCommand(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getShell());
        FileDialog filedialog = command.createFileDialog();
        String[] extensions = new NativeFormatEPLoader().getExtensionsArray();

        List<String> expected = new LinkedList<String>();
        for (String extension : extensions) {
            String filter = "*." + extension;
            expected.add(filter);
        }

        assertCollectionTheSame(expected, Arrays.asList(filedialog.getFilterExtensions()));
    }
}
