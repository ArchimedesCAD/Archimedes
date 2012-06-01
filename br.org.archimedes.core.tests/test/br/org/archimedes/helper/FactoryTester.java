/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/26, 11:32:46, by Mariana V. Bravo.<br>
 * It is part of package br.org.archimedes.factories on the br.org.archimedes.core.tests project.<br>
 */

package br.org.archimedes.helper;

import static org.junit.Assert.assertNotNull;
import br.org.archimedes.Constant;
import br.org.archimedes.Tester;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This class is used to test command factories.<br>
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author marivb
 */
public abstract class FactoryTester extends Tester {
	
	/**
     * Constructor.
     */
    public FactoryTester () {

        // To load the correct font within the controller.
        // This hack is needed since the singletons within Utils set an activator so the Constant
        // cant find out it is a test.
        Assert.assertNotNull(Constant.DEFAULT_FONT);
    }

    /**
     * Asserts the factory begins properly. Fails if a message is not returned or if the finish
     * state is not valid.
     * 
     * @param factory
     *            The factory to be tested
     * @param finishWithCmds
     *            Indicates if the factory is expected to finish after the begin. Also indicates if
     *            it should make commands.
     */
    protected void assertBegin (CommandFactory factory, boolean finishWithCmds) {

        assertFinished(factory, true);
        String message = factory.begin();
        Assert.assertNotNull("Should tell the user something nice.", message);
        assertFinished(factory, finishWithCmds);
        assertMakeCommands(factory, finishWithCmds);
    }

    /**
     * Asserts the factory begins properly. Fails if a message is not returned or if the finish
     * state is not valid.
     * 
     * @param factory
     *            The factory to be tested
     * @param finish
     *            Indicates if the factory is expected to finish after the begin.
     * @param withCmds
     *            Indicates if the factory should make commands after the begin.
     */
    protected void assertBegin (CommandFactory factory, boolean finish, boolean withCmds) {

        assertFinished(factory, true);
        String message = factory.begin();
        Assert.assertNotNull("Should tell the user something nice.", message);
        assertFinished(factory, finish);
        assertMakeCommands(factory, withCmds);
    }

    /**
     * Asserts the factory cancels properly. Fails if a message is not returned or if the
     * getCommands is not as specified.
     * 
     * @param factory
     *            The factory to be tested
     * @param makeCmds
     *            Indicates if the factory is expected to make commands after being canceled.
     */
    protected void assertCancel (CommandFactory factory, boolean makeCmds) {

        String message = factory.cancel();
        Assert.assertNotNull("Should tell the user something nice.", message);
        assertMakeCommands(factory, makeCmds);
    }

    /**
     * Tests a valid next iteration of a factory. Fails if:
     * <ul>
     * <li>Any exception is thrown</li>
     * <li>The returned message is null</li>
     * <li>The factory's "finish state" is invalid</li>
     * <li>The factory doesn't have expected behaviour about generating commands</li>
     * </ul>
     * 
     * @param factory
     *            The factory to be tested
     * @param parameter
     *            The parameter to be passed
     * @param finishWithCmds
     *            If true, the factory is expected to finish with commands. If false, it is expected
     *            not to finish and not to make commands.
     */
    protected void assertSafeNext (CommandFactory factory, Object parameter, boolean finishWithCmds) {

        assertSafeNext(factory, parameter, finishWithCmds, finishWithCmds);
    }

    /**
     * Tests a valid next iteration of a factory. Fails if:
     * <ul>
     * <li>Any exception is thrown</li>
     * <li>The returned message is null</li>
     * <li>The factory's "finish state" is invalid</li>
     * <li>The factory doesn't have expected behaviour about generating commands</li>
     * </ul>
     * 
     * @param factory
     *            The factory to be tested
     * @param parameter
     *            The parameter to be passed
     * @param finish
     *            If true, the factory is expected to finish after this iteration.
     * @param makeCmds
     *            If true, the factory is expected to make commands after this iteration.
     */
    protected void assertSafeNext (CommandFactory factory, Object parameter, boolean finish,
            boolean makeCmds) {

        assertSafeNext(factory, parameter, finish, makeCmds, true);
    }

    /**
     * Tests a valid next iteration of a factory. Fails if:
     * <ul>
     * <li>Any exception is thrown</li>
     * <li>The returned message is null</li>
     * <li>The factory's "finish state" is invalid</li>
     * <li>The factory doesn't have expected behaviour about generating commands</li>
     * </ul>
     * 
     * @param factory
     *            The factory to be tested
     * @param parameter
     *            The parameter to be passed
     * @param finish
     *            If true, the factory is expected to finish after this iteration.
     * @param makeCmds
     *            If true, the factory is expected to make commands after this iteration.
     * @param shouldTalk
     *            If true, the factory is expected to return some message.
     */
    protected void assertSafeNext (CommandFactory factory, Object parameter, boolean finish,
            boolean makeCmds, boolean shouldTalk) {

        String message = null;
        try {
            message = factory.next(parameter);
        }
        catch (InvalidParameterException e) {
            Assert.fail("Should not throw this exception: " + e);
        }
        if (shouldTalk) {
            Assert.assertNotNull("Should tell the user something nice", message);
        }
        else {
            Assert.assertNull("Should not tell the user something nice", message);
        }
        assertFinished(factory, finish);
        assertMakeCommands(factory, makeCmds);
    }

    /**
     * Asserts a factory has (or not) generated commands.
     * 
     * @param factory
     *            The factory to be tested.
     * @param makeCmds
     *            if true, the factory is expected to generate commands. Otherwise, it isn't.
     */
    private void assertMakeCommands (CommandFactory factory, boolean makeCmds) {

        String should = makeCmds ? "" : "not";

        List<Command> cmds = factory.getCommands();
        boolean hasCmds = cmds != null && !cmds.isEmpty();
        Assert.assertEquals("The factory should " + should + " have made commands", makeCmds,
                hasCmds);
    }

    /**
     * Asserts a factory has (or not) finished. Checks the isDone and getNextParser methods work as
     * expected.
     * 
     * @param factory
     *            The factory to be tested.
     * @param finish
     *            if true, the factory is expected to be finished. Otherwise, it isn't.
     */
    protected void assertFinished (CommandFactory factory, boolean finish) {

        String should = finish ? "" : "not";

        Assert.assertEquals("The factory should " + should + " be done", finish, factory.isDone());
        Parser nextParser = factory.getNextParser();
        if (finish) {
            Assert.assertNull("The parser should " + should + " be null", nextParser);
        }
        else {
            Assert.assertNotNull("The parser should " + should + " be null", nextParser);
        }
    }

    /**
     * Tests if a parameter is considered invalid by the factory. Fails if InvalidParameterException
     * is not thrown. Also checks that the finish state is not changed and that no commands are made
     * beacuse of an invalid parameter.
     * 
     * @param factory
     *            The factory to be tested. It should not be done.
     * @param parameter
     *            The invalid parameter.
     */
    protected void assertInvalidNext (CommandFactory factory, Object parameter) {

        boolean done = factory.isDone();
        try {
            factory.next(parameter);
            Assert.fail("Should throw an exception");
        }
        catch (InvalidParameterException e) {}
        assertFinished(factory, done);
        assertMakeCommands(factory, false);
    }
    
    @Test
    public abstract void testFactoryName ();
}
