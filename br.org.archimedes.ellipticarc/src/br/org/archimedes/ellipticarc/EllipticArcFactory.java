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

import br.org.archimedes.controller.commands.PutOrRemoveElementCommand;
import br.org.archimedes.exceptions.InvalidArgumentException;
import br.org.archimedes.exceptions.InvalidParameterException;
import br.org.archimedes.exceptions.NullArgumentException;
import br.org.archimedes.factories.CommandFactory;
import br.org.archimedes.gui.model.Workspace;
import br.org.archimedes.gui.opengl.OpenGLWrapper;
import br.org.archimedes.interfaces.Command;
import br.org.archimedes.interfaces.Parser;
import br.org.archimedes.line.Line;
import br.org.archimedes.model.Point;
import br.org.archimedes.parser.PointParser;

public class EllipticArcFactory implements CommandFactory {
	private Workspace workspace;
	private boolean active;
	private Command command;
	private Point center;
	private Point widthPoint;
	private Point heightPoint;
	private Point initialPoint;
	private Point endPoint;

	public EllipticArcFactory() {

		workspace = br.org.archimedes.Utils.getWorkspace();
		deactivate();
	}

	private void deactivate() {
		center = null;
		widthPoint = null;
		heightPoint = null;
		initialPoint = null;
		endPoint = null;
		active = false;
	}

	@Override
	public String begin() {
		active = true;
		br.org.archimedes.Utils.getController().deselectAll();

		return Messages.EllipticArcFactory_SelectCenter;
	}

	@Override
	public String next(final Object parameter) throws InvalidParameterException {
		String result = null;

		if (isDone()) {
			throw new InvalidParameterException();
		}

		if (parameter != null) {
			try {
				if (center == null) {
					center = (Point) parameter;
					workspace.setPerpendicularGripReferencePoint(center);
					result = Messages.EllipticArcFactory_SelectWidthPoint;
				} else if (widthPoint == null) {
					widthPoint = (Point) parameter;
					workspace.setPerpendicularGripReferencePoint(widthPoint);
					result = Messages.EllipticArcFactory_SelectHeightPoint;
				} else if (heightPoint == null) {
					heightPoint = (Point) parameter;
					workspace.setPerpendicularGripReferencePoint(heightPoint);
					result = Messages.EllipticArcFactory_SelectInitialPoint;
				} else if (initialPoint == null) {
					initialPoint  = (Point) parameter;
					workspace.setPerpendicularGripReferencePoint(initialPoint);
					result = Messages.EllipticArcFactory_SelectEndPoint;
				} else {
					endPoint  = (Point) parameter;
					workspace.setPerpendicularGripReferencePoint(endPoint);
					result = createEllipticArc();
				}
			} catch (ClassCastException e) {
				throw new InvalidParameterException();
			}
		} else {
			throw new InvalidParameterException();
		}
		return result;
	}

	private String createEllipticArc() {
		String result = null;
		EllipticArc newEllipticArc;
		try {
			newEllipticArc = new EllipticArc(center, widthPoint, heightPoint, initialPoint, endPoint);
			command = new PutOrRemoveElementCommand(newEllipticArc, false);
			result = Messages.Created;
		} catch (NullArgumentException e) {
			result = Messages.NotCreatedBecauseNullArgument;
		} catch (InvalidArgumentException e) {
			result = Messages.NotCreatedBecauseInvalidArgument;
		}
		deactivate();
		return result;
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
		Parser returnParser = null;
		if (active) {
			returnParser = new PointParser();
		}
		return returnParser;
	}

	@Override
	public void drawVisualHelper() {
		//TODO
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
