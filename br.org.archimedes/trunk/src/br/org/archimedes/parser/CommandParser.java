/*
 * Created on 23/03/2006
 */

package br.org.archimedes.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.rcp.extensionpoints.ElementEPLoader;
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
        ElementEPLoader elementLoader = new ElementEPLoader();
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
