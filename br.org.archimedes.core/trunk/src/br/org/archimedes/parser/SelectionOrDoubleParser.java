package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NoActiveDrawingException;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.model.Selection;

public class SelectionOrDoubleParser implements Parser {
	private Selection selection;
	private Double value;
	private boolean done;
	public SelectionOrDoubleParser () {
		done = false;
		selection = null;
		value = null;
		br.org.archimedes.Utils.getController().deselectAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#next(java.lang.String)
	 */
	public String next (String message) throws InvalidParameterException {
		if (Utils.isDouble(message)) {
            value = Math.abs((Double) Utils.getDouble(message));
            done = true;
        } else {
			try {
				selection = br.org.archimedes.Utils.getController().getCurrentSelection();
			}
			catch (NoActiveDrawingException e) {
				// Should not happen.
				e.printStackTrace();
			}
	
			if (selection.isEmpty()) {
				selection = null;
				throw new InvalidParameterException();
			}
        }

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#isDone()
	 */
	public boolean isDone () {
		return (selection != null || done);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.org.archimedes.interpreter.parser.Parser#getParameter()
	 */
	public Object getParameter () {
		return (selection == null)? value : selection;		
	}

}
