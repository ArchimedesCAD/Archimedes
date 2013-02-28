package br.org.archimedes.ellipticarc;

import org.eclipse.swt.widgets.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

public class EllipticArcFactory implements CommandFactory {

	@Override
	@Override
	public String begin() {
		active = true;
		br.org.archimedes.Utils.getController().deselectAll();

		return Messages.EllipseFactory_SelectInitialPoint;
	}

	@Override
	@Override
	public String next(final Object parameter) throws InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Override
	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@Override
	public String cancel() {
		deactivate();
		return Messages.Canceled;
	}

	@Override
	@Override
	public Parser getNextParser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Override
	public void drawVisualHelper() {
		// TODO Auto-generated method stub

	}

	@Override
	@Override
	public List<Command> getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Override
	public String getName() {
		return "ellipticArc";
	}

	@Override
	@Override
	public boolean isTransformFactory() {
		return false;
	}

}
