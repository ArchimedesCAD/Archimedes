/**
 * 
 */

package br.org.archimedes.copytoclipboard;

import java.util.List;
import java.util.Set;

import br.org.archimedes.controller.Controller;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;

/**
 * @author cesarse
 */
public class CopyToClipboardFactory extends SelectorFactory {

    @Override
    protected String finishFactory (Set<Element> selection)
            throws InvalidParameterException {

        Controller.getInstance().copyToClipboard(selection);

        return Messages.CommandFinished;
    }

    @Override
    protected String getCancelMessage () {

        return Messages.Cancelled;
    }

    public List<Command> getCommands () {

        // TODO Auto-generated method stub
        return null;
    }

    public String getName () {

        return "copytoclipboard"; //$NON-NLS-1$
    }
}
