package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

public class IntegerParser implements Parser {

	int integerValue;
	
	public String next(String message) throws InvalidParameterException {
		
		if (Utils.isInteger(message)) {
            integerValue = Utils.getInteger(message);
        }
        else {
            throw new InvalidParameterException(Messages.Integer_expectingInteger);
        }

		
		return String.valueOf(integerValue);
	}

	public boolean isDone() {
		return (Object) integerValue != null;
	}

	public Object getParameter() {
		return integerValue;
	}
	
}
