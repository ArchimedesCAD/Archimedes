/**
 * Copyright (c) 2006, 2009 Hugo Corbucci and others.<br>
 * All rights reserved. This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html<br>
 * <br>
 * Contributors:<br>
 * Hugo Corbucci - initial API and implementation<br>
 * Roberto L. M. Rodrigues, Eduardo Morais and Neuton Jr.<br>
 * <br>
 * This file was created on 2013/02/28, 13:06:39, by Roberto L. M. Rodrigues.<br>
 * It is part of package br.org.archimedes.ellipticArc on the br.org.archimedes.ellipticArc project.<br>
 */

package br.org.archimedes.ellipticarc;

import java.util.ArrayList;
import java.util.List;

import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;

public class EllipticArcFactory implements CommandFactory {
	private Workspace workspace;
	private boolean active;
	private Command command;
	private boolean isCenterProtocol;

	public EllipticArcFactory() {

		workspace = br.org.archimedes.Utils.getWorkspace();
		deactivate();
		this.isCenterProtocol = false;
	}

	private void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String begin() {
		active = true;
		br.org.archimedes.Utils.getController().deselectAll();

		return Messages.EllipticArcFactory_SelectInitialPoint;
	}

	@Override
	public String next(final Object parameter) throws InvalidParameterException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDone() {
		return !active;
	}

	@Override
	public String cancel() {
		deactivate();
		return Messages.Canceled;
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
		List<Command> cmds = null;

		if (command != null) {
			cmds = new ArrayList<Command>();
			cmds.add(command);
			command = null;
		}

		return cmds;
	}

	@Override
	public String getName() {
		return "ellipticArc";
	}

	@Override
	public boolean isTransformFactory() {
		return false;
	}

}
