/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Victor D. Lopes - later contributions<br>
 * <br>
 * This file was created on 2007/04/02, 11:11:29, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.paste on the br.org.archimedes.paste project.<br>
 */
package br.org.archimedes.paste;

import br.org.archimedes.Constant;
import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;
import br.org.archimedes.model.Layer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasteFactory implements CommandFactory {

    private Collection<Element> elements;

    private Command command;

    private boolean done = true;


    public String begin () {

        String result = Messages.ClipboardEmpty;
        done = false;
        elements = br.org.archimedes.Utils.getWorkspace().getClipboard();
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

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
