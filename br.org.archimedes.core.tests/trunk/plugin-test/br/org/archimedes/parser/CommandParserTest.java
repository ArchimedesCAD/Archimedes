/*
 * Created on 23/03/2006
 */

package br.org.archimedes.parser;

import junit.framework.TestCase;

import org.junit.Test;

import br.org.archimedes.factories.CommandFactory;

public class CommandParserTest extends TestCase {

    /**
     * Tests: the recognization of the commands (String).
     */
    @Test
    public void test () {

        CommandParser cp = new CommandParser();

        CommandFactory com;

        com = cp.getCommand("invalid");
        assertNull(com);
    }
}
