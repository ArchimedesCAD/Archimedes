package br.org.archimedes.parser;

import br.org.archimedes.Utils;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.interfaces.Parser;

public class IntegerParser implements Parser{

	int integerValue;
	
	public String next(String message) throws InvalidParameterException {
		
		if (Utils.isDouble(message)) {
            integerValue = Utils.getInteger(message);
        }
        else {
            throw new InvalidParameterException("CRIAR MENSAGEM QUE ESTAVA ESPERANDO UM INTEIRO");
        }

		
		return String.valueOf(integerValue);
	}

	public boolean isDone() {
		return integerValue > 0;
	}

	public Object getParameter() {
		return integerValue;
	}

	
	
}
