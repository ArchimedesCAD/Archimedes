/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * <br>
 * This file was created on 2006/03/23, 11:32:46, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.controller on the br.org.archimedes.core.tests project.<br>
 */
package br.org.archimedes.controller;

import java.util.Observable;
import java.util.Observer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.model.Drawing;

public class InputControllerTest {

    
    /**
     * Belongs to package br.org.archimedes.controller.
     *
     * @author night
     *
     */
    public class InnerObserver implements Observer {

        private String returnValue = null;

        /* (non-Javadoc)
         * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
         */
        public void update (Observable o, Object arg) {
            
                returnValue = arg.toString();
        }

        /**
         * @return The return value received or null if none
         */
        public String getReturnValue () {

            return returnValue;
        }

    }

    private InputController interpreter;

    private Controller controller;


    public InputControllerTest () {

        interpreter = br.org.archimedes.Utils.getInputController();
        controller = br.org.archimedes.Utils.getController();
    }

    @Before
    public void setUp () {

        controller.setActiveDrawing(new Drawing("Drawing"));
    }

    @After
    public void tearDown () {

        controller.setActiveDrawing(null);
    }

    // TODO Descobrir pq essa desgraca nao passa

    /**
     * Here we test the getInstance method from the class Interpreter. We also
     * test if the Interpreter creates the tokens, and recognizes them. At last
     * we execute commands.
     */
    @Test
    public void testGetInstance () {

        Assert.assertNotNull(br.org.archimedes.Utils.getInputController());

        repeatCommand();
    }

    @Test
    public void testCancelNullCommand () {

        InputController instance = br.org.archimedes.Utils.getInputController();
        try {
            instance.setCurrentFactory(null);
            instance.cancelCurrentFactory();
        }
        catch (NullPointerException e) {
            Assert.fail("Should not throw null pointer exception");
        }
    }

    private void repeatCommand () {

        Drawing drawing = new Drawing("Drawing 1");
        controller.setActiveDrawing(drawing);
        interpreter.setDrawing(drawing);

        String answer = runCommand("xl", new String[] {"0.0;0.0", "1.0;1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);
        answer = runCommand("", new String[] {"1.0;0.0", "-1.0;-1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);
        answer = runCommand("  ", new String[] {"0.0;1.0", "0.0;-1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);

        answer = runCommand("c", new String[] {"1.0;0.0", "0.0;0.0"});
        Assert.assertNotNull(
                "The answer should be that the circle is ok.", answer);
        answer = runCommand("", new String[] {"0.0;0.0", "0.0;1.0"});
        Assert.assertNotNull(
                "The answer should be that the circle is ok.", answer);
        answer = runCancelledCommand("", new String[] {"1.0;0.0"});
        Assert.assertNotNull(
                "The answer should be that the circle is canceled.",
                answer);

        answer = runCommand("c", new String[] {"0.0;0.0", "1.0;1.0"});
        Assert.assertNotNull(
                "The answer should be that the infinite line is ok.", answer);

        // TODO Create tests that create identical lines
    }

    private String runCancelledCommand (String command, String[] parameters) {

        runCommand(command, parameters);
        InnerObserver resultObserver = new InnerObserver();
        interpreter.addObserver(resultObserver);
        interpreter.cancelCurrentFactory();
        interpreter.deleteObserver(resultObserver);

        return resultObserver.getReturnValue();
    }

    private String runCommand (String command, String[] parameters) {

        InnerObserver resultObserver = new InnerObserver();
        interpreter.addObserver(resultObserver);
        interpreter.receiveText(command);
        for (String parameter : parameters) {
            interpreter.receiveText(parameter);
        }
        interpreter.deleteObserver(resultObserver);
        return resultObserver.getReturnValue();
    }
}
