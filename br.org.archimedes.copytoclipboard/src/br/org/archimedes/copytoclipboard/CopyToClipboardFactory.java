/**
 * Copyright (c) 2007, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Eduardo O. de Souza - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2007/03/26, 12:57:25, by Eduardo O. de Souza.<br>
 * It is part of package br.org.archimedes.copytoclipboard on the br.org.archimedes.copytoclipboard project.<br>
 */
package br.org.archimedes.copytoclipboard;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.SelectorFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.model.Element;

import java.util.List;
import java.util.Set;

/**
 * @author César Seragiotto
 */
public class CopyToClipboardFactory extends SelectorFactory {

    @Override
    protected String finishFactory (Set<Element> selection)
            throws InvalidParameterException {

        br.org.archimedes.Utils.getController().copyToClipboard(selection);

        return Messages.CommandFinished;
    }

    @Override
    protected String getCancelMessage () {

        return Messages.Cancelled;
    }

    public List<Command> getCommands () {

        // This doesn't generate commands since it only changes the clipboard
        return null;
    }

    public String getName () {

        return "copytoclipboard"; //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see br.org.archimedes.factories.CommandFactory#isTransformFactory()
     */
    public boolean isTransformFactory () {

        return true;
    }
}
