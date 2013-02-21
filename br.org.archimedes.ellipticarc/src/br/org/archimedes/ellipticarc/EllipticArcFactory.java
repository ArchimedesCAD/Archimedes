package br.org.archimedes.ellipticarc;

import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

public class EllipticArcFactory implements CommandFactory {

	@Override
	public String begin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String next(Object parameter) throws InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String cancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Parser getNextParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawVisualHelper() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Command> getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTransformFactory() {
		// TODO Auto-generated method stub
		return false;
	}

}
