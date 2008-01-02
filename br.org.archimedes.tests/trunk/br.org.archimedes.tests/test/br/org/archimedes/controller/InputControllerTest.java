/*
 * Created on 23/03/2006
 */

package br.org.archimedes.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.org.archimedes.model.Drawing;

public class InputControllerTest {

    private InputController interpreter;

    private Controller controller;


    public InputControllerTest () {

        interpreter = InputController.getInstance();
        controller = Controller.getInstance();
    }

    @Before
    public void setUp () {

        controller.setActiveDrawing(new Drawing("Drawing"));
    }

    @After
    public void tearDown () {

        controller.setActiveDrawing(null);
    }

    // TODO Descobrir pq essa desgraca nao roda

    /**
     * Here we test the getInstance method from the class Interpreter. We also
     * test if the Interpreter creates the tokens, and recognizes them. At last
     * we execute commands.
     */
    @Test
    public void testGetInstance () {

        Assert.assertNotNull(InputController.getInstance());

        repeatCommand();
    }

    @Test
    public void testCancelNullCommand () {

        InputController instance = InputController.getInstance();
        try {
            instance.setCurrentFactory(null);
            instance.cancelCurrentCommand();
        }
        catch (NullPointerException e) {
            Assert.fail("Should not throw null pointer exception");
        }
    }

    private void repeatCommand () {

        Drawing drawing = new Drawing("Drawing 1");
        controller.setActiveDrawing(drawing);
        interpreter.setDrawing(drawing);

        String answer = runCommand("l", new String[] {"0.0;0.0", "1.0;1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);
        answer = runCommand("", new String[] {"1.0;0.0", "-1.0;-1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);
        answer = runCommand("  ", new String[] {"0.0;1.0", "0.0;-1.0"});
        Assert.assertNotNull("The answer should be that the line is ok.",
                answer);

        answer = runCommand("xl", new String[] {"1.0;0.0", "0.0;0.0"});
        Assert.assertNotNull(
                "The answer should be that the infinite line is ok.", answer);
        answer = runCommand("", new String[] {"0.0;0.0", "0.0;1.0"});
        Assert.assertNotNull(
                "The answer should be that the infinite line is ok.", answer);
        answer = runCancelledCommand("", new String[] {"1.0;0.0"});
        Assert.assertNotNull(
                "The answer should be that the infinite line is canceled.",
                answer);

        answer = runCommand("", new String[] {"0.0;0.0", "1.0;1.0"});
        Assert.assertNotNull(
                "The answer should be that the infinite line is ok.", answer);

        // TODO Create tests that create identical lines
    }

    private String runCancelledCommand (String command, String[] parameters) {

        String returnValue = null;
        interpreter.receiveText(command);
        for (String parameter : parameters) {
            interpreter.receiveText(parameter);
        }
        interpreter.cancelCurrentCommand();

        return returnValue;
    }

    private String runCommand (String command, String[] parameters) {

        String returnValue = null;
        interpreter.receiveText(command);
        for (String parameter : parameters) {
            interpreter.receiveText(parameter);
        }

        return returnValue;
    }
}
