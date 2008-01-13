/*
 * Created on 18/08/2006
 */

package br.org.archimedes.orto;

import br.org.archimedes.exceptions.IllegalActionException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Drawing;


/**
 * Belongs to package br.org.archimedes.factories.
 * 
 * @author jefsilva
 */
public class OrtoCommand implements Command {

    /*
     * (non-Javadoc)
     * 
     * @see br.org.archimedes.model.commands.Command#doIt(br.org.archimedes.model.Drawing)
     */
    public void doIt (Drawing drawing) throws IllegalActionException,
            NullArgumentException {

        Workspace workspace = Workspace.getInstance();
        boolean ortoState = workspace.isOrtoOn();
        workspace.setOrtoOn( !ortoState);
    }
}
