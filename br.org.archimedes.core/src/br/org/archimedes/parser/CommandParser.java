/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Gustavo Menezes - initial API and implementation<br>
 * Hugo Corbucci, Mariana V. Bravo, Paulo L. Huaman - later contributions<br>
 * <br>
 * This file was created on 2006/03/23, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.rcp.extensionpoints.ElementFactoryEPLoader;
import br.org.archimedes.rcp.extensionpoints.FactoryEPLoader;

/**
 * This class must have all the verifications for all the commands.<br>
 * Belongs to package br.org.archimedes.interpreter.
 * 
 * @author gigante
 */
public class CommandParser {

    private Map<String, CommandFactory> commands;

    /**
     * Default constructor.
     */
    public CommandParser () {

        FactoryEPLoader factoryLoader = new FactoryEPLoader();
        ElementFactoryEPLoader elementLoader = new ElementFactoryEPLoader();
        commands = new HashMap<String, CommandFactory>();
        
        commands.putAll(factoryLoader.getFatoryMap());
        commands.putAll(elementLoader.getElementFactoryMap());
    }

    /**
     * Verifies if a command exists.
     * 
     * @param command
     *            The command to verify. Returns true if the command exists,
     *            false otherwise.
     */
    public boolean existsCommand (String command) {

        return commands.containsKey(command.toLowerCase());
    }

    /**
     * This method identificates what command factory will be executed.
     * 
     * @param commandString
     *            The identifier of a command factory.
     * @return The command factory associated to the identifier.
     */
    public CommandFactory getCommand (String commandString) {

        return commands.get(commandString.toLowerCase());
    }

    public List<String> getCommandList () {

        List<String> factoryNames = new ArrayList<String>();
        for (CommandFactory cmdFact : commands.values()) {
            if ( !factoryNames.contains(cmdFact.toString())) {
                factoryNames.add(cmdFact.toString());
            }
        }
        return factoryNames;
    }
}
