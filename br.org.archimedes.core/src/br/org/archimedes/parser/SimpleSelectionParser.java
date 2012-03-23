/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Mariana V. Bravo - initial API and implementation<br>
 * Hugo Corbucci - later contributions<br>
 * <br>
 * This file was created on 2006/06/15, 00:03:02, by Hugo Corbucci.<br>
 * It is part of package br.org.archimedes.parser on the br.org.archimedes.core project.<br>
 */
package br.org.archimedes.parser;

import java.util.Set;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Element;

/**
 * Belongs to package br.org.archimedes.interpreter.parser.
 * 
 * @author marivb
 */
public class SimpleSelectionParser implements Parser {

	private Set<Element> selection;

	public SimpleSelectionParser() {
		selection = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
	 */
	public String next(String message) throws InvalidParameterException {
		if (Utils.isReturn(message)) {
			try {
                selection = br.org.archimedes.Utils.getController().getCurrentSelectedElements();
            }
            catch (NoActiveDrawingException e) {
                // Should not happen.
                e.printStackTrace();
            }
		}
		else {
            throw new InvalidParameterException();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#isDone()
	 */
	public boolean isDone() {
		return selection != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
	 */
	public Object getParameter() {
		return selection;
	}
}
