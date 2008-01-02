/*
 * Created on 08/05/2006
 */

package br.org.archimedes.undo;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

public class UndoFactory implements CommandFactory {

    private Command command;


    public String begin () {

        setCommand(new UndoCommand());
        return Messages.UndoPerformed;
    }

    /**
     * @param command
     *            The command to be set
     */
    protected void setCommand (Command command) {

        this.command = command;
    }

    public String next (Object parameter) throws InvalidParameterException {

        return null;
    }

    public boolean isDone () {

        return true;
    }

    public String cancel () {

        return null;
    }

    public void drawVisualHelper () {

        // No visual helper
    }

    public String getName () {

        return "undo"; //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tarantulus.archimedes.commands.Command#getNextParser()
     */
    public Parser getNextParser () {

        return null;
    }

    public List<Command> getCommands () {

        List<Command> cmds = null;
        if (command != null) {
            cmds = new ArrayList<Command>();
            cmds.add(command);
            setCommand(null);
        }
        return cmds;
    }
}
