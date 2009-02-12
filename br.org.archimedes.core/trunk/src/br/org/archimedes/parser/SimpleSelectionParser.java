/*
 * Created on Jun 15, 2006
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
