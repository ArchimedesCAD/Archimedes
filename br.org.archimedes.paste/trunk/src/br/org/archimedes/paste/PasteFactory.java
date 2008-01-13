
package br.org.archimedes.paste;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.org.archimedes.Constant;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;

public class PasteFactory implements CommandFactory {

    private Collection<Element> elements;

    private Command command;

    private boolean done = true;


    public String begin () {

        String result = Messages.ClipboardEmpty;
        done = false;
        elements = Workspace.getInstance().getClipboard();
        try {
            result = next(elements);
        }
        catch (InvalidParameterException e) {
            e.printStackTrace();
        }

        return result;
    }

    public String cancel () {

        return null;
    }

    public void drawVisualHelper () {

    }

    public List<Command> getCommands () {

        List<Command> commands = new ArrayList<Command>();
        if (command != null) {
            commands.add(command);
            command = null;
        }

        return commands;
    }

    public String getName () {

        return "paste"; //$NON-NLS-1$
    }

    public Parser getNextParser () {

        return null;
    }

    public boolean isDone () {

        return done;
    }

    @SuppressWarnings("unchecked") //$NON-NLS-1$
    public String next (Object parameter) throws InvalidParameterException {

        String result;        
        
        if (parameter == null) {
            throw new InvalidParameterException();
        }
        try {
        	
        	Collection<Element> parametersClone = new ArrayList<Element>();
        	
        	for (Element element : (Collection<Element>)parameter) {
        		
        		Element cloned = element.clone();
    			Layer layerClone = element.getLayer().clone();
    			cloned.setLayer(layerClone);
    			
				parametersClone.add(cloned);
			}
            command = new PutOrRemoveElementCommand(parametersClone, false);
            result = Messages.CommandFinished;
            done = true;
        }
        catch (NullArgumentException e) {
            e.printStackTrace();
            result = Messages.Failed + Constant.NEW_LINE + Messages.TargetExpected;
        }
        return result;
    }
}
