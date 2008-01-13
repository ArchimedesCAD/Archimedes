/*
 * Created on 25/08/2006
 */

package br.org.archimedes.controller.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.interfaces.UndoableCommand;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package br.org.archimedes.model.commands.
 * 
 * @author jefsilva
 */
public class MacroCommand implements UndoableCommand {

	private List<UndoableCommand> commands;

	/**
	 * Contructor.
	 * 
	 * @param commandList
	 *            The list of commands to be executed by this macro.
	 * @throws NullArgumentException
	 *             In case the command list is null
	 * @throws IllegalActionException
	 *             In case the command list is empty<br>
	 *             TODO Change to Invalid Parameter?
	 */
	public MacroCommand(List<UndoableCommand> commandList)
			throws NullArgumentException, IllegalActionException {

		if (commandList == null) {
			throw new NullArgumentException();
		}
		if (commandList.isEmpty()) {
			throw new IllegalActionException();
		}

		commands = new ArrayList<UndoableCommand>(commandList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
	 */
	public void doIt(Drawing drawing) throws IllegalActionException,
			NullArgumentException {

		for (UndoableCommand cmd : commands) {
			cmd.doIt(drawing);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.model.commands.UndoableCommand#undoIt(br.org.archimedes.model.Drawing)
	 */
	public void undoIt(Drawing drawing) throws IllegalActionException,
			NullArgumentException {

		List<UndoableCommand> reversedCommands = new ArrayList<UndoableCommand>(
				commands);
		Collections.reverse(reversedCommands);
		for (UndoableCommand cmd : reversedCommands) {
			cmd.undoIt(drawing);
		}
	}
}
